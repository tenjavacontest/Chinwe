package net.chunk64.chinwe.gadgets;

import net.chunk64.chinwe.Agent;
import net.chunk64.chinwe.util.BondUtils;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
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
		bullet.setMetadata("bond:bullet", new FixedMetadataValue(plugin, true));
		setXpLevel(ammo, p);

		// effects
		BlockFace direction = BondUtils.getDirection(p.getLocation().add(p.getLocation().getDirection()));
		for (int i = 0; i < 3; i++)
			p.getWorld().playEffect(p.getEyeLocation(), Effect.SMOKE, direction);
		p.playSound(p.getLocation(), Sound.VILLAGER_DEATH, 0.3F, 2F);

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

}
