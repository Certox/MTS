package me.minetime.MTS.commands;

import java.sql.ResultSet;

import me.minetime.MTS.MySQL;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Commanddelsperre extends CommandMT {

    @Override
    public void run(final CommandSender sender, final String commandLabel, final String[] args) throws Exception {
	int bantype = 0;

	if (args.length != 1)
	    throw new VerwendungsException("/delsperre name");
	try {
	    ResultSet rs = MySQL.Query("SELECT `reason` FROM `mt_timebans` WHERE `name`='" + args[0] + "';");

	    while (rs.next())
		bantype = 2;

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}

	try {
	    ResultSet rs = MySQL.Query("SELECT `reason` FROM `mt_bans` WHERE `name`='" + args[0] + "';");

	    while (rs.next()) {
		bantype = 1;

	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}

	try {
	    ResultSet rs = MySQL.Query("SELECT `reason` FROM `mt_globalbans` WHERE `name`='" + args[0] + "';");

	    while (rs.next()) {
		bantype = 3;

	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}

	if (bantype == 1) {
	    sender.sendMessage(ChatColor.GOLD + "Die permanente Sperre von " + args[0] + " wurde aufgehoben!");
	    MySQL.Update("DELETE FROM `mt_bans` WHERE `name`='" + args[0] + "';");
	    MySQL.Update("DELETE FROM `mt_globalbans` WHERE `name`='" + args[0] + "';");
	    MySQL.Update("DELETE FROM `mt_timebans` WHERE `name`='" + args[0] + "';");
	} else if (bantype == 2) {
	    sender.sendMessage(ChatColor.GOLD + "Die Zeit-Sperre von " + args[0] + " wurde aufgehoben!");
	    MySQL.Update("DELETE FROM `mt_bans` WHERE `name`='" + args[0] + "';");
	    MySQL.Update("DELETE FROM `mt_globalbans` WHERE `name`='" + args[0] + "';");
	    MySQL.Update("DELETE FROM `mt_timebans` WHERE `name`='" + args[0] + "';");

	} else if (bantype == 3) {
	    sender.sendMessage(ChatColor.GOLD + "Die globale Sperre von " + args[0] + " wurde aufgehoben!");
	    MySQL.Update("DELETE FROM `mt_bans` WHERE `name`='" + args[0] + "';");
	    MySQL.Update("DELETE FROM `mt_globalbans` WHERE `name`='" + args[0] + "';");
	    MySQL.Update("DELETE FROM `mt_timebans` WHERE `name`='" + args[0] + "';");
	} else
	    sender.sendMessage(ChatColor.RED + args[0] + " hat keine Sperre!");
    }
}
