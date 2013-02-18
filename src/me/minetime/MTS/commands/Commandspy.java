package me.minetime.MTS.commands;

import me.minetime.MTS.MTS;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;

import org.bukkit.ChatColor;

public class Commandspy extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) {

	if (!MTS.spy.contains(user.getPlayer().getName())) {
	    user.getPlayer().sendMessage(ChatColor.GREEN + "Spy aktiviert!");
	    MTS.spy.add(user.getPlayer().getName());
	} else {
	    user.getPlayer().sendMessage(ChatColor.GREEN + "Spy deaktiviert!");
	    MTS.spy.remove(user.getPlayer().getName());
	}

    }
}