package me.minetime.MTS.commands;

import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;

import org.bukkit.ChatColor;

public class Commandtrade extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) {
	if (user.getChatroom().equalsIgnoreCase("GLOBAL")) {
	    user.setChatroom("trade");
	    user.getPlayer().sendMessage(ChatColor.GREEN + "Du hast den Handels-Chat betreten.");
	} else {
	    if (user.getChatroom().equalsIgnoreCase("trade")) {
		user.setChatroom("GLOBAL");
		user.getPlayer().sendMessage(ChatColor.GREEN + "Du hast den Handels-Chat verlassen.");
	    } else {
		user.getPlayer().sendMessage(
			ChatColor.RED + "Fehler: Du kannst den Handels-Chat nur vom globalen Chat betreten.");
	    }
	}

    }

}
