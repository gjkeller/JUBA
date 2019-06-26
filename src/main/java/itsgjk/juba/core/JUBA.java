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

package itsgjk.juba.core;

import itsgjk.juba.entity.EntityBuilder;
import itsgjk.juba.entity.JUBAGuild;
import itsgjk.juba.entity.JUBAGuildInfo;
import itsgjk.juba.entity.JUBAUser;
import itsgjk.juba.requests.JUBAAuditableRestAction;
import itsgjk.juba.requests.JUBARestAction;
import itsgjk.juba.requests.Requester;
import itsgjk.juba.util.Checks;
import itsgjk.juba.util.JUBALogger;
import itsgjk.juba.util.Route;
import okhttp3.Response;
import org.json.JSONObject;
import org.slf4j.Logger;

public class JUBA {

    public static final Logger LOG = JUBALogger.getLog(JUBA.class);
    private Requester requester;
    private EntityBuilder entityBuilder;

    /**
     * Creates a JUBA instance for use in interfacing with the UnbelievaBoat API
     *
     * @param token UnbelievaBoat API token (Retrieved here after logging in: <a href="https://unbelievaboat.com/api/docs">UnbelievaBoat website</a>)
     */
    public JUBA(String token){

        if(token == null || token.isEmpty())
            throw new IllegalArgumentException("Provided token was null or empty!");

        this.requester = new Requester(this, token);
        entityBuilder = new EntityBuilder(this);
    }

    /**
     * Sets a user's balance
     *
     * @param guildId Discord ID of the guild the user is in
     * @param userId Discord ID of the user
     * @param cash Cash balance to set
     * @param bank Bank balance to set
     * @return Updated JUBAUser object. Unfortunately, at this time, this updated user object does not contain a leaderboard rank. It will be set as -1.
     */
    public JUBAAuditableRestAction<JUBAUser> setBalance(String guildId, String userId, long cash, long bank){
        Checks.isSnowflake(guildId, "Guild ID");
        Checks.isSnowflake(userId, "User ID");

        JSONObject jsonObject = new JSONObject();
        jsonObject.append("cash", cash);
        jsonObject.append("bank", bank);

        return new JUBAAuditableRestAction<JUBAUser>(requester, Route.PUT_USER_BALANCE.compile(guildId, userId), jsonObject) {
            @Override
            protected JUBAUser handleResponse(Response response) {
                return entityBuilder.buildUser(response, guildId);
            }
        };
    }

    /**
     * Sets a user's cash balance
     *
     * @param guildId Discord ID of the guild the user is in
     * @param userId Discord ID of the user
     * @param cash Cash balance to set
     * @return Updated JUBAUser object. Unfortunately, at this time, this updated user object does not contain a leaderboard rank. It will be set as -1.
     */
    public JUBAAuditableRestAction<JUBAUser> setCashBalance(String guildId, String userId, long cash){
        Checks.isSnowflake(guildId, "Guild ID");
        Checks.isSnowflake(guildId, "User ID");

        JSONObject jsonObject = new JSONObject();
        jsonObject.append("cash", cash);

        return new JUBAAuditableRestAction<JUBAUser>(requester, Route.PUT_USER_BALANCE.compile(guildId, userId), jsonObject) {
            @Override
            protected JUBAUser handleResponse(Response response) {
                return entityBuilder.buildUser(response, guildId);
            }
        };
    }

    /**
     * Sets a user's bank balance
     *
     * @param guildId Discord ID of the guild the user is in
     * @param userId Discord ID of the user
     * @param bank Bank balance to set
     * @return Updated JUBAUser object. Unfortunately, at this time, this updated user object does not contain a leaderboard rank. It will be set as -1.
     */
    public JUBAAuditableRestAction<JUBAUser> setBankBalance(String guildId, String userId, long bank){
        Checks.isSnowflake(guildId, "Guild ID");
        Checks.isSnowflake(guildId, "User ID");

        JSONObject jsonObject = new JSONObject();
        jsonObject.append("bank", bank);

        return new JUBAAuditableRestAction<JUBAUser>(requester, Route.PUT_USER_BALANCE.compile(guildId, userId), jsonObject) {
            @Override
            protected JUBAUser handleResponse(Response response) {
                return entityBuilder.buildUser(response, guildId);
            }
        };
    }

    /**
     * Modifies a user's balance, adding or removing a set amount of money from their account
     *
     * @param guildId Discord ID of the guild the user is in
     * @param userId Discord ID of the user
     * @param cash Cash balance to modify
     * @param bank Bank balance to modify
     * @return Updated JUBAUser object. Unfortunately, at this time, this updated user object does not contain a leaderboard rank. It will be set as -1.
     */
    public JUBAAuditableRestAction<JUBAUser> modifyBalance(String guildId, String userId, long cash, long bank){
        Checks.isSnowflake(guildId, "Guild ID");
        Checks.isSnowflake(userId, "User ID");

        JSONObject jsonObject = new JSONObject();
        jsonObject.append("cash", cash);
        jsonObject.append("bank", bank);

        return new JUBAAuditableRestAction<JUBAUser>(requester, Route.PATCH_USER_BALANCE.compile(guildId, userId), jsonObject) {
            @Override
            protected JUBAUser handleResponse(Response response) {
                return entityBuilder.buildUser(response, guildId);
            }
        };
    }

