package net.chunk64.chinwe.gadgets;

import net.chunk64.chinwe.Agent;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class BallpointPen extends Gadget
{
	private int cumulativeClicks = 3;
	private BukkitTask task = null;

	public BallpointPen(Agent agent)
	{
		super(agent, GadgetType.PEN);

		Player player = agent.getPlayer();
		setXpLevel(cumulativeClicks, player);
		setXpBar(1F, player);
	}


	@Override
	public void execute()
	{

		// cooldown
		if (!isCooldownOver())
			return;

		// already ticking
		if (task != null)
			return;

		final Player player = agent.getPlayer();
		if (player == null)
			return;

		// explode
		if (--cumulativeClicks <= 0)
		{
			// start ticking
			setXpBar(1F, player);

			task = new BallpointPenTicker(player, this, true, null, BallpointPenTicker.ExplosionPoint.PLAYER).runTaskTimer(plugin, 0L, 10L);
			return;
		}

		// click down
		setXpLevel(cumulativeClicks, player);
		player.getWorld().playSound(player.getLocation(), Sound.WOOD_CLICK, 0.2F, 1.5F);
		addCooldown(0.5);


	}

	public int getClicks()
	{
		return cumulativeClicks;
	}


	/**
	 * Drops and blows up the item after finishing the countdown
	 *
	 * @param item The pen
	 */
	public void drop(Item item, Player player)
	{
		System.out.println("task = " + task);

		if (task != null)
			task.cancel();

		// no pickup
		item.setPickupDelay(Integer.MAX_VALUE);

		// throw it away
		Vector direction = player.getLocation().getDirection();
		direction.setY(direction.getY() + 0.1);
		item.setVelocity(direction.normalize());

		// start timer again
		new BallpointPenTicker(player, this, false, item, BallpointPenTicker.ExplosionPoint.ITEM).runTaskTimer(plugin, 0L, 10L);
	}
}
