package me.minetime.MTS.commands;

import static me.minetime.MTS.clazz.Message.msg;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import me.minetime.MTS.MTSData;
import me.minetime.MTS.MySQL;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.clazz.User;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Commandp extends CommandMT {
    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {

	Player player = user.getPlayer();

	if (args.length == 0) {
	    player.sendMessage(ChatColor.AQUA + "-------------- PlayerBefehle --------------");
	    player.sendMessage(ChatColor.GOLD + "Friede schliessen/auflösen: " + ChatColor.DARK_AQUA
		    + "/p friede [spielername]");
	    player.sendMessage(ChatColor.GOLD + "Status von Spieler: " + ChatColor.DARK_AQUA + "/p stats [spielername]");
	    player.sendMessage(ChatColor.GOLD + "Friedensliste: " + ChatColor.DARK_AQUA + "/p list [seitenzahl]");
	    player.sendMessage(ChatColor.GOLD + "Heiraten/Scheiden: " + ChatColor.DARK_AQUA + "/p heiraten [spielername]");
	} else {
	    if (args[0].equalsIgnoreCase("heiraten")) { // player
							// heiraten/scheiden

		// Jetzige Zeit definieren
		Date myDate = new Date();
		SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yyyy");
		df2.setTimeZone(TimeZone.getDefault());
		df2.format(myDate);
		Calendar gc2 = new GregorianCalendar();
		gc2.setTime(myDate);
		Date now = gc2.getTime();

		if (user.isMarried() == true) {
		    // Scheiden
		    User partner = new User(user.getPartner());

		    MySQL.Update("DELETE FROM `mt_partner` WHERE `from`='" + user.getName() + "' OR `to`='" + user.getName()
			    + "';");
		    if (partner.isOnline()) {
			partner.getPlayer().sendMessage(ChatColor.RED + user.getName() + " hat sich von dir getrennt.");
		    }

		    user.getPlayer().sendMessage(ChatColor.RED + "Du hast dich von " + partner.getName() + " getrennt.");
		    MTSData.marriedChatTags.remove(partner.getName());
		    MTSData.marriedChatTags.remove(user.getName());
		} else {
		    if (args.length < 2) {
			throw new VerwendungsException("/p heiraten [spielername]");
		    } else {
			// Heiraten
			User partner = new User(args[1]);

			if (user.getName().equalsIgnoreCase(args[1])) {
			    throw new Exception("Du kannst dich nicht selbst heiraten.");
			}

			if (partner.isMarried() == true) {
			    throw new Exception(ChatColor.GOLD + partner.getName() + ChatColor.RED
				    + " ist bereits verheiratet.");
			}

			if (partner.isOnline() == false) {
			    throw new Exception(ChatColor.GOLD + partner.getName() + ChatColor.RED + " ist offline.");
			}

			if (partner.getMarriageLog().contains(df2.format(now))) {
			    throw new Exception(ChatColor.GOLD + partner.getName() + ChatColor.RED
				    + " hat heute bereits geheiratet.");
			}

			if (user.getMarriageLog().contains(df2.format(now))) {
			    throw new Exception("Du hast heute bereits geheiratet.");
			}
			MTSData.requestAntrag.remove(partner.getName());
			MTSData.requestAntrag.put(partner.getName(), user.getName());
			partner.getPlayer().sendMessage(
				ChatColor.AQUA + "Der Spieler " + ChatColor.GOLD + user.getName() + ChatColor.AQUA
					+ " hat dir einen Heiratsantrag gestellt. Annehmen mit: /annehmen antrag");
			user.getPlayer().sendMessage(ChatColor.GREEN + "Der Antrag wurde gesendet.");

		    }
		}
	    }

	    if (args[0].equalsIgnoreCase("friede")) { // Player friede

		if (args.length == 2) {

		    int isfriede = 0;
		    if (Bukkit.getServer().getPlayer(args[1]) != null) {
			if (player.getName().equals(args[1]))
			    throw new Exception(msg("friedeMitSichSelber"));
			try {
			    ResultSet rs = MySQL.Query("SELECT `from` FROM `mt_friede` WHERE `from`='" + player.getName()
				    + "' AND `to`='" + args[1] + "';");
			    while (rs.next()) {
				isfriede = 1;
			    }

			    rs.close();
			} catch (Exception err) {
			    System.out.println("bClan00: " + err);
			}

			if (isfriede == 1) {
			    // 5-Sek cooldown Timer starten
			    player.sendMessage(ChatColor.RED + "Der Friede mit " + ChatColor.GOLD + args[1] + ChatColor.RED
				    + " wird in 5 Sekunden aufgelöst.");
			    Bukkit.getServer()
				    .getPlayer(args[1])
				    .sendMessage(
					    ChatColor.RED + "Der Friede mit " + ChatColor.GOLD + player.getName()
						    + ChatColor.RED + " wird in 5 Sekunden aufgelöst.");

			    Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("MTS"),
				    new Runnable() {
					public void run() {
					    MySQL.Update("DELETE FROM `mt_friede` WHERE `from`='"
						    + user.getPlayer().getName() + "' AND `to`='" + args[1] + "';");
					    MySQL.Update("DELETE FROM `mt_friede` WHERE `from`='" + args[1] + "' AND `to`='"
						    + user.getPlayer().getName() + "';");
					    if (Bukkit.getServer().getPlayer(args[1]) != null) {
						User usera = new User(args[1]);
						MTSData.TBL_friede.remove(usera.getName());
						MTSData.TBL_friede.put(usera.getName(), usera.loadFriede());

						Bukkit.getServer()
							.getPlayer(args[1])
							.sendMessage(
								ChatColor.RED + "Der Spieler " + ChatColor.GOLD
									+ user.getPlayer().getName() + ChatColor.RED
									+ " hat den Frieden mit dir aufgelöst.");
					    }
					    if (user.isOnline()) {
						MTSData.TBL_friede.remove(user.getName());
						MTSData.TBL_friede.put(user.getName(), user.loadFriede());
						user.getPlayer().sendMessage(
							ChatColor.RED + "Der Friede mit " + ChatColor.GOLD + args[1]
								+ ChatColor.RED + " wurde aufgelöst.");
					    }

					}
				    }, 100L);

			} else {

			    String remover = "";

			    for (String s : MTSData.requestFriede) {
				String[] teil = s.split("#");
				if (teil[0].equalsIgnoreCase(args[0])) {
				    remover = s;
				}
			    }

			    MTSData.requestFriede.remove(remover);
			    MTSData.requestFriede.add(args[1] + "#" + player.getName());

			    player.sendMessage(ChatColor.AQUA + "Du hast dem Spieler " + ChatColor.GOLD + args[1]
				    + ChatColor.AQUA + " Frieden angeboten");
			    Bukkit.getServer()
				    .getPlayer(args[1])
				    .sendMessage(
					    ChatColor.AQUA + "Der Spieler " + ChatColor.GOLD + player.getName()
						    + ChatColor.AQUA + " hat dir Frieden angeboten.");
			    Bukkit.getServer()
				    .getPlayer(args[1])
				    .sendMessage(
					    ChatColor.AQUA + "Zum Annehmen tippe: " + ChatColor.GOLD + "/annehmen friede");

			}

		    } else {
			player.sendMessage(ChatColor.RED + "Der Spieler ist nicht online!");
		    }
		} else {
		    throw new VerwendungsException("/p friede [spielername]");
		}
	    }

	    if (args[0].equalsIgnoreCase("stats")) { // Friedensstatus

		if (args.length == 1) {
		    cmd_stats(player.getName(), player);
		} else if (args.length == 2) {
		    cmd_stats(args[1], player);

		} else {
		    throw new VerwendungsException("/p stats [spielername]");
		}

	    }
	    if (args[0].equalsIgnoreCase("list")) {
		int seitenzahl = 1;
		if (args.length == 1)
		    seitenzahl = 1;
		else if (args.length == 2) {
		    try {
			seitenzahl = Integer.parseInt(args[1]);
		    } catch (NumberFormatException e) {
			throw new Exception("Ungueltige Seitenzahl!");
		    }
		} else {
		    player.sendMessage(ChatColor.RED + "Verwendung: " + ChatColor.AQUA + "/p list [Seitenzahl]");
		    return;
		}

		int anzahl = 0;
		List<String> pFriede = new ArrayList<String>(); // alle Spieler
								// mit denne man
								// Frieden hat
		boolean next = false; // nächste Seite?
		try {
		    ResultSet rs = MySQL.Query("SELECT `to` FROM `mt_friede` WHERE `from`='" + player.getName() + "';");
		    while (rs.next()) {
			anzahl++;
			String name = rs.getString(1);
			pFriede.add(name);
		    }
		} catch (Exception err) {
		    System.out.println("[MTS]Fehler: " + err);
		}
		if (anzahl / 15 >= seitenzahl)
		    next = true;
		if (anzahl == 0) {
		    throw new Exception(ChatColor.RED + "Du hast bisher noch kein Frieden geschlossen!");
		}
		if (anzahl / 15 < seitenzahl - 1) {
		    throw new Exception(ChatColor.RED + "Seite " + seitenzahl + " gibt es nicht!");

		}
		StringBuilder liste = new StringBuilder();
		for (int i = (seitenzahl - 1) * 15; i < pFriede.size() && i < seitenzahl * 15; i++) {

		    String name = pFriede.get(i);
		    ChatColor isOnline = ChatColor.GRAY;

		    if (Bukkit.getServer().getPlayer(name) != null)
			isOnline = ChatColor.GREEN;

		    if (i == (seitenzahl * 15) - 1 || i == pFriede.size() - 1) {
			liste.append(isOnline + name);
		    } else
			liste.append(isOnline + name + ChatColor.GOLD + " | ");

		}

		player.sendMessage(ChatColor.AQUA + "|-------------------" + ChatColor.GOLD + "Friedensliste["
			+ (seitenzahl) + "]" + ChatColor.AQUA + "-------------------|");
		player.sendMessage(liste.toString());
		if (next)
		    player.sendMessage(ChatColor.AQUA + "Auf die nächste Seite mit /p list " + (seitenzahl + 1));
	    }

	}

    }

    private void cmd_stats(String name, Player player) throws Exception {
	User user = new User(name);

	if (!user.hasAccount()) {
	    throw new Exception("Dieser Spieler ist unserem System nicht bekannt.");
	}

	// ---- RANKING-PLATZ
	int n = 0;
	boolean done = false;

	try {
	    ResultSet rs = MySQL.Query("SELECT `name` FROM `mt_stats` ORDER BY `kills` DESC;");

	    while (rs.next() && done == false) {
		n++;
		if (rs.getString(1).equalsIgnoreCase(name)) {
		    done = true;
		}
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	// ---- RANKING-PLATZ

	// KD ausrechnen
	double KD = user.getKills();
	if (user.getDeaths() != 0) {
	    KD = (double) user.getKills() / user.getDeaths(); // KD ausrechnen
	    KD = Math.round(KD * 100) / 100.0; // KD auf 2 nachkommastellen
					       // runden
	}

	// Spielzeit berechnen
	int time = Integer.valueOf(user.getPlaytime());
	int day = 0;
	int hour = 0;
	int min = 0;
	String ausgabe = "";

	if (time == 0) {
	    ausgabe = "Keine Angabe";
	} else {
	    if (time >= 60) {
		while (time >= 60) {
		    hour = hour + 1;
		    time = time - 60;
		}

		min = time;

		if (hour >= 24) {
		    while (hour >= 24) {
			day = day + 1;
			hour = hour - 24;
		    }
		}
	    } else {
		min = time;
	    }

	    ausgabe = day + " Tage, " + hour + " Stunden, " + min + " Minuten";
	}

	// Partner
	String partner = "-";
	if (user.isMarried())
	    partner = user.getPartner();

	// Friede
	String wort = " kein";
	if (user.isFriede(player.getName()))
	    wort = "";

	// Clan
	String clan = "-";
	if (user.isInClan() == true)
	    clan = user.getClan().getcolouredClanTag();

	player.sendMessage(ChatColor.AQUA + "[]----- " + ChatColor.GOLD + "Stats von " + name + ChatColor.AQUA + " -----[]");
	player.sendMessage(ChatColor.GOLD + "Ranking: " + ChatColor.DARK_AQUA + "#" + n);
	player.sendMessage(ChatColor.GOLD + "Kills: " + ChatColor.DARK_AQUA + user.getKills() + ChatColor.AQUA + " | "
		+ ChatColor.GOLD + "Tode: " + ChatColor.DARK_AQUA + user.getDeaths() + ChatColor.AQUA + " | "
		+ ChatColor.GOLD + "KD: " + ChatColor.DARK_AQUA + KD);
	player.sendMessage(ChatColor.GOLD + "Clan: " + clan);
	player.sendMessage(ChatColor.GOLD + "Partner: " + ChatColor.DARK_AQUA + partner);
	player.sendMessage(ChatColor.GOLD + "Spielzeit: " + ChatColor.DARK_AQUA + ausgabe);
	player.sendMessage(ChatColor.DARK_AQUA + "Du hast mit dem Spieler" + wort + " Frieden.");

    }
}