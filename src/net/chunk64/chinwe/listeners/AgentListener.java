package net.chunk64.chinwe.listeners;

import net.chunk64.chinwe.Agent;
import net.chunk64.chinwe.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class AgentListener implements Listener
{
	private final Main plugin;

	public AgentListener(Main plugin)
	{
		this.plugin = plugin;
	}


	@EventHandler
	public void onSwap(PlayerItemHeldEvent event)
	{
		// agent
		Agent agent = Agent.getAgent(event.getPlayer().getName());
		if (agent == null)
			return;


		Agent.updateXp(agent, event.getPlayer().getInventory().getItem(event.getNewSlot()), event.getPlayer());
	}
	

}
