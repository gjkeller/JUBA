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

import itsgjk.juba.core.JUBA;
import itsgjk.juba.entity.JUBAUser;
import itsgjk.juba.exceptions.RateLimitException;

import java.util.List;

public class Example {

    /**
     * Program start
     */
    public static void main(String[] args){
        // Create JUBA instance to interact with UnbelievaBoat's API
        // We pass our API token to JUBA for it to authenticate
        JUBA juba = new JUBA("Your Token"); // Pass your UnbelievaBoat token (found at https://unbelievaboat.com/api/docs#Authorization)

        // Next, let's get a guild's (server) leaderboard.
        // NOTE the keyword retrieve. This usually means you are interacting directly with UnbelievaBoat's API, which takes time.
        // When doing this, we give you an object called JUBARestAction. This is not your final result! Using JUBARestAction, you
        // can chose how we get your result. Because you don't want to block your application's main thread while JUBA contacts UnbelievaBoat,
        // it is recommended you use the method .queue after a JUBARestAction. This will put your request in a queue, which will be completed
        // asynchronously.
        // Since queue() will not get your result immediately, you can specify what you want to be done with your result by using a Callback. (see below)
        juba.retrieveGuild("Guild ID").queue(
                guild -> // Lambda statement (Consumer taking the result)
                {
                    // Once JUBA receives a response from UnbelievaBoat's API, it will provide the result to the success Consumer. In this case, the result is a JUBAGuild.
                    List<JUBAUser> users = guild.getUsers(); // All the users in this guild's leaderboard
                    JUBAUser topUser = users.get(0); // The top user in this guild

                    System.out.println("This's guild's top user has " + topUser.getTotalBalance() + " money!"); // Get the total (cash+bank) balance from the top user and print it
                },
                error ->
                {
                    // Occasionally, queueing a request might result in an exception. The failure Consumer provides a Throwable.
                    // While JUBA can usually handle rate limits, it might still have an issue with them. Report any issues you encounter.
                    System.out.println("Something went wrong while finding this guild: " + error.getMessage());
                }
        );

        // If you don't want to mess with lambdas and queueing and would like more control over your code, such as how you handle rate limits, you can block the thread
        // until a request is complete by using complete()
        // JUBA will also not handle nay rate limits you may encounter, so expect them and know how to handle them.
        try{
            // This method will give your result, in this case a JUBAUser, however because we're using complete(), it'll block the thread to do so (which could take a while!)
            JUBAUser user = juba.retrieveUser("Guild ID", "User ID").complete();
            System.out.println("User " + user.getUserId() + " has " + user.getCashBalance() +  " cash and " + user.getBankBalance() + " in their bank!");
        }
        catch(RateLimitException ex){
            System.out.println("Encountered a ratelimit while trying to retrieve a user. Retry after: " + ex.getRetryAfter() + "ms");
        }
        catch(Throwable ex){
            System.out.println("Something went wrong while retrieving a user. I should report this on GitHub!");
            ex.printStackTrace();
        }

        // Although this is not on the API docs, UnbelievaBoat provides an endpoint to find information about a guild, such as its money symbol, guild name, etc.
        // This may be unreliable and could change in the future since it is not officially documented.
        juba.retrieveGuildInfo("Guild ID").queue(
                guildInfo ->
                {
                    String iconLink = guildInfo.getIconLink(); // The guild's icon
                    String moneySymbol = guildInfo.getSymbol(); // The guild's symbol for money. ($, an emoji code, etc.)
                    String name = guildInfo.getName(); // The guild's name
                }
        );
    }
}