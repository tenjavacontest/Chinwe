package net.chunk64.chinwe.bond;

import net.chunk64.chinwe.Main;
import net.chunk64.chinwe.gadgets.Gadget;
import net.chunk64.chinwe.gadgets.GadgetType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class AgentListener implements Listener
{
	private final Main plugin;

	public AgentListener(Main plugin)
	{
		this.plugin = plugin;
	}


	@EventHandler
	public void onSwap (PlayerItemHeldEvent event)
	{
		// agent
		Agent agent = Agent.getAgent(event.getPlayer().getName());
		if (agent == null)
			return;

		ItemStack itemStack = event.getPlayer().getInventory().getItem(event.getNewSlot());

		for (GadgetType type : GadgetType.values())
		{
			if (type.getItemStack().equals(itemStack))
			{
				Gadget gadget = agent.getGadget(type);
				event.getPlayer().setLevel(gadget.getXpLevel());
				event.getPlayer().setExp(gadget.getXpBar());
				break;
			}
		}


	}


}
