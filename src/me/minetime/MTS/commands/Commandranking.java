package me.minetime.MTS.commands;

import java.sql.ResultSet;

import me.minetime.MTS.MySQL;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Commandranking extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {

	Player player = user.getPlayer();
	player.sendMessage(ChatColor.AQUA + "--------- Kills | Top 5 ---------");

	try {
	    ResultSet rs = MySQL.Query("SELECT `kills`,`name` FROM `mt_stats` ORDER BY kills DESC LIMIT 5;");

	    int zahl = 1;
	    while (rs.next()) {
		player.sendMessage(ChatColor.AQUA + "#" + String.valueOf(zahl) + ChatColor.DARK_AQUA + " | "
			+ ChatColor.GOLD + String.valueOf(rs.getInt(1)) + ChatColor.DARK_AQUA + " | " + ChatColor.GOLD
			+ rs.getString(2));
		zahl++;
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MySQL-Error: " + err.getMessage());
	    throw new Exception("MySQL-Fehler");

	}

    }

}
