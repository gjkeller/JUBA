package itsgjk.juba.requests;

import itsgjk.juba.core.JUBA;
import itsgjk.juba.util.Route;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.concurrent.CompletableFuture;

public class Requester {

    OkHttpClient client;
    JUBA juba;
    private String token;

    public Requester(JUBA juba, String token){
        client = new OkHttpClient();
        this.juba = juba;
        this.token = token;
    }

    //Sparta id 472264989793583106
    //My id 196834326963290112

    public Response getResponse(Route.CompiledRoute compiledRoute){
        Request req = new Request.Builder()
                .header("Authorization", token)
                .url(compiledRoute.getUrl())
                .build();
    }

    public CompletableFuture<Response> getResponseAsync(Route.CompiledRoute compiledRoute){
        return CompletableFuture.supplyAsync(() -> getResponse(compiledRoute));
    }
    //https://www.foreach.be/blog/parallel-and-asynchronous-programming-in-java-8
}
