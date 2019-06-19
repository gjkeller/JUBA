package itsgjk.juba.util;

import itsgjk.juba.core.JUBA;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

public class RequestUtil {

    public static RequestBody createRequestBody(String jsonBody){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        return RequestBody.create(JSON, jsonBody);
    }

    public static long getRetryAfter(Response response) {
        String body;
        try{
            body = response.body().string();
        }
        catch(Throwable ex){
            JUBA.LOG.error("Failed to convert Response to String:", ex);
            throw new IllegalArgumentException("Response could not be converted to String");
        }

        JSONObject object = new JSONObject(body);

        return object.getLong("retry_after");
    }
}
