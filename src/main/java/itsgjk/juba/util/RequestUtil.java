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
