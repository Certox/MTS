package me.minetime.MTS.commands;

import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Commandregeln extends CommandMT {

    @Override
    public void run(final IUser user, final String CommandLabel, final String[] args) {

	Player player = user.getPlayer();

	try {

	    if (args.length == 1) {
		if (args[0].equals("2")) {
		    player.sendMessage(ChatColor.AQUA + "--------------" + ChatColor.BOLD + " Regeln | 2/3 "
			    + ChatColor.RESET + ChatColor.AQUA + "--------------");
		    player.sendMessage(ChatColor.AQUA + "#9:" + ChatColor.GOLD + " Keine /kill oder Alt+F4 Aufforderungen");
		    player.sendMessage(ChatColor.AQUA + "#10:" + ChatColor.GOLD
			    + " Keine Verst\u00F6sse gegen das Deutsche Gesetz");
		    player.sendMessage(ChatColor.AQUA + "#11:" + ChatColor.GOLD
			    + " Kein Lava und Wasser in Umgebung von protecteten \u00F6ffentlichen Gebieten");
		    player.sendMessage(ChatColor.AQUA + "#12:" + ChatColor.GOLD + " Keine Erpressungen aller Art");
		    player.sendMessage(ChatColor.AQUA + "#13:" + ChatColor.GOLD + " Keine Enderperlen " + ChatColor.BOLD
			    + "im Kampf");
		    player.sendMessage(ChatColor.AQUA
			    + "Das MineTimeTeam beh\u00E4lt sich vor, die Regeln jederzeit zu \u00E4ndern!");
		}

	    } else {
		player.sendMessage(ChatColor.AQUA + "--------------" + ChatColor.BOLD + " Regeln | 1/3 " + ChatColor.RESET
			+ ChatColor.AQUA + "--------------");
		player.sendMessage(ChatColor.AQUA + "#1:" + ChatColor.GOLD + " Kein Spamming/Flaming");
		player.sendMessage(ChatColor.AQUA + "#2:" + ChatColor.GOLD + " Keine Beleidigungen aller Art");
		player.sendMessage(ChatColor.AQUA + "#3:" + ChatColor.GOLD + " Kein Caps/Grossschrift");
		player.sendMessage(ChatColor.AQUA + "#4:" + ChatColor.GOLD
			+ " Keine Werbung (YouTube, Minecraft, Teamspeak etc.)");
		player.sendMessage(ChatColor.AQUA + "#5:" + ChatColor.GOLD + " Kein Anst\u00F6ssiges Vehalten");
		player.sendMessage(ChatColor.AQUA + "#6:" + ChatColor.GOLD + " Kein Hackclient / X-Ray");
		player.sendMessage(ChatColor.AQUA + "#7:" + ChatColor.GOLD + " Kein /repair f\u00FCr andere User");
		player.sendMessage(ChatColor.AQUA + "#8:" + ChatColor.GOLD + " Kein /stack f\u00FCr andere User");
		player.sendMessage(ChatColor.AQUA + "Für die 2. Seite der Regeln: " + ChatColor.GOLD + "/regeln 2");
	    }

	} catch (Exception ex) {
	    System.out.println("MTS-Fehler: " + ex.getMessage());
	}
    }

}
