package net.chunk64.chinwe.bond;

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
import org.bukkit.event.player.PlayerInteractEvent;

public class AgentListener implements Listener
{
	private final Main plugin;

	public AgentListener(Main plugin)
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
			LivingEntity entity = (LivingEntity) event.getEntity();

			// ding
			shooter.playSound(shooter.getLocation(), Sound.ORB_PICKUP, 1F, 1F);

			// kill
			entity.setHealth(0D);
			entity.remove(); // test body is removed straight away
			entity.getWorld().playEffect(entity.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
		}
	}

	@EventHandler
	public void onPistolFire(PlayerInteractEvent event)
	{
		// holding pistol
		if (event.getPlayer().getItemInHand().getType() != GadgetType.PISTOL.getMaterial())
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


}
