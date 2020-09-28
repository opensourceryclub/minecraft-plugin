package com.opensourcery.minecraft;

import org.bukkit.Server;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Server;
import java.io.*;

public class RemoteReload {

	static Server server;
	static Logger logger;
	static SyncListener syncListener;
	static Thread syncThread;

	public static void initialize (  ) {

		server = Bukkit.getServer();
		logger = Bukkit.getLogger();

		syncListener = new SyncListener( server );
		syncThread = new Thread(syncListener);
		syncThread.start();
		
		server.getScheduler().scheduleSyncRepeatingTask(App.plugin, new Runnable() {
			public void run() {
				syncListener.syncProcess();
			}}, 0, 10);
		
		logger.info("Initialized Remote Reload");

	}

	public static void disable () {
		syncThread.stop();
	}

}