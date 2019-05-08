package itsgjk.juba.requests;


import itsgjk.juba.core.JUBA;
import itsgjk.juba.util.Route;
import okhttp3.Response;

import java.util.function.Consumer;

public abstract class JUBARestAction<T> {

    private JUBA juba;
    private JUBARequest<? super T> request;
    private Route.CompiledRoute route;
    private static Consumer<?> DEFAULT_SUCCESS_CALLBACK = t -> {};
    private static Consumer<? super Throwable> DEFAULT_ERROR_CALLBACK = t -> {
        JUBA.LOGGER.error("Request returned error:");
        t.printStackTrace();
    };

    public JUBARestAction(JUBA juba, Route.CompiledRoute route){
        this.juba = juba;
        this.route = route;
    }

    @SuppressWarnings("unchecked")
    public void queue(){
        queue((Consumer<T>) DEFAULT_SUCCESS_CALLBACK, DEFAULT_ERROR_CALLBACK);
    }

    public void queue(Consumer<T> success){
        queue(success, DEFAULT_ERROR_CALLBACK);
    }

    public void queue(Consumer<T> success, Consumer<? super Throwable> failure){
        juba.getRequester().getResponseFuture(new JUBARequest<T>(this, success, failure, route));
    }

    public T complete(){
        return juba.getRequester().getResponseFuture(new JUBARequest<>(this, success, failure, route)).get();
    }

    public abstract void handleResponse(Response response, JUBARequest<?> request);
}
