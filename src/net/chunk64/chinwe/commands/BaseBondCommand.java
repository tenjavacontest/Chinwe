package net.chunk64.chinwe.commands;

import net.chunk64.chinwe.util.BondUtils;
import net.chunk64.chinwe.util.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

public abstract class BaseBondCommand implements CommandExecutor
{
	protected String command;
	private boolean playerOnly;
	private Permission perm;

	public BaseBondCommand(PluginCommand command, boolean playerOnly, Permission perm)
	{
		this.command = command.getName();
		this.playerOnly = playerOnly;
		this.perm = perm;

		command.setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		try
		{
			if (cmd.getName().equalsIgnoreCase(command))
			{
				// player only
				if (playerOnly && !CommandUtils.isPlayer(sender))
					return true;

				// permission check
				if (!CommandUtils.hasPermission(sender, perm))
					return true;

				// run command
				run(sender, cmd, args);

				return true;
			}


		} catch (IllegalArgumentException e)
		{
			BondUtils.message(sender, "&c" + e.getMessage());
		} catch (IncorrectUsageException e)
		{
			CommandUtils.sendUsage(sender, cmd);
		} catch (Exception e)
		{
			BondUtils.message(sender, "&cError: " + (e.getMessage() == null ? e : e.getMessage()));
		}
		return true;
	}

	/**
	 * Called when the command is run
	 */
	abstract void run(CommandSender sender, Command cmd, String[] args) throws Exception;
}
