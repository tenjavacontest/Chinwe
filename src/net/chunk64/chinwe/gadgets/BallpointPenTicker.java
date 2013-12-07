package net.chunk64.chinwe.gadgets;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class BallpointPenTicker extends BukkitRunnable
{

	/**
	 * @param player  The player
	 * @param gadget  The pen
	 * @param holding True if they are holding it, otherwise false if they have thrown it
	 * @param item    The dropped pen, null if holding
	 * @param point   The location to tick and explode at
	 */
	public BallpointPenTicker(Player player, Gadget gadget, boolean holding, Item item, ExplosionPoint point)
	{
		this.player = player;
		this.gadget = gadget;
		this.holding = holding;
		this.item = item;
		this.point = point;
	}

	public static enum ExplosionPoint
	{
		PLAYER, ITEM
	}

	private Player player;
	private Gadget gadget;
	private boolean holding;
	private Item item;
	private ExplosionPoint point;

	static int time = 10; // tick for 5 seconds
	final float bit = 0.1F;

	@Override
	public void run()
	{
		Location loc = point == ExplosionPoint.PLAYER ? player.getLocation() : item.getLocation();

		if (--time < 0)
		{
			// remove
			ItemStack toRemove = player.getItemInHand().clone();
			toRemove.setAmount(1);
			player.getInventory().remove(toRemove);

			// remove drop
			if (!holding && item != null)
				item.remove();

			// boom
			explode(loc);

			// reset
			time = 10;
			cancel();
			return;
		}

		// start ticking in hand
		tick(loc);

		// decrease xp bar
		gadget.setXpBar(player.getExp() - bit, player);
	}

	private void tick(Location location)
	{
		location.getWorld().playSound(location, Sound.NOTE_STICKS, 0.5F, 1F);
	}

	private void explode(Location location)
	{
		location.getWorld().createExplosion(location, 6F, true);

		for (Sound sound : Sound.values())
			if (sound.toString().endsWith("_DEATH"))
				location.getWorld().playSound(location, sound, 1F, 1F);
		location.getWorld().playSound(location, Sound.AMBIENCE_THUNDER, 1F, 1F);

		// give a new one
		gadget.getAgent().giveGadget(new BallpointPen(gadget.getAgent()));
		gadget.destroy();

	}


}

