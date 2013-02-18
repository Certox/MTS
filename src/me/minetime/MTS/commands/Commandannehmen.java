package me.minetime.MTS.commands;

import static me.minetime.MTS.clazz.Message.msg;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import me.minetime.MTS.MTSData;
import me.minetime.MTS.MySQL;
import me.minetime.MTS.api.IClan;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.Clan;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.clazz.User;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Commandannehmen extends CommandMT {

    @Override
    public void run(final IUser user, final String CommandLabel, final String[] args) throws Exception {

	Player player = user.getPlayer();

	if (args.length < 1) {
	    throw new VerwendungsException("/annehmen [tag]");
	}

	if (args[0].equalsIgnoreCase("antrag")) {
	    if (MTSData.requestAntrag.containsKey(user.getName())) {
		User partner = new User(MTSData.requestAntrag.get(user.getName()));
		MTSData.requestAntrag.remove(user.getName());

		// Jetzige Zeit definieren
		Date myDate = new Date();
		SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yyyy");
		df2.setTimeZone(TimeZone.getDefault());
		df2.format(myDate);
		Calendar gc2 = new GregorianCalendar();
		gc2.setTime(myDate);
		Date now = gc2.getTime();

		if (partner.isMarried() == true) {
		    throw new Exception(ChatColor.GOLD + partner.getName() + ChatColor.RED + " ist bereits verheiratet.");
		}

		if (partner.getMarriageLog().contains(df2.format(now))) {
		    throw new Exception(ChatColor.GOLD + partner.getName() + ChatColor.RED
			    + " hat heute bereits geheiratet.");
		}

		if (partner.isOnline() == false) {
		    throw new Exception(ChatColor.GOLD + partner.getName() + ChatColor.RED + " ist offline.");
		}

		if (user.getMarriageLog().contains(df2.format(now))) {
		    throw new Exception("Du hast heute bereits geheiratet.");
		}

		user.setPartner(partner.getName());
		Bukkit.broadcastMessage(ChatColor.GOLD + "[Info] " + ChatColor.AQUA + user.getName() + " und "
			+ partner.getName() + " sind nun verheiratet!");
		MTSData.marriedChatTags.add(user.getName());
		MTSData.marriedChatTags.add(partner.getName());
	    } else {
		user.getPlayer().sendMessage(ChatColor.AQUA + "Nichts zum Annehmen!");
	    }
	}

	if (args[0].equalsIgnoreCase("allianz")) {
	    if (MTSData.requestAllianz.containsKey(player.getName())) {
		int otherClanid = MTSData.requestAllianz.get(player.getName());
		MTSData.requestAllianz.remove(player.getName());

		if (user.isInClan() == false) {
		    throw new Exception(msg("annehmenAllianzFail"));
		}

		if (player.getName().equalsIgnoreCase(user.getClan().getClanOwner().getPlayer().getName()) == false) {
		    throw new Exception(msg("annehmenAllianzFail1"));
		}

		if (user.getClan().getClanAlliances().size() >= MTSData.getClanlevelAllianzen(user.getClan().getClanLevel())) {
		    throw new Exception(msg("annehmenAllianzFail2", user.getClan().getClanLevel(),
			    MTSData.getClanlevelAllianzen(user.getClan().getClanLevel())));
		}

		if (new Clan(otherClanid).getClanAlliances().size() >= MTSData.getClanlevelAllianzen(new Clan(otherClanid)
			.getClanLevel())) {
		    throw new Exception(msg("annehmenAllianzFail3"));
		}

		for (IClan ic : user.getClan().getClanAlliances()) {
		    if (ic.getId() == otherClanid) {
			throw new Exception(msg("annehmenAllianzFail3"));
		    }
		}

		// Clan Allianz eintragen
		MySQL.Update("INSERT INTO `mt_clans_allianzen` (`from`, `to`) VALUES ('" + user.getClan().getId() + "', '"
			+ otherClanid + "');");
		MySQL.Update("INSERT INTO `mt_clans_allianzen` (`from`, `to`) VALUES ('" + otherClanid + "', '"
			+ user.getClan().getId() + "');");

		user.getClan().sendClanMessage(
			ChatColor.GOLD + "" + ChatColor.BOLD + "[Clan]" + ChatColor.AQUA + "Eine Allianz mit "
				+ ChatColor.GOLD + new Clan(otherClanid).getClanName() + ChatColor.AQUA
				+ " wurde geschlossen.");
		new Clan(otherClanid).sendClanMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Clan]" + ChatColor.AQUA
			+ "Eine Allianz mit " + ChatColor.GOLD + user.getClan().getClanName() + ChatColor.AQUA
			+ " wurde geschlossen.");

	    } else {
		player.sendMessage(ChatColor.AQUA + "Nichts zum Annehmen.");
	    }
	}

	else if (args[0].equals("verlassen")) {

	    if (MTSData.requestClanverlassen.contains(player.getName())) {
		if (user.getName().equalsIgnoreCase(user.getClan().getClanOwner().getName())) {
		    int clanid = user.getClan().getId();

		    double d = new Clan(clanid).getClanMoney();
		    user.addMoney(d);

		    new Clan(clanid).sendClanMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[CLAN] " + ChatColor.DARK_GRAY
			    + player.getName() + ChatColor.DARK_RED + " hat den Clan aufgelöst!");
		    player.sendMessage(ChatColor.GREEN + "Du hast das Geld aus der Clan-Bank erhalten.");
		    MySQL.Update("DELETE FROM `mt_clans_members` WHERE id='" + clanid + "';");
		    MySQL.Update("DELETE FROM `mt_clans` WHERE id='" + clanid + "';");
		} else {
		    player.sendMessage(ChatColor.RED + "Fehler: Du bist nicht Besitzer des Clans");
		}
	    } else {
		player.sendMessage(ChatColor.AQUA + "Nichts zum annehmen.");
	    }
	}

	if (args[0].equalsIgnoreCase("einladung")) {
	    if (MTSData.requestClaneinladungen.containsKey(player.getName().toLowerCase())) {
		int clanid = MTSData.requestClaneinladungen.get(player.getName().toLowerCase());
		MTSData.requestClaneinladungen.remove(player.getName().toLowerCase());

		if (new Clan(clanid).getClanMembers().size() >= MTSData.getClanLevelMaxMember(new Clan(clanid)
			.getClanLevel())) {
		    throw new Exception(msg("annehmenEinladungFail"));
		}

		user.setClan(clanid);
		new Clan(clanid).sendClanMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[CLAN] " + ChatColor.DARK_GRAY
			+ player.getName() + ChatColor.GRAY + " ist dem Clan beigetreten.");
	    } else {
		player.sendMessage(ChatColor.AQUA + "Nichts zum annehmen.");
	    }
	}

	else if (args[0].equalsIgnoreCase("friede")) {

	    int made = 0;

	    String from = "";
	    String to = "";

	    for (String now : MTSData.requestFriede) {
		String[] teil = now.split("#");
		if (teil[0].equalsIgnoreCase(player.getName())) {
		    made = 1;
		    from = teil[0];
		    to = teil[1];
		}
	    }

	    if (made == 0)
		throw new Exception("Du hast gerade keine Friedensanfrage!");

	    MySQL.Update("INSERT INTO `mt_friede` (`from`, `to`) VALUES ('" + from + "', '" + to + "');");
	    MySQL.Update("INSERT INTO `mt_friede` (`from`, `to`) VALUES ('" + to + "', '" + from + "');");

	    MTSData.TBL_friede.remove(user.getName());
	    MTSData.TBL_friede.put(user.getName(), user.loadFriede());

	    MTSData.requestFriede.remove(from + "#" + to);
	    player.sendMessage(ChatColor.GREEN + "Du hast mit dem Spieler " + ChatColor.GOLD + to + ChatColor.GREEN
		    + " Friede geschlossen.");

	    User userto = new User(to);

	    if (userto.isOnline()) {
		MTSData.TBL_friede.remove(userto.getName());
		MTSData.TBL_friede.put(userto.getName(), user.loadFriede());
	    }

	}

    }
}
