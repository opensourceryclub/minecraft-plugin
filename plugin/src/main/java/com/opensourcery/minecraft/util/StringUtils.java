package com.opensourcery.minecraft.util;

import org.bukkit.ChatColor;

/**
 * Various string utilities
 * @author Wesley Smith
 */
public class StringUtils {

	/**
	 * Colorizes a string (translates & color codes to their in-game)
	 * counterpart.
	 * @param value The string to colorize
	 * @return The colorized string
	 */
	public static String colorize(String value) {
		if(value == null) {
			return null;
		}
		return ChatColor.translateAlternateColorCodes('&', value);
	}

}
