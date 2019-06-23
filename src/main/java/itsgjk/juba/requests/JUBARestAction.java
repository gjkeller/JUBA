/*
 * Copyright 2019 Gabriel Keller
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package itsgjk.juba.requests;

import itsgjk.juba.core.JUBA;
import itsgjk.juba.exceptions.RateLimitException;
import itsgjk.juba.util.RequestUtil;
import itsgjk.juba.util.Route;
import okhttp3.Response;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Class representing the connection between JUBA and the UnbelievaBoat API. Using JUBARestAction, you can specify how JUBA handles your requests how they will be executed.
 * This class appears whenever you make a request to UnbelievaBoat's API.
 *
 * @param <T> Resulting object of the RestAction
 */
public abstract class JUBARestAction<T> {

    protected Requester requester;
    protected Route.CompiledRoute route;
    protected JSONObject data;

    protected static ExecutorService service;
    protected static Consumer<?> DEFAULT_SUCCESS_CALLBACK = t -> {};
    protected static Consumer<? super Throwable> DEFAULT_ERROR_CALLBACK = t -> {
        JUBA.LOG.error("RestAction returned error:", t);
    };

    static{
        service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public JUBARestAction(Requester requester, Route.CompiledRoute route){
        this(requester, route, null);
    }

    public JUBARestAction(Requester requester, Route.CompiledRoute route, JSONObject data){
        this.requester = requester;
        this.route = route;
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    public void queue(){
        queue((Consumer<T>) DEFAULT_SUCCESS_CALLBACK, DEFAULT_ERROR_CALLBACK);
    }

    public void queue(Consumer<T> success){
        queue(success, DEFAULT_ERROR_CALLBACK);
    }

    /**
     * Queues the task for asynchronous execution on a new Thread
     *
     * @param success Consumer passing the resulting object
     * @param error Consumer passing any potential errors
     */
    public void queue(Consumer<T> success, Consumer<? super Throwable> error){
        service.submit(() -> {
            Response r = null;
            boolean retried = false;
            do{
                try{
                    if(data == null)
                        r = requester.execute(route);
                    else
                        r = requester.execute(route, RequestUtil.createRequestBody(data.toString()));
                    T t = handleResponse(r);

                    success.accept(t);
                }
                catch(RateLimitException ex){
                    try{
                        //going to work on this soon. doing this just to stop possible recursion, however this shouldn't happen unless something else is ratelimiting this endpoint
                        if(retried){
                            error.accept(ex);
                            return;
                        }

                        Thread.sleep(ex.getRetryAfter());
                        retried = true;
                    }
                    catch(InterruptedException ex2){
                        //shouldn't happen?
                        error.accept(ex2);
                        return;
                    }
                }
                catch(Throwable ex){
                    error.accept(ex);
                    return;
                }
            }
            while(r == null);
        });
    }

    /**
     * Blocks the current Thread to complete the task
     *
     * @return Expected result that the RestAction should return
     */
    public T complete(){
        Response r = null;

        if(data == null)
            r = requester.execute(route);
        else
            r = requester.execute(route, RequestUtil.createRequestBody(data.toString()));

        return handleResponse(r);
    }

    protected abstract T handleResponse(Response response);
}
