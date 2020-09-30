package com.opensourcery.minecraft;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.Particle;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.io.*;

public class MobManager {

	static Server server;
	static Logger logger;

	public static void initialize ( JavaPlugin plugin ) {

		server = Bukkit.getServer();
		logger = Bukkit.getLogger();

		summonSpecialZombie( plugin );

	}

	public static void summonSpecialZombie ( JavaPlugin plugin) {
		
		World w = server.getWorld("world");
		Location loc=new Location(w,0,68,0);
		Zombie z = (Zombie) w.spawnEntity(loc,EntityType.ZOMBIE);
		z.setCustomName( "Bob" );
		z.setCustomNameVisible(true);
		z.setBaby(false);
		EntityEquipment equipment = z.getEquipment();
		equipment.setItemInHand(new ItemStack(Material.STONE,1));
		
		MyTask task = new MyTask( z, plugin );
		task.runTaskTimer(plugin, 0L, 1L);
	}

	public static void disable () {
	}

	
}


class MyTask extends BukkitRunnable {
	private Zombie z;
	private JavaPlugin plugin;
	private int length;
	public MyTask( Zombie z, JavaPlugin plugin ){
		this.z = z;
		this.plugin = plugin;
		length = 0;
	}
	@Override
	public void run(){
		length = (length + 5) % 300;
		for ( int i = 0; i < length ; i ++ ) {
			Bukkit.getServer().getWorld("world").spawnParticle(Particle.SPELL_MOB, z.getLocation().add(0,(300/5) - (i/5.0),0), 0, length/300.0, 0.1, 0, 1);
		}								
		for ( int i = 0; i < 2 ; i ++ )				   
			Bukkit.getServer().getWorld("world").spawnParticle(Particle.EXPLOSION_LARGE, z.getLocation().add(0,(300/5) - (length/5.0),0), 0, length/300.0, 0.1, 0, 1);
		if ( length == 295 ){
			for ( int i = 0; i < 15 ; i ++ )		
				Bukkit.getServer().getWorld("world").spawnParticle(Particle.EXPLOSION_HUGE, z.getLocation().add(0,(300/5) - (length/5.0),0), 0, length/300.0, 0.1, 0, 1);
		}
	}
}
