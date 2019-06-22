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

/**
 * Class representing a user's money on a specified guild, including their cash, bank, and total balance, and rank on the guild's leaderboard.
 * <p>Sometimes the UnbelievaBoat API does not return the user's rank on the guild. If this happens, their rank is represented as -1.
 * The same user may have multiple JUBAUser objects for multiple guilds. This object is NOT updated when their balance changes due to them using,
 * the work command, talking in chat, etc., instead you must call JUBAUser.update() to update this instance.</p>
 */
public class JUBAUser {

    private JUBA juba;
    private String guildId;
    private String userId;
    private int rank;
    private long cashBalance;
    private long bankBalance;
    private long totalBalance;

    protected JUBAUser(JUBA juba, String guildId, String userId, int rank, long cashBalance, long bankBalance, long totalBalance){
        this.juba = juba;
        this.guildId = guildId;
        this.userId = userId;
        this.rank = rank;
        this.cashBalance = cashBalance;
        this.bankBalance = bankBalance;
        this.totalBalance = totalBalance;
    }

    /**
     * Updates the balances and rank for this user object
     *
     * @return This object after being updated
     */
    public JUBARestAction<JUBAUser> update(){
        return juba.updateUser(this);
    }

    /**
     * The Discord ID of the Guild containing this user
     *
     * @return String ID
     */
    public String getGuildId() {
        return guildId;
    }

    /**
     * The Discord ID of the user
     *
     * @return String ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * The rank of the user on the guild's leaderboard
     *
     * @return Integer rank (1 is highest position)
     */
    public int getRank() {
        return rank;
    }

    /**
     * The amount of money this user has in cash
     * If this user has Infinity as this balance, it will be represented as Long.MAX_VALUE
     *
     * @return Long cash balance
     */
    public long getCashBalance() {
        return cashBalance;
    }

    /**
     * The amount of money this user has in their bank
     * If the user has Infinity as this balance, it will be represented as Long.MAX_VALUE
     *
     * @return Long bank balance
     */
    public long getBankBalance() {
        return bankBalance;
    }

    /**
     * The amount of money this user has total, in both cash and bank accounts.
     * If cash OR bank are Infinity (Long.MAX_VALUE) this will be Infinity.
     *
     * @return Long total balance
     */
    public long getTotalBalance() {
        return totalBalance;
    }

    /**
     * This's object's JUBA instance
     *
     * @return JUBA object
     */
    public JUBA getJuba() {
        return juba;
    }

    protected void setRank(int rank) {
        this.rank = rank;
    }

    protected void setCashBalance(long cashBalance) {
        this.cashBalance = cashBalance;
    }

    protected void setBankBalance(long bankBalance) {
        this.bankBalance = bankBalance;
    }

    protected void setTotalBalance(long totalBalance) {
        this.totalBalance = totalBalance;
    }
}
