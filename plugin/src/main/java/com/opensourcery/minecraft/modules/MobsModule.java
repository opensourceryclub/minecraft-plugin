package com.opensourcery.minecraft.modules;

import com.opensourcery.minecraft.App;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MobsModule extends AbstractModule {

	/**
	 * {@inheritDoc}
	 */
	public MobsModule(App plugin, ConfigurationSection config) {
		super(plugin, config);
	}

	@Override
	protected void onEnable() {
		World w = Bukkit.getServer().getWorld("world");
		Location loc=new Location(w,0,68,0);
		Zombie z = (Zombie) w.spawnEntity(loc, EntityType.ZOMBIE);
		z.setCustomName( "Bob" );
		z.setCustomNameVisible(true);
		z.setBaby(false);
		EntityEquipment equipment = z.getEquipment();
		equipment.setItemInHand(new ItemStack(Material.STONE,1));

		MyTask task = new MyTask( z, plugin );
		task.runTaskTimer(plugin, 0L, 1L);
	}

	private class MyTask extends BukkitRunnable {
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

}
