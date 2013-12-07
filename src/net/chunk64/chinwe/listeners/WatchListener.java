package net.chunk64.chinwe.listeners;

import net.chunk64.chinwe.Agent;
import net.chunk64.chinwe.Main;
import net.chunk64.chinwe.gadgets.GadgetType;
import net.chunk64.chinwe.gadgets.Wristwatch;
import net.chunk64.chinwe.util.BondUtils;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class WatchListener implements Listener
{
	private Main plugin;

	public WatchListener(Main plugin)
	{
		this.plugin = plugin;
	}


	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{

		// holding watch
		if (!event.getPlayer().getItemInHand().equals(GadgetType.WATCH.getItemStack()))
			return;


		// agent
		Agent agent = Agent.getAgent(event.getPlayer().getName());
		if (agent == null)
			return;

		// get watch
		final Wristwatch watch = (Wristwatch) agent.getGadget(GadgetType.WATCH);
		if (watch == null)
			return;

		// sneaking
		if (!event.getPlayer().isSneaking())
			return;


		// right click laser
		if (event.getAction().toString().contains("RIGHT"))
		{
			if (watch.isCooldownOver())
			{
				watch.toggleLaser();
				return;
			}
		}

		// left click grapple
		if (event.getAction().toString().contains("LEFT"))
			watch.execute();

		// shoot grapple
		if (watch.isExtended())
		{
			final Player player = event.getPlayer();
			player.getWorld().playSound(player.getLocation(), Sound.CREEPER_HISS, 0.5F, 0.05F);

			// "shoot" grapple
			final List<Block> blocks = new ArrayList<>();
			BlockIterator iterator = new BlockIterator(player.getWorld(), player.getLocation().toVector(), player.getLocation().getDirection(), 0, 64);
			BlockFace blockFace = BondUtils.getDirection(player.getLocation());

			Block targetBlock = null;
			while (iterator.hasNext())
			{
				Block block = iterator.next();
				blocks.add(block);
				targetBlock = block;
				block.getWorld().playEffect(block.getLocation(), Effect.SMOKE, blockFace);

				// end
				if (!BondUtils.isTransparent(block.getType()))
					break;
			}

			// distance of 1
			if (targetBlock == null)
				return;

			// pull towards
			new BukkitRunnable()
			{
				int pos = 0;

				public void run()
				{

					// end
					if (pos > blocks.size() - 1)
					{
						player.sendBlockChange(blocks.get(blocks.size() - 1).getLocation(), 20, (byte) 0);
						// shove to last one
						player.setVelocity(blocks.get(blocks.size() - 1).getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(1.6).add(new Vector(0, 0.5, 0)));
						watch.toggleExtended();
						cancel();
						return;
					}

					Block block = blocks.get(pos);
					pos += 4;
					{
						Vector vector = block.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
						player.setVelocity(vector);


					}
				}
			}.runTaskTimer(plugin, 6L, 2L);


		}


	}


}
