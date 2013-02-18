package me.minetime.MTS.commands;

import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.clazz.User;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.ChatColor;

public class Commandmute extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {
	if (args.length < 1) {
	    throw new VerwendungsException("/mute [Spieler]");
	}

	User o = new User(args[0]);

	if (o.isOnline() == false) {
	    new Exception(ChatColor.RED + "Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
	}

	if (user.getPlayer().isOp()) {
	    new Exception(ChatColor.RED + "Du kannst diesen Spieler nicht muten.");
	}

	if (o.isMuted()) {
	    o.setMuted(false);
	    user.getPlayer().sendMessage(
		    ChatColor.GREEN + "Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " wurde entmutet.");
	} else {
	    o.setMuted(true);
	    user.getPlayer().sendMessage(
		    ChatColor.GREEN + "Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " wurde gemutet.");
	}

    }

}
