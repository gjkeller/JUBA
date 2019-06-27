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

```coming soon```

**Download**

The compiled .jar version of JUBA is available on the [releases page](https://github.com/ItsGJK/JUBA/releases).

### Logging

JUBA uses [SLF4J](https://www.slf4j.org/) to log messages. If you do not include a SLF4J implementation in your build, it will print a message to console on startup:
```
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
```

JUBA provides its own default implementation if none is found. Keep in mind it is recommended you use your own SLF4J implementation for customization, logging to files, and/or increased logging speed. 

## Documentation

JavaDocs can be found at this [GitHub page]().
 > Currently, some parts may be incomplete, however, significant public classes such as JUBARestAction, JUBAUser, JUBAGuild, and JUBA have been documented. 

### Examples

You can view examples of how to use JUBA in the [examples directory]().

## Feedback

 This is my first library for an online API! I believe I've done a good job with writing professional, understandable, and functional code, however, if you find any issues or have an idea of a way to improve this library, [issue reports](https://github.com/ItsGJK/JUBA/issues), [feature requests](https://github.com/ItsGJK/JUBA/issues), and [PRs](https://github.com/ItsGJK/JUBA/pulls) are always welcome!
 
 You can contact me on Discord at `gabe#8005`, or join the official [UnbelievaBoat Discord server](https://discordapp.com/invite/YMJ2dGp) for general support regarding the bot and its API. 
 > Please note this server does **NOT** give support regarding this specific library. Please contact `gabe#8005` for anything relating to this library.

##Acknowledgements

Some of this library is inspired from [JDA](https://github.com/DV8FromTheWorld/JDA), a Java wrapper for Discord bots. Similar to JUBA, JDA uses RestAction to queue or complete requests asynchronously. I also took some
