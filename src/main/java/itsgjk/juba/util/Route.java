/*
 * Copyright 2019 Gabriel Keller
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package itsgjk.juba.util;

public class Route {

    private static String BASE_URL = "https://unbelievable.pizza/api/v1";
    public static Route GET_USER_BALANCE = new Route(Method.GET, "/guilds/%s/users/%s");

    private final Method method;
    private final String url;
    private int paramCount;

    private Route(Method method, String url){
        this.method = method;
        this.url = url;

        paramCount = StringUtil.countMatches(url, "%s");
    }

    public CompiledRoute compile(String... args){
        if(paramCount!=args.length)
            throw new IllegalArgumentException("Given parameters couldn't satisfy the arguments of " + this);

        return new CompiledRoute(method, String.format(url, args));
    }

    @Override
    public String toString(){
        return "Route(" + method + ": " + url + ")";
    }

    public class CompiledRoute {

        private Method method;
        private String url;

        public CompiledRoute(Method method, String url){
            this.method = method;
            this.url = BASE_URL + url;
        }

        public Method getMethod() {
            return method;
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
