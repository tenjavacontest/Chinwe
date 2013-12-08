package net.chunk64.chinwe;

import net.chunk64.chinwe.commands.CommandAdminBond;
import net.chunk64.chinwe.commands.CommandBond;
import net.chunk64.chinwe.commands.Permission;
import net.chunk64.chinwe.listeners.AgentListener;
import net.chunk64.chinwe.listeners.BallpointPenListener;
import net.chunk64.chinwe.listeners.PistolListener;
import net.chunk64.chinwe.listeners.WristwatchListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Main extends JavaPlugin
{

	private static Main instance;

	@Override
	public void onEnable()
	{
		instance = this;
		init();
	}

	/**
	 * Register commands and listeners
	 */
	private void init()
	{
		boolean commands = true;
		try
		{
			register("bond", CommandBond.class, true, Permission.BOND);
			register("bondadmin", CommandAdminBond.class, false, Permission.BONDADMIN);

			commands = false; // to determine if commands/listeners caused the problem

			getServer().getPluginManager().registerEvents(new AgentListener(this), this);
			getServer().getPluginManager().registerEvents(new PistolListener(this), this);
			getServer().getPluginManager().registerEvents(new BallpointPenListener(this), this);
			getServer().getPluginManager().registerEvents(new WristwatchListener(this), this);

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

	public static Main getInstance()
	{
		return instance;
	}
}