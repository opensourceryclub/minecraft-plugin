package com.opensourcery.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Server;
import java.util.logging.Logger;
import java.io.*;

public class App extends JavaPlugin {

	public static JavaPlugin plugin;
	Logger logger;

	@Override
	public void onEnable() {
		plugin = this;
		logger = Bukkit.getLogger();

		RemoteReload.initialize();
		MobManager.initialize ( plugin );
		
		logger.info("Plugin Enabled");
	}
	@Override
	public void onDisable() {
		RemoteReload.disable();
		MobManager.disable();
		logger.info("Plugin Disabled");
	}
}