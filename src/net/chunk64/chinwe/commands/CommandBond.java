package net.chunk64.chinwe.commands;

import net.chunk64.chinwe.bond.Agent;
import net.chunk64.chinwe.util.BondUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class CommandBond extends BaseBondCommand
{
	public CommandBond(PluginCommand command, boolean playerOnly, Permission perm)
	{
		super(command, playerOnly, perm);
	}

	@Override
	void run(CommandSender sender, Command cmd, String[] args) throws Exception
	{

		if (args.length != 0)
			throw new IncorrectUsageException();

		Player p = (Player) sender; // the command is player only

		Agent agent = Agent.getAgent(p.getName());

		// create
		if (agent == null)
		{
			new Agent(p);
			BondUtils.message(sender, "&bYou have been recruited by &3MI6!");
		}

		// destroy
		else
		{
			agent.terminate();
			BondUtils.message(sender, "&bYou retired from field work!");
		}

	}
}
