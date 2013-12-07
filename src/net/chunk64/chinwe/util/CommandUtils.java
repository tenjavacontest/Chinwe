package net.chunk64.chinwe.util;

import net.chunk64.chinwe.commands.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUtils
{
	/**
	 * Returns false and messages a commandsender if they aren't a player, otherwise true
	 */
	public static boolean isPlayer(CommandSender sender)
	{
		boolean player = sender instanceof Player;
		if (!player)
			BondUtils.message(sender, "&cOnly players can do that!");
		return player;
	}

	/**
	 * Returns false and messages a player if they don't have a specific permission, otherwise true
	 */
	public static boolean hasPermission(CommandSender sender, Permission perm)
	{
		boolean hasPerm = sender.hasPermission(perm.getPermission());
		if (!hasPerm)
			BondUtils.message(sender, "§cYou can't " + perm.getMessage() + ", you need §6" + perm.getPermission().getName());

		return hasPerm;
	}

	/**
	 * Sends command usage
	 */
	public static void sendUsage(CommandSender sender, Command cmd)
	{
		BondUtils.message(sender, "&cUsage: " + (cmd.getUsage() != null ? cmd.getUsage() : "/" + cmd.getName()));
	}


}
