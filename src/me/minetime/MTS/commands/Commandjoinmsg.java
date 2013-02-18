package me.minetime.MTS.commands;

import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.clazz.Config;

import org.bukkit.ChatColor;

public class Commandjoinmsg extends CommandMT {

    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {
	String nachricht = "";
	Config mts_config = new Config();

	for (int i = 0; i < args.length; i++) {
	    if (i == 0) {
		nachricht += args[i];
	    } else {
		nachricht += " " + args[i];
	    }
	}

	mts_config.setString("JoinMSG.Nachricht", nachricht);

	user.getPlayer().sendMessage(ChatColor.GREEN + "Join MSG ge\u00e4ndert.");
    }

}
