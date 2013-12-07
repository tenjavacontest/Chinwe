package net.chunk64.chinwe.gadgets;

import net.chunk64.chinwe.bond.Agent;
import net.chunk64.chinwe.util.BondUtils;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class Pistol extends Gadget
{


	private int clipSize = 7, ammo = clipSize;

	public Pistol(Agent agent)
	{
		super(agent, GadgetType.PISTOL);
	}

	@Override
	public void execute()
	{

		// cooldown check
		if (!isCooldownOver())
			return;

		// clip check
		if (--ammo <= 0)
		{
			reload();
			return;
		}

		Player p = agent.getPlayer();

		// fire
		Arrow bullet = p.launchProjectile(Arrow.class);
		bullet.setVelocity(bullet.getVelocity().multiply(1.5));
		bullet.setMetadata("bond:bullet", new FixedMetadataValue(plugin, p.getName()));

		// effects
		for (int i = 0; i < 3; i++)
			p.getWorld().playEffect(p.getEyeLocation(), Effect.SMOKE, BondUtils.getDirection(p));
		p.playSound(p.getLocation(), Sound.CAT_HISS, 1F, 2F);

		// cooldown
		addCooldown(0.5);

	}

	void reload()
	{
		ammo = clipSize;
		addCooldown(1.5);

		final Player p = agent.getPlayer();
		p.playSound(p.getLocation(), Sound.CLICK, 1F, 0.9F);
		final Random random = new Random();

		new BukkitRunnable()
		{
			int count = 6;

			@Override
			public void run()
			{
				if (--count == 0)
					cancel();
				p.playSound(p.getLocation(), Sound.CLICK, 1F, random.nextFloat() / 1.5F);

			}
		}.runTaskTimer(plugin, 0L, random.nextInt(5) + 3);

	}


}
