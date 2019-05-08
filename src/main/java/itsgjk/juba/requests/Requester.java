package itsgjk.juba.requests;

import itsgjk.juba.core.JUBA;
import itsgjk.juba.util.Route;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Requester {

    OkHttpClient client;
    ExecutorService service;
    JUBA juba;
    private String token;

    public Requester(JUBA juba, String token){
        client = new OkHttpClient();
        service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.juba = juba;
        this.token = token;
    }

    //Sparta id 472264989793583106
    //My id 196834326963290112

    public void execute(JUBARequest<?> request) throws IOException {
        Route.CompiledRoute route = request.getRoute();

        Request.Builder builder = new okhttp3.Request.Builder();

        builder.url(route.getUrl());
        builder.method(route.getMethod().toString(), route.getBody());

        try{
            Response r = client.newCall(builder.build()).execute();
            request.getRestAction().handleResponse(r, request);
        }
        catch(IOException ex){
            request.onFailure.accept(ex);
        }
    }

    public Response getResponseBlocking(Route.CompiledRoute compiledRoute){
        Request req = new Request.Builder()
                .header("Authorization", token)
                .url(compiledRoute.getUrl())
                .build();
    }

    public Future<Response> getResponseFuture(JUBARequest<?> request){
        return service.submit(() -> execute(request));
    }
    //https://www.foreach.be/blog/parallel-and-asynchronous-programming-in-java-8
}
