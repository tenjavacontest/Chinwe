package net.chunk64.chinwe;

import net.chunk64.chinwe.bond.AgentListener;
import net.chunk64.chinwe.commands.CommandBond;
import net.chunk64.chinwe.commands.Permission;
import net.chunk64.chinwe.util.Config;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Main extends JavaPlugin
{


	@Override
	public void onEnable()
	{
		// perhaps something will happen here
		init();
		new Config(this);


	}

	private void init()
	{
		boolean commands = true;
		try
		{
			register("bond", CommandBond.class, true, Permission.BOND);

			commands = false;

			getServer().getPluginManager().registerEvents(new AgentListener(this), this);

		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e)
		{
			getLogger().severe("Could not register " + (commands ? "commands!" : "events!"));
			e.printStackTrace();
		}
	}

	/**
	 * Registers a command
	 */
	private void register(String command, Class clazz, boolean playerOnly, Permission perm) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
	{
		Constructor constructor = clazz.getConstructor(PluginCommand.class, Boolean.TYPE, Permission.class);
		constructor.newInstance(new Object[]{getCommand(command), playerOnly, perm});
	}
}