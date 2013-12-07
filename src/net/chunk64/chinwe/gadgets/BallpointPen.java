package net.chunk64.chinwe.gadgets;

import net.chunk64.chinwe.bond.Agent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

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
	void execute()
	{
		// cooldown
		if (!isCooldownOver())
			return;

		final Player player = agent.getPlayer();
		if (player == null)
			return;

		// explode
		if (--cumulativeClicks <= 0)
		{
			ItemStack toRemove = player.getItemInHand().clone();
			toRemove.setAmount(1);
			player.getInventory().remove(toRemove);

			// start ticking
			setXpBar(1F, player);

			task = new BukkitRunnable()
			{
				int time = 10; // tick for 5 seconds
				float bit = 100 / time; //

				@Override
				public void run()
				{
					if (--time < 0)
					{
						// boom
						player.sendMessage("boom yey");
						cancel();
					}

					// start ticking in hand
					tick();

					// decrease xp bar
					setXpBar(player.getExp() - bit, player);
				}

				private void tick()
				{
					for (int i = 0; i < (time <= 5 ? 2 : 1); i++)
						player.getWorld().playSound(player.getLocation(), Sound.NOTE_STICKS, 0.5F, 1F);
				}
			}.runTaskTimer(plugin, 0L, 10L);
			return;
		}

		// click down
		setXpLevel(cumulativeClicks, player);
		player.getWorld().playSound(player.getLocation(), Sound.WOOD_CLICK, 0.2F, 1.5F);


	}

	public int getClicks()
	{
		return cumulativeClicks;
	}


	@EventHandler
	public void onClick(PlayerInteractEvent event)
	{
		// must be pen
		if (!event.getPlayer().getItemInHand().equals(GadgetType.PEN.getItemStack()))
			return;

		// agent
		Agent agent = Agent.getAgent(event.getPlayer().getName());
		if (agent == null)
			return;

		// get pen
		BallpointPen pen = (BallpointPen) agent.getGadget(GadgetType.PEN);

		// click
		pen.execute();
	}
}
