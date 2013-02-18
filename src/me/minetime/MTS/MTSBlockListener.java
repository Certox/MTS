package me.minetime.MTS;

import me.minetime.MTS.exception.NoPermissionException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class MTSBlockListener implements Listener {
	
	
    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
	if (!event.isCancelled()) {
	    if (event.getPlayer().getItemInHand().getType().equals(Material.WRITTEN_BOOK)) {
		if (!event.getPlayer().getItemInHand().getEnchantments().isEmpty()) {
		    final PermissionManager pex = PermissionsEx.getPermissionManager();
		    Player p = event.getPlayer();
		    if (!pex.has(p, "MTS.breakbook")) {
			for (Player playa : Bukkit.getOnlinePlayers()) {
			    if (playa.isOp() && pex.has(playa, "MTS.AntiBook")) {
				playa.sendMessage(ChatColor.GOLD + "[AntiBook] " + ChatColor.RED + p.getName()
					+ " versucht mit einem Buch abzubauen");
			    }
			}
			event.setCancelled(true);
			p.kickPlayer(ChatColor.RED + "Mit einem Buch baut man doch nichts ab!");
		    }
		}
	    }
	    

	
		
	    if (event.getBlock().getType() != Material.DIAMOND_ORE)
		return;
	    Location loc = event.getBlock().getLocation();
	    if (MTSData.foundblocks.contains(loc))
		MTSData.foundblocks.remove(loc);
	}
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event) throws NoPermissionException {
	if (!event.isCancelled()) {
	    Block blk = event.getBlock();
	    if (blk.getType() == null){
	    	return;
	    }
	    
	    
	    if(blk.getType() == Material.DIAMOND_ORE){
	    	if(!MTSData.foundblocks.contains(blk.getLocation())){
	    		MTSData.foundblocks.add(blk.getLocation());
	    	}
	    }

	}
    }

}
