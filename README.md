[![Release](https://img.shields.io/github/release/ItsGJK/JUBA.svg?style=flat-square)](https://github.com/ItsGJK/JUBA/releases) [![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com) [![chat discord](https://img.shields.io/badge/chat-discord-blue.svg?style=flat-square)](https://discordapp.com/invite/YMJ2dGp) [![License](https://img.shields.io/github/license/ItsGJK/JUBA.svg?style=flat-square)](https://github.com/ItsGJK/JUBA/blob/v1/LICENSE)
# JUBA (Java UnbelievaBoat API)
JUBA is a Java library for the popular Discord bot [UnbelievaBoat](https://unbelievaboat.com/)! UnbelievaBoat is a bot in Discord offering several features, some of which relating to having custom economies in guilds.

The UnbelievaBoat API currently supports controlling a guild's economy. This library uses version `v1` of the API. New functionality may come to this API in the future, and will be added to JUBA shortly after.

Using JUBA, you can:
 - View user balances
 - Edit user balances
 - View guild leaderboards
 - View guild information (name, icon, money symbol)

## Installation

**Maven**

```xml
<!--coming soon-->
```

**Download**

The compiled .jar version of JUBA is available on the [releases page](https://github.com/ItsGJK/JUBA/releases).

### Logging

JUBA uses [SLF4J](https://www.slf4j.org/) to log messages. If you do not include an SLF4J implementation in your build, it will print a warning to console on startup:
```
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
```

JUBA provides its own default implementation if none is found. Keep in mind it is recommended you use a different SLF4J implementation for customization, logging to files, and higher efficiency. 

## Documentation

JavaDocs can be found at this [GitHub page]().
 > Currently, some parts may be incomplete, however, significant public classes such as JUBARestAction, JUBAUser, JUBAGuild, and JUBA have been documented. 

### JUBARestAction

Every action in JUBA that have to contact UnbelievaBoat's API returns a `JUBARestAction` object. This is not your result, but a gateway to determining how JUBA will retrieve your result.

The recommended way to handle `JUBARestAction` is to call the method `queue()`, which will put the request in a queue that will be executed asynchronously. 

In order to use the result from a `JUBARestAction`, since `queue()` returns `void`, you must provide a success `Consumer` in the parameters for the method. You may also provide a failure `Consumer`, given an error or exception occurs.

Here's an example of how to print a user's balance using `queue()`:
```java
public static void printBalance(JUBA juba, String guildId, String userId){
    juba.retrieveUser(guildId, userId).queue( // Get a JUBAUser object. Since this returns a JUBARestAction, we must queue it
            success -> // Success Consumer. "success" is a JUBAUser
            {
                // This will be executed once the result is received
                System.out.println(success.getTotalBalance()); // Print total balance
            }
    );
}
```

If you'd like more control over your request and rate limiting, or would like ease of use, you can call `complete()` to block the thread until the result is received.

For actions that are logged in UnbelievaBoat's Audit Logs, `JUBAAuditableRestAction` is returned. In order to set a reason, use `reason(String)` before queueing/completing.

### Examples

You can view more commented examples of how to use JUBA in the [examples directory](https://github.com/ItsGJK/JUBA/tree/v1/src/example/java).

## Feedback

 This is my first library for a REST API! I've tried my best to write professional, understandable, and functional code, however, if you find any issues or have an idea of a way to improve this library, [issue reports](https://github.com/ItsGJK/JUBA/issues), [feature requests](https://github.com/ItsGJK/JUBA/issues), and [PRs](https://github.com/ItsGJK/JUBA/pulls) are always welcome!
 
 If you have any questions and want to contact me on Discord, you can do so at `gabe#8005`, or join the official [UnbelievaBoat Discord server](https://discordapp.com/invite/YMJ2dGp) for general support regarding the bot and its API. 
 > **Note:** The official UnbelievaBoat server does **NOT** give support regarding this specific library. Please contact `gabe#8005` for anything specifically relating to this library.

## Acknowledgements

Some aspects of this library were inspired from [JDA](https://github.com/DV8FromTheWorld/JDA), a Java wrapper for making Discord bots. Similar to JUBA, JDA uses RestAction to complete or queue requests asynchronously. I also took some inspiration from their logging implementation, where they provide a fallback logger when no StaticLoggerBinder is found.
