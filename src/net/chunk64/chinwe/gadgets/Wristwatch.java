package net.chunk64.chinwe.gadgets;

import net.chunk64.chinwe.Agent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BlockIterator;

public class Wristwatch extends Gadget
{
	public Wristwatch(Agent agent)
	{
		super(agent, GadgetType.WATCH);
		setXpBar(1F, agent.getPlayer());
	}

	private boolean extended = false, laser = false;
	private BukkitTask laserTask = null;

	@Override
	public void execute()
	{
		// cooldown check
		if (!isCooldownOver())
			return;

		Player p = agent.getPlayer();
		if (p == null)
			return;

		toggleExtended();
	}

	public boolean isExtended()
	{
		return extended;
	}

	public void toggleExtended()
	{
		extended = !extended;
		setXpBar(extended ? 0F : 1F, agent.getPlayer());
	}

	public void toggleLaser()
	{
		laser = !laser;
		setXpBar(laser ? 0F : 1F, agent.getPlayer());
		addCooldown(1);

		// stop
		if (!laser)
		{
			if (laserTask != null)
				laserTask.cancel();
			laserTask = null;
			return;
		}

		// start
		laserTask = new BukkitRunnable()
		{
			Block targeted = null;
			Player player = agent.getPlayer();

			@Override
			public void run()
			{
				// stop
				if (player == null || player.isDead() || !player.isOnline())
				{
					toggleLaser();
					return;
				}

				// get target
				BlockIterator iterator = new BlockIterator(player.getWorld(), player.getLocation().toVector(), player.getLocation().getDirection(), 0, 4);

				while (iterator.hasNext())
					targeted = iterator.next();

				// TODO laser



			}
		}.runTaskTimer(plugin, 0L, 2L);


	}
}
