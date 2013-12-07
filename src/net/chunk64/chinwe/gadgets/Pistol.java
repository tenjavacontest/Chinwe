package net.chunk64.chinwe.gadgets;

import net.chunk64.chinwe.bond.Agent;
import net.chunk64.chinwe.util.BondUtils;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class Pistol extends Gadget
{
	private int clipSize = 8, ammo = clipSize;

	public Pistol(Agent agent)
	{
		super(agent, GadgetType.PISTOL);
		setXpLevel(clipSize, agent.getPlayer());
	}


	@Override
	public void execute()
	{

		// cooldown check
		if (!isCooldownOver())
			return;

		Player p = agent.getPlayer();
		if (p == null)
			return;

		// clip check
		if (--ammo <= 0)
		{
			reload();
			p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 0.2F, 0.1F);
			return;
		}


		// fire
		Arrow bullet = p.launchProjectile(Arrow.class);
		bullet.setVelocity(bullet.getVelocity().multiply(2));
		bullet.setMetadata("bond:bullet", new FixedMetadataValue(plugin, p.getName()));
		setXpLevel(ammo, p);

		// effects
		BlockFace direction = BondUtils.getDirection(p.getLocation().add(p.getLocation().getDirection()));
		for (int i = 0; i < 3; i++)
			p.getWorld().playEffect(p.getEyeLocation(), Effect.SMOKE, direction);
		p.playSound(p.getLocation(), Sound.CAT_HISS, 0.3F, 3F);

		// cooldown
		addCooldown(0.5);

	}

	void reload()
	{
		ammo = clipSize;
		addCooldown(1.5);

		final Player p = agent.getPlayer();

		new BukkitRunnable()
		{
			int count = 0;

			@Override
			public void run()
			{
				if (count++ >= clipSize - 1)
					cancel();
				p.playSound(p.getLocation(), Sound.CLICK, 0.5F, 0.7F);
				setXpLevel(count, p);

			}
		}.runTaskTimer(plugin, 0L, 4L);

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
