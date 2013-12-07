package net.chunk64.chinwe.listeners;

import net.chunk64.chinwe.Agent;
import net.chunk64.chinwe.Main;
import net.chunk64.chinwe.commands.Permission;
import net.chunk64.chinwe.gadgets.BallpointPen;
import net.chunk64.chinwe.gadgets.GadgetType;
import net.chunk64.chinwe.util.CommandUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BallpointPenListener implements Listener
{


	private final Main plugin;

	public BallpointPenListener(Main plugin)
	{
		this.plugin = plugin;
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

		// permission
		if (!CommandUtils.hasPermission(event.getPlayer(), Permission.PEN))
			return;

		// click
		pen.execute();
	}


	@EventHandler
	public void onDrop(PlayerDropItemEvent event)
	{
		// must be pen
		if (!event.getItemDrop().getItemStack().equals(GadgetType.PEN.getItemStack()))
			return;

		// agent
		Agent agent = Agent.getAgent(event.getPlayer().getName());
		if (agent == null)
			return;

		// get pen
		BallpointPen pen = (BallpointPen) agent.getGadget(GadgetType.PEN);
		pen.drop(event.getItemDrop(), event.getPlayer());
	}


}
