package me.minetime.MTS.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Commandevent extends CommandMT {

    public static boolean stat = false;
    public static List<String> players = new ArrayList<String>();
    public static HashMap<Integer, Location> kords = new HashMap<Integer, Location>();
    public static List<String> hasAlready = new ArrayList<String>();

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {
	Player player = user.getPlayer();

	if (args.length == 0) {
	    if (!player.isOp()) {
		throw new VerwendungsException("/event join");
	    } else {
		player.sendMessage(ChatColor.AQUA + "/event <togglejoin:ende:clear>");
	    }
	} else {
	    if (args[0].equalsIgnoreCase("clear")) {
		if (player.isOp()) {
		    hasAlready.clear();
		    player.sendMessage(ChatColor.GREEN + "Die Liste wurde geleert.");
		}
	    }

	    if (args[0].equalsIgnoreCase("join")) {
		if (stat == false) {
		    player.sendMessage(ChatColor.RED + "Du kannst das Event jetzt nicht betreten.");
		} else {
		    if (players.size() == 11) {
			player.sendMessage(ChatColor.RED + "Es sind bereits alle Plätze belegt.");
		    } else {
			if (hasAlready.contains(player.getName())) {
			    player.sendMessage(ChatColor.RED + "Du hattest bereits teilgenommen.");
			} else {
			    hasAlready.add(player.getName());
			    players.add(player.getName());
			    player.teleport(kords.get(players.size()));

			    player.sendMessage(ChatColor.GREEN + "Du wurdest teleportiert.");

			    if (players.size() == 11) {
				String msg = "";
				int counter = 0;

				for (String s : players) {
				    counter = counter + 1;
				    if (counter == 11) {
					msg = msg + ChatColor.DARK_AQUA + s;
				    } else {
					msg = msg + ChatColor.DARK_AQUA + s + ChatColor.AQUA + " | ";
				    }
				}

				Bukkit.broadcastMessage(ChatColor.GOLD + "[Event]" + ChatColor.AQUA
					+ "Alle Plätze sind nun belegt, Teilnehmer: " + msg);
			    }
			}
		    }
		}
	    }

	    if (args[0].equalsIgnoreCase("togglejoin")) {
		if (player.isOp()) {
		    if (stat == false) {
			stat = true;
			player.sendMessage(ChatColor.GREEN + "Die Spieler können nun joinen.");
		    } else {
			stat = false;
			player.sendMessage(ChatColor.GREEN + "Die Spieler können nun nicht mehr joinen.");
		    }
		}
	    }

	    if (args[0].equalsIgnoreCase("ende")) {
		if (player.isOp()) {
		    for (String s : players) {
			Bukkit.getPlayer(s).teleport(
				new Location(Bukkit.getServer().getWorld("MineTime.me Spawn Welt"), 19996.5, 74, 20084.5));
		    }

		    stat = false;
		    players.clear();

		    player.sendMessage(ChatColor.GREEN + "Event beendet.");
		}
	    }
	}
    }
}
