package com.opensourcery.minecraft.modules;

import com.opensourcery.minecraft.App;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 * Represents a module of the plugin that is able to be
 * enabled and disabled via a command ingame and via
 * the config file.
 *
 * @author Wesley Smith
 */
public abstract class AbstractModule implements Listener {

	/**
	 * The {@link ConfigurationSection} that contain's this
	 * module's config
	 */
	protected final ConfigurationSection config;
	/**
	 * The plugin that this module is a part of
	 */
	protected final App plugin;

	/**
	 * Creates a new module instance
	 * @param plugin The plugin running this module
	 * @param config The {@link ConfigurationSection} that contain's this
	 *               module's config
	 */
	public AbstractModule(App plugin, ConfigurationSection config) {
		this.plugin = plugin;
		this.config = config;
	}

	/**
	 * Called when this module is enabled. Use it in superclasses
	 * to do specific handling when the module is about to be
	 * used.
	 */
	protected void onEnable() {}

	/**
	 * Enable the module. This method will register all events
	 * in the class, call {@link AbstractModule#onEnable()}, and
	 * then log the time it took to do both.
	 */
	public final void enable() {
		final long startTime = System.currentTimeMillis();

		Bukkit.getPluginManager().registerEvents(this, plugin);
		onEnable();

		log("Enabled in " + (System.currentTimeMillis() - startTime) + "ms");
	}

	/**
	 * Called when this module is disabled. Use it in superclasses
	 * to do specific handling when the module no longer allowed to be
	 * enabled.
	 */
	protected void onDisable() {}

	/**
	 * Disables the module. This method will unregister all events
	 * in the class, call {@link AbstractModule#onDisable()}, and
	 * then log the time it took to do them.
	 */
	public final void disable() {
		final long startTime = System.currentTimeMillis();

		onDisable();
		HandlerList.unregisterAll(this);

		log("Disabled in " + (System.currentTimeMillis() - startTime) + "ms");
	}

	/**
	 * See {@link AbstractModule#getName(Class)}
	 */
	public final String getName() {
		return getName(getClass());
	}

	/**
	 * Get the name of this module. The name is created by the Simple Class Name
	 * of the current class with "Module" chopped off the end.
	 * @param clazz The class to generate the name from
	 * @return The name of the module, as described above
	 */
	public static String getName(Class<? extends AbstractModule> clazz) {
		final String name = clazz.getSimpleName();
		return name.substring(0, name.length() - "Module".length());
	}

	/**
	 * Logs a message to console that displays the module name
	 * with it.
	 * @param message The message to log
	 */
	protected final void log(String message) {
		plugin.getLogger().info("[" + getClass().getSimpleName() + "] " + message);
	}

}
