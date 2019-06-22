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
import itsgjk.juba.requests.JUBARestAction;

import java.util.List;

/**
 * Class representing a Discord guild's UnbelievaBoat leaderboard, containing a list of users in the guild and their balances/rank.
 * The list of users is sorted from rank 1, the highest rank, (index 0) to the lowest rank
 */
public class JUBAGuild {

    private JUBA juba;
    private String id;
    private List<JUBAUser> users;

    protected JUBAGuild(JUBA juba, String id, List<JUBAUser> users){
        this.juba = juba;
        this.id = id;
        this.users = users;
    }

    /**
     * Updates the leaderboard of this guild object
     *
     * @return This object after being updated
     */
    public JUBARestAction<JUBAGuild> update(){
        return juba.updateGuild(this);
    }

    /**
     * Gets information about the guild
     *
     * @return JUBAGuildInfo object
     */
    public JUBARestAction<JUBAGuildInfo> retrieveInfo(){
        return juba.retrieveGuildInfo(id);
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

    /**
     * This's object's JUBA instance
     *
     * @return JUBA object
     */
    public JUBA getJuba() {
        return juba;
    }

    protected void setUsers(List<JUBAUser> users){
        this.users = users;
    }
}
