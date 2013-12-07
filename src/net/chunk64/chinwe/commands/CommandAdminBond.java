package net.chunk64.chinwe.commands;

import net.chunk64.chinwe.Agent;
import net.chunk64.chinwe.util.BondUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class CommandAdminBond extends BaseBondCommand
{
	public CommandAdminBond(PluginCommand command, boolean playerOnly, Permission perm)
	{
		super(command, playerOnly, perm);
	}

	@Override
	void run(CommandSender sender, Command cmd, String[] args) throws Exception
	{

		// help
		if (args.length == 0)
		{
			BondUtils.message(sender, "&2--- &aBondAdmin Help");
			BondUtils.message(sender, "&b/bondadmin...");
			BondUtils.message(sender, "&3hire [player] &7Hire a player.");
			BondUtils.message(sender, "&3fire [player] &7Fire a player.");
			BondUtils.message(sender, "&2--- &aEnd of Help");
			return;
		}

		// hire/fire
		if (args.length <= 2)
		{
			boolean hire = args[0].equalsIgnoreCase("hire");

			if (!hire && !args[0].equalsIgnoreCase("fire"))
				throw new IncorrectUsageException();

			// not enough args
			if (args.length != 2)
				throw new IncorrectUsageException();

			Player player = Bukkit.getPlayer(args[1]);
			if (player == null)
				throw new IllegalArgumentException("'" + args[1] + "' is not online!");

			// can't hire yourself
			if (player == sender)
				throw new IllegalArgumentException("You can't " + (hire ? "h" : "f") + "ire yourself! Use /bond for that.");

			// checks
			Agent current = Agent.getAgent(player.getName());

			if (hire && current != null)
				throw new IllegalArgumentException(player.getName() + " can't be hired, they're already employed!");
			if (!hire && current == null)
				throw new IllegalArgumentException(player.getName() + " can't be fired, they're not employed!");

			// lets do dis
			if (hire)
			{
				new Agent(player);
				BondUtils.message(player, "&bCongratulations, you have been employed by &3" + sender.getName() + "&b!");
				BondUtils.message(player, "&7Mouse over your gadgets to see how to use them, and good luck!");
				BondUtils.message(sender, "&bYou hired &3" + player.getName() + "&b!");
			}
			else
			{
				current.terminate();
				BondUtils.message(player, "&bYou've been fired by &3" + sender.getName() + "&b!");
				BondUtils.message(player, "&7Clear your desk and be out within 5 minutes.");
				BondUtils.message(sender, "&bYou fired &3" + player.getName() + "&b!");
			}





		}




	}
}
