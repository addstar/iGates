/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ptibiscuit.igates.listeners;

import com.ptibiscuit.framework.permission.PermissionHandler;
import com.ptibiscuit.igates.Plugin;
import com.ptibiscuit.igates.data.Portal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class PlayerListener implements Listener {

	private Plugin plug;
	private PermissionHandler perm = null;
	public PlayerListener(Plugin instance) {
		plug = instance;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerMove(PlayerMoveEvent e) {
		if (e.isCancelled()) { return; }

		// Ignore if the player didnt move to a new block
		if (e.getFrom().getBlockX() != e.getTo().getBlockX()
			|| e.getFrom().getBlockY() != e.getTo().getBlockY()
			|| e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
			
			Portal portal = plug.getPortalByPosition(e.getTo());
			if (portal != null) {
				Player p = e.getPlayer();

				perm = plug.getPermissionHandler();
				// Soit il possède la permission use, soit il possède la permissions spéciale. =)
				if (perm.has(p, "portal.use", false) || perm.has(p, "portal.use." + portal.getFillType().getName().toLowerCase(), false)) {
					e.setCancelled(!portal.teleportPlayer(p));
				} else {
					plug.sendPreMessage(p, "cant_do");
					e.setCancelled(true);
					return;
				}
				
			}
		}
	}
	
	// This is for disallowing people to use end-portal and nether-portal to go to these land.
	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerUsePortal(PlayerPortalEvent e) {
		if (e.getCause() == TeleportCause.END_PORTAL || e.getCause() == TeleportCause.NETHER_PORTAL) {
			Portal p = plug.getPortalByPosition(e.getFrom(), 0.7);
			if (p != null) {
				e.setCancelled(true);
			}
		}
	}
}
