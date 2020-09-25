# Opensourcery Minecraft Plugin

One of a few projects we are launching this semster that we hope you can all learn from and contribute to!

## Getting Setup

Plugin boilderplate made with [This](https://www.spigotmc.org/wiki/creating-a-blank-spigot-plugin-in-vs-code/) tutorial. For more details go there.

You will need:

- Java
- JDK
- Maven
- VSCode ( recomended )
- Minecraft 
- Java extentions for vs code ( recomended )

To make sure you have the above installed, make sure you can type `java` and `mvn` into your terminal and see valid outputs. If either is not a registered command then it is not installed

To initialize the server:
```
> start.bat
```
You will have to accept the eula and then start it again

After that you are fully set up!

## Development

The script `build.bat` will:

- Build the plugin into a .jar file
- Move it to the server/plugins folder
- Turn off the old version on the server
- Load in the newly build plugin

The server will `NOT` need to be restarted after every build. Turn it on once at the start, then you are fine.

The script `start.bat` will:

- Turn on the server locally

Make sure the server is `ONLINE` before you run `build.bat`

To connect to the server type in `localhost` into the ip slot on minecraft