/*
 * Copyright 2019 Gabriel Keller
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package itsgjk.juba.util;

public class Route {

    public static Route GET_USER_BALANCE = new Route(Method.GET, "/guilds/{guild.id}/users/{user.id}");

    private final Method method;
    private final String url;
    private int paramCount;

    private Route(Method method, String url){
        this.method = method;
        this.url = url;

        paramCount = StringUtil.countMatches(url, "%s");

    }

    public class CompiledRoute {


        public CompiledRoute(){

        }
    }
}
