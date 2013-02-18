package me.minetime.MTS.commands;

import me.minetime.MTS.MTSData;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Commandpvp extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {
	Player player = user.getPlayer();

	if (player.isOp()) {
	    if (MTSData.pvp == false) {
		MTSData.pvp = true;
		player.sendMessage(ChatColor.GREEN + "PvP wurde aktiviert.");
	    } else {
		MTSData.pvp = false;
		player.sendMessage(ChatColor.GREEN + "PvP wurde deaktiviert.");
	    }
	}
    }

}