package net.chunk64.chinwe.gadgets;

import net.chunk64.chinwe.Main;
import net.chunk64.chinwe.bond.Agent;

import java.util.HashMap;
import java.util.Map;

public abstract class Gadget
{
	private static Map<String, Gadget> instances = new HashMap<>(); // needed?
	protected static Main plugin = Main.getInstance();

	protected Agent agent;
	protected long cooldown;
	protected GadgetType type;

	public Gadget(Agent agent, GadgetType type)
	{
		this.agent = agent;
		this.cooldown = 0;
		this.type = type;
	}

	abstract void execute();


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
	protected boolean isCooldownOver()
	{
		return cooldown < 0 || cooldown <= System.currentTimeMillis();
	}

	public GadgetType getType()
	{
		return type;
	}

}
