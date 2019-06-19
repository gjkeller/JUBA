package itsgjk.juba.entity;

/**
 * Class representing a user's money on a specified guild, including their cash, bank, and total balance, and rank on the guild's leaderboard.
 * <p>Sometimes the UnbelievaBoat API does not return the user's rank on the guild. If this happens, their rank is represented as -1.
 * The same user may have multiple JUBAUser objects for multiple guilds. This object is NOT updated when their balance changes due to them using,
 * the work command, talking in chat, etc., instead you must get another instance of the user object to have their updated balance.</p>
 */
public class JUBAUser {

    private String guildId;
    private String userId;
    private int rank;
    private long cashBalance;
    private long bankBalance;
    private long totalBalance;

    protected JUBAUser(String guildId, String userId, int rank, long cashBalance, long bankBalance, long totalBalance){
        this.guildId = guildId;
        this.userId = userId;
        this.rank = rank;
        this.cashBalance = cashBalance;
        this.bankBalance = bankBalance;
        this.totalBalance = totalBalance;
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
     * @return
     */
    public long getTotalBalance() {
        return totalBalance;
    }
}
