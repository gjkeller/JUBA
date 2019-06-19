package itsgjk.juba.requests;

import itsgjk.juba.core.JUBA;
import itsgjk.juba.exceptions.RateLimitException;
import itsgjk.juba.exceptions.RequestException;
import itsgjk.juba.util.RequestUtil;
import itsgjk.juba.util.Route;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class Requester {

    private OkHttpClient client;
    private JUBA juba;
    private String token;


    public Requester(JUBA juba, String token){
        client = new OkHttpClient();
        this.juba = juba;
        this.token = token;
    }

    public Response execute(Route.CompiledRoute compiledRoute) throws RateLimitException {
        return execute(compiledRoute, null);
    }

    public Response execute(Route.CompiledRoute compiledRoute, RequestBody body) throws RateLimitException{
        Request request = new Request.Builder()
                .header("Authorization", token)
                .url(compiledRoute.getUrl())
                .method(compiledRoute.getMethod().toString(), body)
                .build();

        try{
            Response r = client.newCall(request).execute();
            if(r.isSuccessful())
                return r;
            else if(r.code()==429)
                throw new RateLimitException("The request was ratelimited", RequestUtil.getRetryAfter(r));
            else
                JUBA.LOG.error("Experienced error while executing request: " + r.code() + ": " + r.message());
                throw new RequestException(r.code(), r.code() + " " + r.message());
        }
        catch(IOException ex){
            JUBA.LOG.error("Error while performing request:", ex);
            throw new RuntimeException(ex);
        }
    }
}