    /**
     * Modifies a user's cash balance, adding or removing a set amount of money from their cash account
     *
     * @param guildId Discord ID of the guild the user is in
     * @param userId Discord ID of the user
     * @param cash Cash balance to modify
     * @return Updated JUBAUser object. Unfortunately, at this time, this updated user object does not contain a leaderboard rank. It will be set as -1.
     */
    public JUBAAuditableRestAction<JUBAUser> modifyCashBalance(String guildId, String userId, long cash){
        //Not calling modifyBalance() with bank as 0 because that'd still count as a modification in bank. Want to do exactly what the user needs.
        Checks.isSnowflake(guildId, "Guild ID");
        Checks.isSnowflake(userId, "User ID");

        JSONObject jsonObject = new JSONObject();
        jsonObject.append("cash", cash);

        return new JUBAAuditableRestAction<JUBAUser>(requester, Route.PATCH_USER_BALANCE.compile(guildId, userId), jsonObject) {
            @Override
            protected JUBAUser handleResponse(Response response) {
                return entityBuilder.buildUser(response, guildId);
            }
        };
    }

    /**
     * Modifies a user's bank balance, adding or removing a set amount of money from their bank account
     *
     * @param guildId Discord ID of the guild the user is in
     * @param userId Discord ID of the user
     * @param bank Bank balance to modify
     * @return Updated JUBAUser object. This updated user object does not contain a leaderboard rank, and will instead be set as -1.
     */
    public JUBAAuditableRestAction<JUBAUser> modifyBankBalance(String guildId, String userId, long bank){
        Checks.isSnowflake(guildId, "Guild ID");
        Checks.isSnowflake(userId, "User ID");

        JSONObject jsonObject = new JSONObject();
        jsonObject.append("bank", bank);

        return new JUBAAuditableRestAction<JUBAUser>(requester, Route.PATCH_USER_BALANCE.compile(guildId, userId), jsonObject) {
            @Override
            protected JUBAUser handleResponse(Response response) {
                return entityBuilder.buildUser(response, guildId);
            }
        };
    }

    /**
     * Gets a user's balance
     *
     * @param guildId Discord ID of the guild the user is in
     * @param userId Discord ID of the user
     * @return JUBAUser object
     */
    public JUBARestAction<JUBAUser> retrieveUser(String guildId, String userId){
        Checks.isSnowflake(guildId, "Guild ID");
        Checks.isSnowflake(userId, "User ID");

        return new JUBARestAction<JUBAUser>(requester, Route.GET_USER_BALANCE.compile(guildId, userId)) {
            @Override
            protected JUBAUser handleResponse(Response response) {
                return entityBuilder.buildUser(response, guildId);
            }
        };
    }

    /**
     * Updates the balances and rank for the specified user object
     *
     * @return Updated JUBAUser object
     */
    public JUBARestAction<JUBAUser> updateUser(JUBAUser user){
        return new JUBARestAction<JUBAUser>(requester, Route.GET_USER_BALANCE.compile(user.getGuildId(), user.getUserId())) {
            @Override
            protected JUBAUser handleResponse(Response response) {
                return entityBuilder.updateUser(user, response);
            }
        };
    }

    /**
     * Gets a guild's leaderboard and users
     *
     * @param guildId Discord ID of a guild
     * @return Updated guild object
     */
    public JUBARestAction<JUBAGuild> retrieveGuild(String guildId){
        Checks.isSnowflake(guildId, "Guild ID");

        return new JUBARestAction<JUBAGuild>(requester, Route.GET_GUILD_LEADERBOARD.compile(guildId)){
            @Override
            protected JUBAGuild handleResponse(Response response) {
                return entityBuilder.buildGuild(response, guildId);
            }
        };
    }

    /**
     * Updates the leaderboard of the specified guild object
     *
     * @return Updated JUBAGuild object
     */
    public JUBARestAction<JUBAGuild> updateGuild(JUBAGuild jubaGuild){
        return new JUBARestAction<JUBAGuild>(requester, Route.GET_GUILD_LEADERBOARD.compile(jubaGuild.getId())) {
            @Override
            protected JUBAGuild handleResponse(Response response) {
                return entityBuilder.updateGuild(jubaGuild, response);
            }
        };
    }

    /**
     * Gets information about a guild
     *
     * @param guildId Discord ID of a guild
     * @return JUBAGuildInfo object
     */
    public JUBARestAction<JUBAGuildInfo> retrieveGuildInfo(String guildId){
        Checks.isSnowflake(guildId, "Guild ID");

        return new JUBARestAction<JUBAGuildInfo>(requester, Route.GET_GUILD.compile(guildId)) {
            @Override
            protected JUBAGuildInfo handleResponse(Response response) {
                return entityBuilder.buildGuildInfo(response);
            }
        };
    }
}
