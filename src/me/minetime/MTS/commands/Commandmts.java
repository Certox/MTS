package me.minetime.MTS.commands;

import me.minetime.MTS.MTS;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Commandmts extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws VerwendungsException {
	Player player = user.getPlayer();
	boolean stat = true;

	if (stat == true) {
	    if (args.length == 0) {
		player.sendMessage(ChatColor.AQUA + "[]------------------- " + ChatColor.GOLD + "Credits" + ChatColor.AQUA
			+ " -------------------[]");
		player.sendMessage(ChatColor.DARK_AQUA + "MTS Copyright 2012 MineTime");
		player.sendMessage(ChatColor.GOLD + "Coded by Batschkoto, viebi, Muwbi & f14_tomcat");
		player.sendMessage(ChatColor.AQUA + "[]---------------------------------------------[]");

		stat = false;
	    }
	}

	if (stat == true) {
	    boolean isCommand = false;

	    if (args[0].equalsIgnoreCase("reload")) {
		isCommand = true;

		player.sendMessage(ChatColor.GOLD + "[MTS] " + ChatColor.GRAY + "Reload-Vorgang wird eingeleitet.");
		MTS.reload();
		player.sendMessage(ChatColor.GOLD + "[MTS] " + ChatColor.GRAY + "Reload komplett.");
	    }
	    if (isCommand == false) {
		throw new VerwendungsException("/mts <reload>");
	    }
	}
    }

}
