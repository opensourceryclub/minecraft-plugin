package com.opensourcery.minecraft.reload;

import com.opensourcery.minecraft.App;
import org.bukkit.Server;
import java.util.logging.Logger;
import org.bukkit.Bukkit;

public class RemoteReload {

	static Server server;
	static Logger logger;
	static SyncListener syncListener;
	static Thread syncThread;

	public static void initialize ( App plugin ) {

		server = Bukkit.getServer();
		logger = Bukkit.getLogger();

		syncListener = new SyncListener( server );
		syncThread = new Thread(syncListener);
		syncThread.start();
		
		server.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				syncListener.syncProcess();
			}}, 0, 10);
		
		logger.info("Initialized Remote Reload");

	}

	public static void disable () {
		syncThread.stop();
	}

}