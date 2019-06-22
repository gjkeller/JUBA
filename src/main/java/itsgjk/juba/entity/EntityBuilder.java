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

package itsgjk.juba.entity;

import itsgjk.juba.core.JUBA;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EntityBuilder {

    private JUBA juba;

    public EntityBuilder(JUBA juba){
        this.juba = juba;
    }

    public JUBAUser buildUser(Response response, String guildId){
        JSONObject jsonObject = getJsonObject(response);

        return buildUser(jsonObject, guildId);
    }

    public JUBAUser buildUser(JSONObject userJson, String guildId){
        //Convert body to JSON object and start preparing fields
        String userId = userJson.getString("user_id");
        int rank = -1;
        try{
            rank = userJson.getInt("rank");
        }
        //Rank wasn't included in the user object. This is (for some reason) optionally returned. Keeping at -1
        catch(JSONException ex){}
        long cash = getNumberBalance(userJson, "cash");
        long bank = getNumberBalance(userJson, "bank");
        long total = getNumberBalance(userJson, "total");

        return new JUBAUser(juba, guildId, userId, rank, cash, bank, total);
    }

    public JUBAUser updateUser(JUBAUser jubaUser, Response response){
        JSONObject jsonObject = getJsonObject(response);

        return updateUser(jubaUser, jsonObject);
    }

    public JUBAUser updateUser(JUBAUser jubaUser, JSONObject userJson){
        String userId = userJson.getString("user_id");
        int rank = -1;
        try{
            rank = userJson.getInt("rank");
        }
        //Rank wasn't included in the user object. This is (for some reason) optionally returned. Keeping at -1
        catch(JSONException ex){}
        long cash = getNumberBalance(userJson, "cash");
        long bank = getNumberBalance(userJson, "bank");
        long total = getNumberBalance(userJson, "total");

        jubaUser.setRank(rank);
        jubaUser.setCashBalance(cash);
        jubaUser.setTotalBalance(total);

        return jubaUser;
    }

    public JUBAGuild buildGuild(Response response, String guildId){
        //Get JSON array
        JSONArray guildJson = getJsonArray(response);

        //Start building list of users in the Guild
        List<JUBAUser> list = new ArrayList<>();
        for(int i = 0; i < guildJson.length(); i++){
            JSONObject userJson = guildJson.getJSONObject(i);
            list.add(buildUser(userJson, guildId));
        }

        return new JUBAGuild(juba, guildId, list);
    }

    public JUBAGuild updateGuild(JUBAGuild jubaGuild, Response response){
        //Get JSON array
        JSONArray jsonArray = getJsonArray(response);

        return updateGuild(jubaGuild, jsonArray);
    }

    public JUBAGuild updateGuild(JUBAGuild jubaGuild, JSONArray jsonArray){
        //Start building list of users in the Guild
        List<JUBAUser> list = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject userJson = jsonArray.getJSONObject(i);
            list.add(buildUser(userJson, jubaGuild.getId()));
        }

        jubaGuild.setUsers(list);

        return jubaGuild;
    }

    public JUBAGuildInfo buildGuildInfo(Response response){
        JSONObject jsonObject = getJsonObject(response);

        return buildGuildInfo(jsonObject);
    }

    public JUBAGuildInfo buildGuildInfo(JSONObject jsonObject){
        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String icon = jsonObject.getString("icon");
        String symbol = jsonObject.getString("symbol");

        return new JUBAGuildInfo(id, name, icon, symbol);
    }

    private static long getNumberBalance(JSONObject obj, String name){
        try{
            return obj.getLong(name);
        }
        catch(JSONException ex){
            //If the input wasn't a long, I'm assuming it'll be a String saying "Infinity".
            return Long.MAX_VALUE;
        }
    }

    private static String getContent(Response response){
        String body;
        try{
            body = response.body().string();
        }
        catch(Throwable ex){
            JUBA.LOG.error("Failed to build object:", ex);
            throw new IllegalArgumentException("Failed to build object", ex);
        }

        return body;
    }

    private static JSONObject getJsonObject(Response response){
        String body = getContent(response);

        return new JSONObject(body);
    }

    private static JSONArray getJsonArray(Response response){
        String body = getContent(response);

        return new JSONArray(body);
    }
}
