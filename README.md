> This readme is not complete. Links may be broken and content could be missing.

[![License](https://img.shields.io/github/license/ItsGJK/JUBA.svg?style=flat-square)](https://img.shields.io/github/license/ItsGJK/JUBA.svg?style=flat-square) [![Release](https://img.shields.io/github/release/ItsGJK/JUBA.svg?style=flat-square)]((https://img.shields.io/github/release/ItsGJK/JUBA.svg?style=flat-square)) [![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com) [![chat discord](https://img.shields.io/badge/chat-discord-blue.svg?style=flat-square)](https://img.shields.io/badge/chat-discord-blue.svg?style=flat-square)
# JUBA (Java UnbelievaBoat API)
The Java library for popular Discord bot [UnbelievaBoat](https://unbelievaboat.com/)! UnbelievaBoat is a bot in Discord with several features, some of which relating to economies in guilds.

The UnbelievaBoat API currently supports controlling a guild's economy. This library uses `v1` of the API. New functionality may come to this API in the future, and will be added to JUBA shortly after.

Using JUBA, you can:
 - View user balances
 - View guild leaderboards
 - View guild information (name, icon, money symbol)
 - Edit user balances

## Installation

**Maven**

```xml
<!--coming soon-->
```

**Download**

The compiled .jar version of JUBA is available on the [releases page](https://github.com/ItsGJK/JUBA/releases).

### Logging

JUBA uses [SLF4J](https://www.slf4j.org/) to log messages. If you do not include a SLF4J implementation in your build, it will print a warning to console on startup:
```
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
```

JUBA provides its own default implementation if none is found. Keep in mind it is recommended you use a different SLF4J implementation for customization, logging to files, and higher efficiency . 

## Documentation

JavaDocs can be found at this [GitHub page]().
 > Currently, some parts may be incomplete, however, significant public classes such as JUBARestAction, JUBAUser, JUBAGuild, and JUBA have been documented. 

### JUBARestAction

Every action in JUBA that has to contact UnbelievaBoat's API returns a `JUBARestAction` object. This is not your result, but a gateway to determining how JUBA will retrieve your result.

The recommended way to handle `JUBARestAction` is to call the method `queue()`, which will put the request in a queue that will be executed asynchronously. 

In order to use the result from a `JUBARestAction`, you must provide a success `Consumer` in the parameters for the method. You may also provide a failure `Consumer`, given an error or exception occurs.

Here's an example of how to print a user's balance using `queue()`:
```java
public static void printBalance(JUBA juba, String guildId, String userId){
    juba.retrieveUser(guildId, userId).queue( // Get JUBAUser object
            success -> // Success Consumer
            {
                // This will be executed once the result is received
                System.out.println(success.getTotalBalance()); // Print total balance
            }
    );
}
```

If you'd like more control over your request and rate limiting or would like ease of use, you can call `complete()` to block the thread until the result is received.

For actions that are logged in UnbelievaBoat's Audit Logs, `JUBAAuditableRestAction` is returned. In order to set a reason, use `reason(String reason)` before queueing/completing.

### Examples

You can view more commented examples of how to use JUBA in the [examples directory](https://github.com/ItsGJK/JUBA/tree/v1/src/example/java).

## Feedback

 This is my first library for a REST API! I believe I've done a good job with writing professional, understandable, and functional code, however, if you find any issues or have an idea of a way to improve this library, [issue reports](https://github.com/ItsGJK/JUBA/issues), [feature requests](https://github.com/ItsGJK/JUBA/issues), and [PRs](https://github.com/ItsGJK/JUBA/pulls) are always welcome!
 
 You can contact me on Discord at `gabe#8005`, or join the official [UnbelievaBoat Discord server](https://discordapp.com/invite/YMJ2dGp) for general support regarding the bot and its API. 
 > Please note this server does **NOT** give support regarding this specific library. Please contact `gabe#8005` for anything relating to this library.

## Acknowledgements

Some aspects of this library are inspired from [JDA](https://github.com/DV8FromTheWorld/JDA), a Java wrapper for making Discord bots. Similar to JUBA, JDA uses RestAction to queue or complete requests asynchronously. I also took some inspiration from their logging implementation, where they provide a fallback logger when no StaticLoggerBinder is found. This is my first time working with SLF4J, so this may be a normal thing for APIs to do, but I'd just like to give credit where its due.
