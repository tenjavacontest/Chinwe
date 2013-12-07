package net.chunk64.chinwe.gadgets;

import net.chunk64.chinwe.Agent;
import net.chunk64.chinwe.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public abstract class Gadget implements Listener
{
	protected static Main plugin = Main.getInstance();

	protected Agent agent;
	protected long cooldown;
	protected GadgetType type;
	protected int xpLevel;
	protected float xpBar;

	public Gadget(Agent agent, GadgetType type)
	{
		this.agent = agent;
		this.type = type;
		this.cooldown = 0;
		this.xpLevel = 0;
		this.xpBar = 1F;
		registerListener();
	}

	public abstract void execute();

	protected void addCooldown(double seconds)
	{
		cooldown = (long) (System.currentTimeMillis() + (seconds * 1000));
	}

	protected long getCooldown()
	{
		return cooldown;
	}

	/**
	 * Returns true if cooldown has finished
	 */
	public boolean isCooldownOver()
	{
		return cooldown < 0 || cooldown <= System.currentTimeMillis();
	}

	public GadgetType getType()
	{
		return type;
	}


	private static Set<String> registered = new HashSet<>();

	protected void registerListener()
	{
		if (!registered.contains(getClass().getName()))
		{
			registered.add(getClass().getName());
			plugin.getServer().getPluginManager().registerEvents(this, plugin);
		}
	}

	protected void unregisterListener()
	{
		if (agent.getAgents().isEmpty() && registered.contains(getClass().getName()))
			forceUnregisterListener();
	}

	protected void forceUnregisterListener()
	{
		HandlerList.unregisterAll(this);
		registered.remove(getClass().getName());
	}

	public int getXpLevel()
	{
		return xpLevel;
	}

	public float getXpBar()
	{
		return xpBar;
	}

	public void setXpLevel(int level, Player player)
	{
		this.xpLevel = level;
		if (player.getItemInHand().equals(type.getItemStack()))
			player.setLevel(level < 0 ? 0 : level);
	}

	public void setXpBar(float bar, Player player)
	{
		this.xpBar = bar;
		if (player.getItemInHand().equals(type.getItemStack()))
			player.setExp(bar < 0 ? 0 : bar);
	}

	public Agent getAgent()
	{
		return agent;
	}

	public void destroy()
	{
		getAgent().removeGadget(this);

		agent = null;
		type = null;
		unregisterListener();
	}

	@Override
	public String toString()
	{
		return "Gadget{agent=" + agent + ", type=" + type.toString() + "}";
	}


}
