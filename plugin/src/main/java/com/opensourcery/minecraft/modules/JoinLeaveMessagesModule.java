package com.opensourcery.minecraft.modules;

import com.opensourcery.minecraft.App;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.opensourcery.minecraft.util.StringUtils.colorize;

/**
 * A module to customize the join and leave messages
 * on the server.
 *
 * @author Wesley Smith
 */
public class JoinLeaveMessagesModule extends AbstractModule {

	/**
	 * {@inheritDoc}
	 */
	public JoinLeaveMessagesModule(App plugin, ConfigurationSection config) {
		super(plugin, config);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.setJoinMessage(getMessage("join", event));
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		event.setQuitMessage(getMessage("quit", event));
	}

	/**
	 * Get, colorize, and replace variables in a config message.
	 * @param name The name of the config message.
	 * @param event The {@link PlayerEvent} that this message is used for
	 * @return The formatted message
	 */
	private String getMessage(String name, PlayerEvent event) {
		final String value = super.config.getString(name, null);
		if(value == null) {
			return null;
		}

		return colorize(value.replace("%player%", event.getPlayer().getName()));
	}

}
