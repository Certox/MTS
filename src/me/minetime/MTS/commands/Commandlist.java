package me.minetime.MTS.commands;

import me.minetime.MTS.clazz.CommandMT;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Commandlist extends CommandMT {

    @Override
    public void run(final CommandSender sender, final String commandLabel, final String[] args) {
	int max = Bukkit.getServer().getMaxPlayers();
	int anzahl = 0;
	anzahl = Bukkit.getServer().getOnlinePlayers().length;
	sender.sendMessage(ChatColor.GRAY + "-----" + ChatColor.GOLD + "MineTime" + ChatColor.GRAY + "-----");
	if (anzahl >= max)
	    sender.sendMessage(ChatColor.RED + "" + anzahl + " / " + max + ChatColor.GREEN + " sind online");
	else
	    sender.sendMessage(ChatColor.GRAY + "" + anzahl + " / " + max + ChatColor.GREEN + " sind online");
    }

}
