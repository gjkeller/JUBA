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

import okhttp3.RequestBody;

public class Route {

    private static String BASE_URL = "https://unbelievable.pizza/api/v1";
    //Get user object. Infinity balance will be returned as a String, not a number
    public static Route GET_USER_BALANCE = new Route(Method.GET, "/guilds/%s/users/%s");
    //Set balance. Number cash, Number bank, String reason expected
    public static Route PUT_USER_BALANCE = new Route(Method.PUT, "/guilds/%s/users/%s");
    //Change balance by number. Number cash, Number bank, String reason expected
    public static Route PATCH_USER_BALANCE = new Route(Method.PATCH, "/guilds/%s/users/%s");
    //Gets Array of User objects in descending order of net worth (total)
    public static Route GET_GUILD_LEADERBOARD = new Route(Method.GET, "/guilds/%s/users");
    //Gets Guild information (name, icon, etc.)
    public static Route GET_GUILD = new Route(Method.GET, "/guilds/%s");

    private final Method method;
    private final String url;
    private int paramCount;

    private Route(Method method, String url){
        this.method = method;
        this.url = url;

        paramCount = StringUtil.countMatches(url, "%s");
    }

    public CompiledRoute compile(RequestBody body, String... args){
        if(paramCount!=args.length)
            throw new IllegalArgumentException("Given parameters couldn't satisfy the arguments of " + this);

        return new CompiledRoute(method, BASE_URL + String.format(url, args), body);
    }

    public CompiledRoute compile(String... args){
        return compile(null, args);
    }

    @Override
    public String toString(){
        return "Route(" + method + ": " + url + ")";
    }

    public class CompiledRoute {

        private Method method;
        private RequestBody body;
        private String url;

        public CompiledRoute(Method method, String url, RequestBody body){
            this.method = method;
            this.body = body;
            this.url = url;
        }

        public Method getMethod() {
            return method;
        }

        public RequestBody getBody() {
            return body;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String toString(){
            return "CompiledRoute(" + method + ": " + url + ")";
        }
    }
}
