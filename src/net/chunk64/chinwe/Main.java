package net.chunk64.chinwe;

import net.chunk64.chinwe.commands.CommandBond;
import net.chunk64.chinwe.commands.Permission;
import net.chunk64.chinwe.listeners.AgentListener;
import net.chunk64.chinwe.listeners.BallpointPenListener;
import net.chunk64.chinwe.listeners.PistolListener;
import net.chunk64.chinwe.listeners.WatchListener;
import net.chunk64.chinwe.util.Config;
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
			getServer().getPluginManager().registerEvents(new PistolListener(this), this);
			getServer().getPluginManager().registerEvents(new BallpointPenListener(this), this);
			getServer().getPluginManager().registerEvents(new WatchListener(this), this);

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