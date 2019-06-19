package itsgjk.juba.entity;

import itsgjk.juba.core.JUBA;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EntityBuilder {
    public static JUBAUser buildUser(Response response, String guildId){
        //Get JSON body
        String body;
        try{
            body = response.body().string();
        }
        catch(Throwable ex){
            JUBA.LOG.error("Failed to build JUBAUser object:", ex);
            return null;
        }

        return buildUser(new JSONObject(body), guildId);
    }

    public static JUBAUser buildUser(JSONObject userJson, String guildId){
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

        return new JUBAUser(guildId, userId, 0, cash, bank, total);
    }

    public static JUBAGuild buildGuild(Response response, String guildId){
        //Get JSON body
        String body;
        try{
            body = response.body().string();
        }
        catch(Throwable ex){
            JUBA.LOG.error("Failed to build JUBAGuild object:", ex);
            return null;
        }

        JSONArray guildJson = new JSONArray(body);
        //Start building list of users in the Guild
        List<JUBAUser> list = new ArrayList<>();
        for(int i = 0; i < guildJson.length(); i++){
            JSONObject userJson = guildJson.getJSONObject(i);
            list.add(buildUser(userJson, guildId));
        }

        return new JUBAGuild(guildId, list);
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
}
