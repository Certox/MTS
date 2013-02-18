package me.minetime.MTS.commands;

import me.minetime.MTS.MTSData;
import me.minetime.MTS.clazz.CommandMT;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Commandglobalmute extends CommandMT {

    @Override
    public void run(final CommandSender sender, final String commandLabel, final String[] args) {

	boolean neu = !MTSData.globalmute;
	MTSData.globalmute = neu;
	String mess = neu ? "aktiviert" : "deaktiviert";
	Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "[MTS] " + ChatColor.AQUA + "Globalmute " + mess);
    }

}
