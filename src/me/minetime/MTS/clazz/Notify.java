package me.minetime.MTS.clazz;

import me.minetime.MTS.MTSData;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Notify extends Thread {
    private Player player;
    private Block block;
    private int light;

    public Notify(Player player, Block block, int light) {
	this.player = player;
	this.block = block;
	this.light = light;
    }

    public void run() {

	PermissionManager pex = PermissionsEx.getPermissionManager();
	int total = getAllRelative(block, player) + 1;
	String msg = ChatColor.GOLD + player.getName() + ChatColor.AQUA + " hat " + String.valueOf(total)
		+ " Diamant(en) gefunden. Sichtbarkeit(" + String.valueOf(light) + "%)";

	for (Player p : Bukkit.getServer().getOnlinePlayers()) {
	    if (pex.has(p, "MTS.antiX")) {
		p.sendMessage(ChatColor.AQUA + "[AntiX] " + msg);
	    }
	}

    }

    private int getAllRelative(Block block, Player player) {
	Integer total = 0;

	for (BlockFace face : BlockFace.values()) {
	    if (block.getRelative(face).getType() == block.getType()) {
		Block rel = block.getRelative(face);

		if (!MTSData.foundblocks.contains(rel.getLocation())) {
		    MTSData.foundblocks.add(rel.getLocation());

		    total++;
		    total = total + getAllRelative(rel, player);
		}

	    }
	}

	return total;
    }
}