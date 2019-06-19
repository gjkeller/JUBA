/*
 * Copyright 2019 Gabriel Keller
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package itsgjk.juba.core;

import itsgjk.juba.entity.EntityBuilder;
import itsgjk.juba.entity.JUBAUser;
import itsgjk.juba.requests.JUBAAuditableRestAction;
import itsgjk.juba.requests.JUBARestAction;
import itsgjk.juba.requests.Requester;
import itsgjk.juba.util.Checks;
import itsgjk.juba.util.Route;
import okhttp3.Response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JUBA {

    public static final Logger LOG = LoggerFactory.getLogger(JUBA.class);
    private Requester requester;


    public JUBA(String token){

        if(token == null || token.isEmpty())
            throw new IllegalArgumentException("Provided token was null or empty!");

        this.requester = new Requester(this, token);
        JUBARestAction.init();
    }

    /**
     * Sets a user's balance
     * Use Long.MAX_VALUE to represent Infinity in cash or bank balance
     *
     * @param guildId Discord ID of the guild the user is in
     * @param userId Discord ID of the user
     * @param cash Cash balance to set
     * @param bank Bank balance to set
     * @return The updated JUBAUser object
     */
    public JUBAAuditableRestAction<JUBAUser> setUserBalance(String guildId, String userId, long cash, long bank){
        Checks.isSnowflake(guildId, "Guild ID");
        Checks.isSnowflake(userId, "User ID");

        JSONObject jsonObject = new JSONObject();
        jsonObject.append("cash", cash);
        jsonObject.append("bank", bank);

        return new JUBAAuditableRestAction<JUBAUser>(requester, Route.PUT_USER_BALANCE.compile(guildId, userId), jsonObject) {
            @Override
            protected JUBAUser handleResponse(Response response) {
                return EntityBuilder.buildUser(response, guildId);
            }
        };
    }

    public JUBAAuditableRestAction<JUBAUser> modifyUserBalance(String guildId, String userId, long cash, long bank){
        Checks.isSnowflake(guildId, "Guild ID");
        Checks.isSnowflake(userId, "User ID");

        JSONObject jsonObject = new JSONObject();
        jsonObject.append("cash", cash);
        jsonObject.append("bank", bank);

        return new JUBAAuditableRestAction<JUBAUser>(requester, Route.PATCH_USER_BALANCE.compile(guildId, userId), jsonObject) {
            @Override
            protected JUBAUser handleResponse(Response response) {
                return EntityBuilder.buildUser(response, guildId);
            }
        };
    }

    /**
     * Gets a user's balance
     * Long.MAX_VALUE is used to represent infinity in balances
     *
     * @param guildId Discord ID of the guild the user is in
     * @param userId Discord ID of the user
     * @return The JUBAUser object
     */
    public JUBARestAction<JUBAUser> getUserBalance(String guildId, String userId){
        Checks.isSnowflake(guildId, "Guild ID");
        Checks.isSnowflake(userId, "User ID");

        return new JUBARestAction<JUBAUser>(requester, Route.GET_USER_BALANCE.compile(guildId, userId)) {
            @Override
            protected JUBAUser handleResponse(Response response) {
                return EntityBuilder.buildUser(response, guildId);
            }
        };

    }
}
