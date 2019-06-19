package itsgjk.juba.entity;

import java.util.List;

public class JUBAGuild {

    private String id;
    private List<JUBAUser> users;

    protected JUBAGuild(String id, List<JUBAUser> users){
        this.id = id;
        this.users = users;
    }

    /**
     * Discord Snowflake ID of the Guild
     *
     * @return String ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the users in the guild
     *
     * @return List of JUBAUser objects
     */
    public List<JUBAUser> getUsers() {
        return users;
    }
}
