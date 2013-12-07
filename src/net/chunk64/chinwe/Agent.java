package net.chunk64.chinwe;

import net.chunk64.chinwe.gadgets.BallpointPen;
import net.chunk64.chinwe.gadgets.Gadget;
import net.chunk64.chinwe.gadgets.GadgetType;
import net.chunk64.chinwe.gadgets.Pistol;
import net.chunk64.chinwe.util.BondUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Agent
{
	private static Map<String, Agent> agents = new HashMap<>();
	private String name;
	private Map<GadgetType, Gadget> gadgets = new HashMap<>();

	public Agent(Player player)
	{
		this.name = player.getName();
		agents.put(name, this);

		// TODO store inventory and xp
		store(player);


		// equip
		// tux
		ItemStack tux = BondUtils.setInfo(Material.CHAINMAIL_CHESTPLATE, "&6Tuxedo", Arrays.asList("&aWhat a gentleman"), null);
		player.getInventory().setChestplate(tux);

		// martini
		ItemStack martini = BondUtils.setInfo(Material.POTION, "&6Martini", Arrays.asList("&aShaken, not stirred"), null);
		player.getInventory().setItem(8, martini);

		// give gadgets
		giveGadget(new Pistol(this));
		giveGadget(new BallpointPen(this));


		// update level
		updateXp(this, player.getItemInHand(), player);
	}


	public void terminate(boolean destroyGadgets)
	{
		agents.remove(name);

		if (destroyGadgets)
		{
			for (Gadget gadget : gadgets.values())
				gadget.destroy();
		}
		gadgets = null;

		Player player = getPlayer();

		if (player != null)
		{
			// TODO restore inventory
			store(player);

			// effects?
		}
	}

	public void giveGadget(final Gadget gadget)
	{
		gadgets.put(gadget.getType(), gadget);
		Player player = getPlayer();
		if (player != null)
			player.getInventory().addItem(gadget.getType().getItemStack());
	}

	public void removeGadget(Gadget gadget)
	{
		gadgets.remove(gadget.getType());
		Player player = getPlayer();
		if (player != null)
			player.getInventory().remove(gadget.getType().getItemStack());
	}


	/**
	 * Will return null if gadget is not found
	 */
	public Gadget getGadget(GadgetType type)
	{
		return gadgets.get(type);
	}

	/**
	 * Will return null if offline
	 */
	public Player getPlayer()
	{
		return Bukkit.getPlayerExact(name);
	}


	/**
	 * Gets the agent by the given name, null if not found
	 */
	public static Agent getAgent(String name)
	{
		return agents.get(name);
	}

	public static Collection<Agent> getAgents()
	{
		return agents.values();
	}

	public static void updateXp(Agent agent, ItemStack itemStack, Player player)
	{
		int level = 0;
		float bar = 1F;

		for (GadgetType type : GadgetType.values())
		{
			if (type.getItemStack().equals(itemStack))
			{
				Gadget gadget = agent.getGadget(type);
				level = gadget.getXpLevel();
				bar = gadget.getXpBar();
				break;
			}
		}
		player.setLevel(level);
		player.setExp(bar);
	}


	/**
	 * Simple clearing of inventory and xp reset
	 */
	private void store(Player player)
	{
		// TODO store and restore
		player.getInventory().clear();
		player.getInventory().setArmorContents(new ItemStack[player.getInventory().getArmorContents().length]);
		player.setLevel(0);
	}

	@Override
	public String toString()
	{
		return "Agent{name=" + name + "}";
	}

}