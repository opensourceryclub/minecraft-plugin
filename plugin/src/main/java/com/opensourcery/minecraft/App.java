package com.opensourcery.minecraft;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Server;
import java.io.*;

public class App extends JavaPlugin {

	SyncListener syncListener;
	Thread syncThread;
	Server server;

	@Override
    public void onEnable() {

		server = getServer();

        getLogger().info("Update Without Restart! Yay!");

		/* Checkin with api request */
		syncListener = new SyncListener( server );
        syncThread = new Thread(syncListener);
		syncThread.start();
		
		server.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				syncListener.syncProcess();
			}}, 0, 10);
		
    }
    @Override
    public void onDisable() {
        getLogger().info("See you again, SpigotMC!");
        syncThread.stop();
    }
}