package net.chunk64.chinwe.gadgets;

import net.chunk64.chinwe.Agent;
import net.chunk64.chinwe.util.BondUtils;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

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
			Block block = null;
			Player player = agent.getPlayer();
			Map<Block, Integer> damage = new HashMap<>();

			@Override
			public void run()
			{
				// stop
				if (player == null || player.isDead() || !player.isOnline())
				{
					toggleLaser();
					return;
				}

//				BlockIterator iterator = new BlockIterator(player.getWorld(), player.getLocation().toVector(), player.getLocation().getDirection(), 0, 1);
//				while (iterator.hasNext())
//					block = iterator.next();

				// get target
				block = player.getTargetBlock(null, 2); // easier


				// check you're looking at a block
				if (block != null && block.getType() != Material.AIR)
				{
					if (damage.containsKey(block))
					{
						int damaged = damage.get(block);

						// should it break?
						if (damaged < 2)
						{
							// yus
							damage.remove(block);
							block.breakNaturally();
						} else
						{
							// just decrement damage
							damage.put(block, damage.get(block) - 2);
						}
					}

					else
					{
						damage.put(block, block.getType() == Material.OBSIDIAN || block.getType() == Material.BEDROCK ? 8 : 4);
						block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
						block.getWorld().playEffect(player.getLocation(), Effect.SMOKE, BondUtils.getDirection(player.getLocation()));
						block.getWorld().playEffect(block.getLocation(), Effect.SMOKE, BlockFace.UP);
						block.getWorld().playSound(block.getLocation(), Sound.FUSE, 0.5F, 0.1F);
					}
				}


			}
		}.runTaskTimer(plugin, 0L, 5L);


	}
}
