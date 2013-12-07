package net.chunk64.chinwe.listeners;

import net.chunk64.chinwe.Agent;
import net.chunk64.chinwe.Main;
import net.chunk64.chinwe.gadgets.GadgetType;
import net.chunk64.chinwe.gadgets.Pistol;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PistolListener implements Listener
{
	private final Main plugin;

	public PistolListener(Main plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler
	public void onPistolHit(EntityDamageByEntityEvent event)
	{
		// must be living
		if (!(event.getEntity() instanceof LivingEntity))
			return;

		// pistol
		if (event.getDamager() instanceof Arrow && event.getDamager().hasMetadata("bond:bullet"))
		{
			Player shooter = (Player) ((Arrow) event.getDamager()).getShooter(); // only players could shoot a bullet
			final LivingEntity entity = (LivingEntity) event.getEntity();

			// ding
			shooter.playSound(shooter.getLocation(), Sound.ORB_PICKUP, 1F, 1F);

			// kill
			entity.setHealth(0D);
			new BukkitRunnable()
			{
				@Override
				public void run()
				{
					entity.remove();
					entity.getWorld().playEffect(entity.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
				}
			}.runTaskLater(plugin, 2L);
		}
	}

	@EventHandler
	public void onPistolFire(PlayerInteractEvent event)
	{
		// holding pistol
		if (!event.getPlayer().getItemInHand().equals(GadgetType.PISTOL.getItemStack()))
			return;

		// agent
		Agent agent = Agent.getAgent(event.getPlayer().getName());
		if (agent == null)
			return;

		// get pistol
		Pistol pistol = (Pistol) agent.getGadget(GadgetType.PISTOL);
		if (pistol == null)
			return;

		// fire
		pistol.execute();

	}

	@EventHandler
	public void onPistolMiss(ProjectileHitEvent event)
	{
		// pistol
		if (event.getEntity() instanceof Arrow && event.getEntity().hasMetadata("bond:bullet"))
			event.getEntity().remove();

	}


}
