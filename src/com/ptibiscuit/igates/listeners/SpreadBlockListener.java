package com.ptibiscuit.igates.listeners;

import com.ptibiscuit.framework.permission.PermissionHandler;
import com.ptibiscuit.igates.Plugin;
import com.ptibiscuit.igates.data.Portal;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;

public class SpreadBlockListener implements Listener {
	private Plugin plug;
	public SpreadBlockListener(Plugin instance) {
		plug = instance;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockFromTo(BlockFromToEvent e)
	{
		if (plug.getConfig().getBoolean("config.retain_liquid"))
		{
			for (Portal p : plug.getData().getPortals())
			{
				if (p.isIn(e.getBlock().getLocation()) && !p.isIn(e.getToBlock().getLocation()))
				{
					e.setCancelled(true);
					return;
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPhysics(BlockPhysicsEvent e)
	{
		if ((e.getChangedType() == Material.WATER) || (e.getChangedType() == Material.LAVA)) {
			for (Portal p : plug.getData().getPortals())
			{
				if (p.isIn(e.getBlock().getLocation()))
				{
					e.setCancelled(true);
					return;
				}
			}
		}
	}
}
