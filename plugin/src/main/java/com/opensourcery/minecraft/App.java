package com.opensourcery.minecraft;

import com.opensourcery.minecraft.modules.MobsModule;
import com.opensourcery.minecraft.reload.RemoteReload;
import com.opensourcery.minecraft.modules.AbstractModule;
import com.opensourcery.minecraft.modules.JoinLeaveMessagesModule;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The main entry point for the plugin
 */
public final class App extends JavaPlugin {

	/**
	 * All registered modules
	 */
	private final Set<AbstractModule> availableModules = new HashSet<>();
	/**
	 * All of the currently enabled modules
	 */
	private final Set<AbstractModule> enabledModules = new HashSet<>();

	/**
	 * The main entry point for the plugin. The server software will
	 * call this method for us at the correct time during server
	 * startup.
	 *
	 * All modules should be registered here.
	 */
	@Override
	public void onEnable() {
		super.saveDefaultConfig();
		RemoteReload.initialize(this);

		registerModule(JoinLeaveMessagesModule.class);
		registerModule(MobsModule.class);

		getAvailableModules().stream()
				.filter(module -> getConfig().getBoolean("modules." + module.getName() + ".enabled", false))
				.forEach(this::enableModule);
	}

	/**
	 * The exit point for the plugin. The server software will
	 * call this method for us at the correct time during server
	 * shutdown.
	 */
	@Override
	public void onDisable() {
		disableAllModules();
		RemoteReload.disable();
	}

	/**
	 * Get all of the registered modules
	 * @return An unmodifiable set of available modules
	 */
	public Set<AbstractModule> getAvailableModules() {
		return Collections.unmodifiableSet(availableModules);
	}

	/**
	 * Get all of the enabled modules
	 * @return An unmodifiable set of enabled modules
	 */
	public Set<AbstractModule> getEnabledModules() {
		return Collections.unmodifiableSet(enabledModules);
	}

	/**
	 * Enable a module
	 * @param module The module instance to enable
	 */
	public void enableModule(AbstractModule module) {
		module.enable();
		enabledModules.add(module);
	}

	/**
	 * Disable a module
	 * @param module The module instance to disable
	 */
	public void disableModule(AbstractModule module) {
		module.disable();
		enabledModules.remove(module);
	}

	/**
	 * Disable all currently enabled modules
	 */
	public void disableAllModules() {
		enabledModules.forEach(this::disableModule);
	}

	/**
	 * Registers a new module. This method makes it as simple as calling
	 * {@code registerModule(MyModuleClass.class)} to register modules. It
	 * will dynamically get the configuration section for the module and
	 * choose the default module constructor. If you need to specify
	 * more information when creating a module, you need to register it
	 * manually using {@link App#registerModule(AbstractModule...)}
	 * @param clazz The class of the module to register
	 * @throws RuntimeException if the module is unable to load for any
	 * 							reason
	 */
	private void registerModule(Class<? extends AbstractModule> clazz) {
		final String moduleName = AbstractModule.getName(clazz);
		final ConfigurationSection config = super.getConfig().getConfigurationSection("modules." + moduleName + ".config");
		try {
			final Constructor<? extends AbstractModule> constructor = clazz.getConstructor(App.class, ConfigurationSection.class);
			registerModule(constructor.newInstance(this, config));
		} catch (Throwable rock) {
			throw new RuntimeException("Unable to load module \"" + moduleName + "\"", rock);
		}
	}

	/**
	 * Manually registers a module
	 * @param modules The module instance to register
	 */
	private void registerModule(AbstractModule... modules) {
		availableModules.addAll(Arrays.asList(modules));
	}

}