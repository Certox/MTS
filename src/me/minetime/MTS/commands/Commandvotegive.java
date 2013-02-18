package me.minetime.MTS.commands;

import java.sql.ResultSet;

import me.minetime.MTS.MTSData;
import me.minetime.MTS.MySQL;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.clazz.User;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class Commandvotegive extends CommandMT {

    @Override
    public void run(final CommandSender sender, final String commandLabel, final String[] args) {

	IUser voter = new User(args[0]);

	if (!voter.hasAccount() || args.length != 1)
	    sender.sendMessage("Votegive geblockt, weil es ein Fakeaccount ist!");
	else {
	    String voteMessage = "";

	    // GET VOTEMESSAGE
	    try {
		ResultSet rs = MySQL.Query("SELECT `votemessage` FROM `mt_resources`;");

		while (rs.next()) {
		    voteMessage = MTSData.convertColorcodes(rs.getString(1));

		    voteMessage = voteMessage.replace("-name-", voter.getName());
		}

	    }

	    catch (Exception err) {
		System.out.println("MTS-Fehler: " + err);
	    }

	    // GIVE ITEMS TO PLAYER
	    try {
		ResultSet rs = MySQL.Query("SELECT `cmd` FROM `mt_voteitems`;");

		while (rs.next()) {
		    String command = rs.getString(1).replace("-name-", voter.getName());

		    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
		}

	    }

	    catch (Exception err) {
		System.out.println("MTS-Fehler: " + err);
	    }

	    Bukkit.broadcastMessage(voteMessage);
	}
    }

}
