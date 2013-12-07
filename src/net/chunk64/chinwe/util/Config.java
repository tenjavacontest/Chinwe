package net.chunk64.chinwe.util;

import net.chunk64.chinwe.Main;
import org.bukkit.configuration.Configuration;

import java.io.File;

public class Config
{
	private static Config instance;
	private Configuration config;
	private File configFile;
	private Main plugin;


	public Config(Main plugin)
	{
		this.plugin = plugin;
		instance = this;
		configFile = new File(plugin.getDataFolder(), "config.yml");
		config = plugin.getConfig().getRoot();
		if (!configFile.exists())
			plugin.saveDefaultConfig();


	}

	public static Config getInstance()
	{
		return instance;
	}

	public void save()
	{
		plugin.saveConfig();
	}


}