package com.opensourcery.minecraft;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Server;
import java.util.HashMap;
import java.util.Scanner;

import java.io.*;
import java.net.*;
import java.util.Date;

public class SyncListener  extends BukkitRunnable {

	boolean hit;
	String pluginName;
	Server server;

    public SyncListener( Server _server ) {
		server = _server;
	}

	public void log ( String output) {
		System.out.println("[SyncListener] " + output);
	}

    public void run() {
		
		try {

			ServerSocket serverSock = new ServerSocket(8080);
			log("SyncListener open for requests");

			while ( true ) {
        
				Socket sock = serverSock.accept();

				InputStream sis = sock.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(sis));
				String request = br.readLine(); // Now you get GET index.html HTTP/1.1`
				String[] requestParam = request.split(" ");
				String path = requestParam[1];
				String[] parts = path.split("/");
	
				String plugin = parts[1];

				if ( plugin.equals("Ping")){
					Date today = new Date();
					String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
					sock.getOutputStream().write(httpResponse.getBytes("UTF-8"));
					continue;
				}
	
				log("Got request for restart of " + plugin  );
	
				hit = true;
				pluginName = plugin;

				Date today = new Date();
				String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
				sock.getOutputStream().write(httpResponse.getBytes("UTF-8"));
				
				serverSock.close();
				br.close();
			}

		} catch (Exception e) {
			log("Error processing refresh request");
			e.printStackTrace();
		}
	}
	
	public void syncProcess() {
		if ( hit ) {
			hit = false;
			server.dispatchCommand(server.getConsoleSender(), "say server reloading plugin \'" + pluginName + "\'");
			server.dispatchCommand(server.getConsoleSender(), "plugman reload " + pluginName);
		}
	}
}
