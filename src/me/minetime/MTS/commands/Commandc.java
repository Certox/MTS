package me.minetime.MTS.commands;

import static me.minetime.MTS.clazz.Message.msg;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;

import me.minetime.MTS.MTSData;
import me.minetime.MTS.MySQL;
import me.minetime.MTS.api.IClan;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.Clan;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.clazz.NotMove;
import me.minetime.MTS.clazz.User;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Commandc extends CommandMT {
    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {
	Player player = user.getPlayer();

	if (args.length == 0) {
	    // 1. Seite ausgeben
	    player.sendMessage(ChatColor.AQUA + "-------------- Clan-Befehle | Seite 1 --------------");
	    player.sendMessage(ChatColor.GOLD + "Clan-Befehle auflisten: " + ChatColor.DARK_AQUA + "/c 1 " + ChatColor.GOLD
		    + "und: " + ChatColor.DARK_AQUA + "/c 2");
	    player.sendMessage(ChatColor.GOLD + "Clan erstellen: " + ChatColor.DARK_AQUA
		    + "/c erstellen [clanname] [clank\u00dcrzel]");
	    player.sendMessage(ChatColor.GOLD + "Clan verlassen: " + ChatColor.DARK_AQUA + "/c verlassen");
	    player.sendMessage(ChatColor.GOLD + "Spieler einladen: " + ChatColor.DARK_AQUA + "/c einladen [spielername]");
	    player.sendMessage(ChatColor.GOLD + "Clanwarp: " + ChatColor.DARK_AQUA + "/c warp " + ChatColor.GOLD + "oder: "
		    + ChatColor.DARK_AQUA + "/cw");
	    player.sendMessage(ChatColor.GOLD + "Clan anzeigen: " + ChatColor.DARK_AQUA + "/c clan [spielername]");
	    player.sendMessage(ChatColor.GOLD + "Clanwarp \u00e4ndern: " + ChatColor.DARK_AQUA + "/c warp\u00e4ndern");
	    player.sendMessage(ChatColor.GOLD + "Spieler aus Clan kicken: " + ChatColor.DARK_AQUA + "/c kick [spielername]");
	} else {
	    boolean cmd = false;

	    if (args[0].equalsIgnoreCase("1")) {
		// 1. Seite ausgeben
		player.sendMessage(ChatColor.AQUA + "-------------- Clan-Befehle | Seite 1 --------------");
		player.sendMessage(ChatColor.GOLD + "Clan-Befehle auflisten: " + ChatColor.DARK_AQUA + "/c 1 "
			+ ChatColor.GOLD + "und: " + ChatColor.DARK_AQUA + "/c 2");
		player.sendMessage(ChatColor.GOLD + "Clan erstellen: " + ChatColor.DARK_AQUA
			+ "/c erstellen [clanname] [clank\u00dcrzel]");
		player.sendMessage(ChatColor.GOLD + "Clan verlassen: " + ChatColor.DARK_AQUA + "/c verlassen");
		player.sendMessage(ChatColor.GOLD + "Spieler einladen: " + ChatColor.DARK_AQUA + "/c einladen [spielername]");
		player.sendMessage(ChatColor.GOLD + "Clanwarp: " + ChatColor.DARK_AQUA + "/c warp " + ChatColor.GOLD
			+ "oder: " + ChatColor.DARK_AQUA + "/cw");
		player.sendMessage(ChatColor.GOLD + "Clan anzeigen: " + ChatColor.DARK_AQUA + "/c clan [spielername]");
		player.sendMessage(ChatColor.GOLD + "Clanwarp \u00e4ndern: " + ChatColor.DARK_AQUA + "/c warp\u00e4ndern");
		player.sendMessage(ChatColor.GOLD + "Spieler aus Clan kicken: " + ChatColor.DARK_AQUA
			+ "/c kick [spielername]");
		cmd = true;
	    }

	    if (args[0].equalsIgnoreCase("2")) {
		// 2. Seite ausgeben
		player.sendMessage(ChatColor.AQUA + "-------------- Clan-Befehle | Seite 2 --------------");
		player.sendMessage(ChatColor.GOLD + "Clanmember anzeigen: " + ChatColor.DARK_AQUA + "/c list [clanname]");
		player.sendMessage(ChatColor.GOLD + "Clanchat: " + ChatColor.DARK_AQUA + "%[nachricht]");
		player.sendMessage(ChatColor.GOLD + "Clanbank - abheben: " + ChatColor.DARK_AQUA + "/c abheben [geldsumme]");
		player.sendMessage(ChatColor.GOLD + "Clanbank - einzahlen: " + ChatColor.DARK_AQUA
			+ "/c einzahlen [geldsumme]");
		player.sendMessage(ChatColor.GOLD + "Clan-Status: " + ChatColor.DARK_AQUA + "/c stats [clanname]");
		player.sendMessage(ChatColor.GOLD + "Clanlevel aufsteigen: " + ChatColor.DARK_AQUA + "/c levelup");
		player.sendMessage(ChatColor.GOLD + "ClanAllianz gr\u00fcnden/aufl\u00f6sen: " + ChatColor.DARK_AQUA
			+ "/c allianz [clanname]");
		player.sendMessage(ChatColor.GOLD + "Allianzchat: " + ChatColor.DARK_AQUA + "&[nachricht]");
		player.sendMessage(ChatColor.GOLD + "Clanranking anzeigen: " + ChatColor.DARK_AQUA + "/c ranking");
		cmd = true;
	    }

	    if (args[0].equalsIgnoreCase("erstellen")) {
		cmd = true;
		if (args.length == 3) {
		    c_erstellen(user, args);
		} else {
		    throw new VerwendungsException("/c erstellen [clanname] [clank\u00fcrzel]");
		}
	    }

	    if (args[0].equalsIgnoreCase("warp")) {
		cmd = true;
		c_warp(user, args);
	    }

	    if (args[0].equalsIgnoreCase("einladen")) {
		cmd = true;
		if (args.length == 2) {
		    c_einladen(user, args);
		} else {
		    throw new VerwendungsException("/c einladen [spielername]");
		}
	    }

	    if (args[0].equalsIgnoreCase("warp\u00e4ndern")) {
		cmd = true;
		c_warpaendern(user, args);
	    }

	    if (args[0].equalsIgnoreCase("verlassen")) {
		cmd = true;
		c_verlassen(user, args);
	    }

	    if (args[0].equalsIgnoreCase("kick")) {
		cmd = true;
		if (args.length == 2) {
		    c_kick(user, args);
		} else {
		    throw new VerwendungsException("/c kick [spielername]");
		}
	    }

	    if (args[0].equalsIgnoreCase("list")) {
		cmd = true;
		c_list(user, args);
	    }

	    if (args[0].equalsIgnoreCase("clan")) {
		cmd = true;
		c_clan(user, args);
	    }

	    if (args[0].equalsIgnoreCase("levelup")) {
		cmd = true;
		c_levelup(user, args);
	    }

	    if (args[0].equalsIgnoreCase("einzahlen")) {
		cmd = true;
		if (args.length == 2) {
		    c_einzahlen(user, args);
		} else {
		    throw new VerwendungsException("/c einzahlen [geldsumme]");
		}
	    }

	    if (args[0].equalsIgnoreCase("abheben")) {
		cmd = true;
		if (args.length == 2) {
		    c_abheben(user, args);
		} else {
		    throw new VerwendungsException("/c abheben [geldsumme]");
		}
	    }

	    if (args[0].equalsIgnoreCase("stats")) {
		cmd = true;
		c_stats(user, args);
	    }

	    if (args[0].equalsIgnoreCase("allianz")) {
		cmd = true;
		if (args.length != 2) {
		    throw new VerwendungsException("/c allianz [clanname]");
		} else {
		    c_allianz(user, args);
		}
	    }

	    if (args[0].equalsIgnoreCase("ranking")) {
		cmd = true;
		c_ranking(user, args);
	    }

	    if (cmd == false) {
		// 1. Seite ausgeben
		player.sendMessage(ChatColor.AQUA + "-------------- Clan-Befehle | Seite 1 --------------");
		player.sendMessage(ChatColor.GOLD + "Clan-Befehle auflisten: " + ChatColor.DARK_AQUA + "/c 1 "
			+ ChatColor.GOLD + "und: " + ChatColor.DARK_AQUA + "/c 2");
		player.sendMessage(ChatColor.GOLD + "Clan erstellen: " + ChatColor.DARK_AQUA
			+ "/c erstellen [clanname] [clank\u00fcrzel]");
		player.sendMessage(ChatColor.GOLD + "Clan verlassen: " + ChatColor.DARK_AQUA + "/c verlassen");
		player.sendMessage(ChatColor.GOLD + "Spieler einladen: " + ChatColor.DARK_AQUA + "/c einladen [spielername]");
		player.sendMessage(ChatColor.GOLD + "Clanwarp: " + ChatColor.DARK_AQUA + "/c warp " + ChatColor.GOLD
			+ "oder: " + ChatColor.DARK_AQUA + "/cw");
		player.sendMessage(ChatColor.GOLD + "Clan anzeigen: " + ChatColor.DARK_AQUA + "/c clan [spielername]");
		player.sendMessage(ChatColor.GOLD + "Clanwarp \u00e4ndern: " + ChatColor.DARK_AQUA + "/c warp\u00e4ndern");
		player.sendMessage(ChatColor.GOLD + "Spieler aus Clan kicken: " + ChatColor.DARK_AQUA
			+ "/c kick [spielername]");
	    }
	}
    }

    private void c_allianz(IUser u, String[] args) throws Exception {
	boolean stat = true;

	Player player = u.getPlayer();

	if (stat == true) {
	    if (u.getClan() == null) {
		stat = false;
		player.sendMessage(ChatColor.RED + "Du bist in keinem Clan.");
	    }
	}

	if (stat == true) {
	    if (!u.getName().equalsIgnoreCase(u.getClan().getClanOwner().getName())) {
		stat = false;
		player.sendMessage(ChatColor.RED + "Du bist nicht der ClanOwner deines Clans!");
	    }
	}

	if (stat == true) {
	    if (args[1].equalsIgnoreCase(u.getClan().getClanName())) {
		stat = false;
		player.sendMessage(ChatColor.RED + "Du kannst nicht mit deinem eigenen Clan eine Allianz schliessen.");
	    }
	}

	if (stat == true) {
	    if (MTSData.clannameExist(args[1]) == false) {
		stat = false;
		player.sendMessage(ChatColor.RED + "Der Clan existiert nicht.");
	    }
	}

	boolean haveAlli = false;

	if (stat == true) {
	    for (IClan ic : u.getClan().getClanAlliances()) {
		if (ic.getClanName().equalsIgnoreCase(args[1])) {
		    haveAlli = true;
		}
	    }
	}

	if (stat == true) {
	    if (haveAlli == true) {
		int clanid1 = u.getClan().getId();
		int clanid2 = 0;

		try {
		    ResultSet rs = MySQL.Query("SELECT `id` FROM `mt_clans` WHERE `name`='" + args[1] + "';");

		    while (rs.next()) {
			clanid2 = rs.getInt(1);
		    }

		    rs.close();
		} catch (Exception err) {
		    System.out.println("MTS-Fehler: " + err);
		}

		MySQL.Update("DELETE FROM `mt_clans_allianzen` WHERE `from`='" + clanid1 + "' AND `to`='" + clanid2 + "';");
		MySQL.Update("DELETE FROM `mt_clans_allianzen` WHERE `from`='" + clanid2 + "' AND `to`='" + clanid1 + "';");

		new Clan(clanid1).sendClanMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Clan]" + ChatColor.AQUA
			+ "Die Allianz mit " + ChatColor.GOLD + args[1] + ChatColor.AQUA + " wurde aufgel\u00F6st.");
		new Clan(clanid2).sendClanMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Clan]" + ChatColor.AQUA
			+ "Die Allianz mit " + ChatColor.GOLD + u.getClan().getClanName() + ChatColor.AQUA
			+ " wurde aufgel\u00F6st.");

	    } else {
		boolean stat2 = true;

		IClan c1 = u.getClan();
		int clanid2 = 0;

		try {
		    ResultSet rs = MySQL.Query("SELECT `id` FROM `mt_clans` WHERE `name`='" + args[1] + "';");

		    while (rs.next()) {
			clanid2 = rs.getInt(1);
		    }

		    rs.close();
		} catch (Exception err) {
		    System.out.println("MTS-Fehler: " + err);
		}

		IClan c2 = new Clan(clanid2);

		if (stat2 == true) {
		    if (u.getClan().getClanAlliances().size() >= MTSData.getClanlevelAllianzen(u.getClan().getClanLevel())) {
			stat2 = false;
			player.sendMessage(ChatColor.RED + "Im Clanlevel " + ChatColor.GOLD + u.getClan().getClanLevel()
				+ ChatColor.RED + " kann ein Clan nur " + ChatColor.GOLD
				+ MTSData.getClanlevelAllianzen(u.getClan().getClanLevel()) + ChatColor.RED
				+ " Allianzen besitzen.");
		    }
		}

		if (stat2 == true) {
		    if (c2.getClanAlliances().size() >= MTSData.getClanlevelAllianzen(c2.getClanLevel())) {
			stat2 = false;
			player.sendMessage(ChatColor.RED + "Der andere Clan kann keine Allianzen mehr schlie\u00DFen.");
		    }
		}

		if (stat2 == true) {
		    if (c2.getClanOwner().isOnline() == false) {
			stat2 = false;
			player.sendMessage(ChatColor.RED + "Der andere Clanowner " + ChatColor.GOLD
				+ c2.getClanOwner().getPlayer().getName() + ChatColor.RED + " ist nicht online.");
		    }
		}

		if (stat2 == true) {
		    Player owner = c2.getClanOwner().getPlayer();

		    owner.sendMessage(ChatColor.AQUA + "Der Clan " + ChatColor.GOLD + c1.getClanName() + ChatColor.AQUA
			    + " will mit deinem Clan eine Allianz schlie\u00DFen.");
		    owner.sendMessage(ChatColor.AQUA + "Zum Annehmen, Tippe: " + ChatColor.GOLD + "/annehmen allianz");

		    player.sendMessage(ChatColor.GREEN + "Die Allianzanfrage wurde gesendet.");

		    MTSData.requestAllianz.put(owner.getName(), c1.getId());
		}
	    }
	}
    }

    private void c_stats(IUser user, String[] args) throws Exception {
	Integer clanid = 0;

	if (args.length == 1) {
	    if (user.isInClan())
		clanid = user.getClan().getId();
	    else
		throw new Exception("Du bist in keinem Clan!");
	} else if (args.length == 2) {
	    if (!MTSData.clannameExist(args[1])) {
		throw new Exception(msg("clanStatsFail"));
	    } else {
		try {
		    ResultSet rs = MySQL.Query("SELECT `id` FROM `mt_clans` WHERE `name`='" + args[1] + "';");

		    while (rs.next()) {
			clanid = rs.getInt(1);
		    }

		    rs.close();
		} catch (Exception err) {
		    System.out.println("MTS-Fehler: " + err);
		}
		try {
		    ResultSet rs = MySQL.Query("SELECT `id` FROM `mt_clans` WHERE `short`='" + args[1] + "';");

		    while (rs.next()) {
			clanid = rs.getInt(1);
		    }

		    rs.close();
		} catch (Exception err) {
		    System.out.println("MTS-Fehler: " + err);
		}
	    }
	} else
	    throw new VerwendungsException("/c stats [clanname]");

	Clan c = new Clan(clanid);
	List<IClan> allianzen = c.getClanAlliances();
	String msg = "";

	if (allianzen.size() == 1) {
	    for (IClan s : allianzen) {
		msg = ChatColor.DARK_AQUA + s.getClanName();
	    }
	} else {

	    for (IClan s : allianzen)
		msg = msg + ChatColor.DARK_AQUA + s.getClanName() + ChatColor.AQUA + ", ";

	    if (msg.length() > 2)
		msg = msg.substring(0, msg.length() - 2);
	    else
		msg = "/";

	}

	Integer onlineMember = 0;

	for (IUser iu : c.getClanMembers()) {
	    if (iu.isOnline()) {
		onlineMember = onlineMember + 1;
	    }
	}

	Player player = user.getPlayer();

	player.sendMessage(ChatColor.AQUA + "[]-------- " + ChatColor.GOLD + "ClanStats: " + c.getClanName()
		+ ChatColor.AQUA + " --------[]");
	player.sendMessage(ChatColor.GOLD + "Gr\u00fcnder: " + ChatColor.DARK_AQUA + c.getClanFounder().getName()
		+ ChatColor.AQUA + " | " + ChatColor.GOLD + "Owner: " + ChatColor.DARK_AQUA + c.getClanOwner().getName());
	player.sendMessage(ChatColor.GOLD + "Clank\u00fcrzel: " + ChatColor.DARK_AQUA + c.getuncolouredClanTag()
		+ ChatColor.AQUA + " | " + ChatColor.GOLD + "Platz im Ranking: " + ChatColor.DARK_AQUA + c.getClanRank());
	player.sendMessage(ChatColor.GOLD + "Mitglieder: " + ChatColor.DARK_AQUA + c.getClanMembers().size() + "/"
		+ MTSData.getClanLevelMaxMember(c.getClanLevel()) + ChatColor.AQUA + " | " + ChatColor.GREEN + onlineMember
		+ ChatColor.DARK_AQUA + " Online");
	player.sendMessage(ChatColor.GOLD + "Clanbank: " + ChatColor.DARK_AQUA + BigDecimal.valueOf(c.getClanMoney())
		+ " MineTaler" + ChatColor.AQUA + " | " + ChatColor.GOLD + "Clanlevel: " + ChatColor.DARK_AQUA
		+ c.getClanLevel());
	player.sendMessage(ChatColor.GOLD + "Clankills: " + ChatColor.DARK_AQUA + c.getClanKills() + ChatColor.AQUA + " | "
		+ ChatColor.GOLD + "Clantode: " + ChatColor.DARK_AQUA + c.getClanDeaths());
	player.sendMessage(ChatColor.GOLD + "Allianzen: " + ChatColor.DARK_AQUA + msg);

    }

    private void c_abheben(IUser user, String[] args) throws Exception {
	double summe = 0.0;

	try {
	    summe = Double.parseDouble(args[1]);
	} catch (NumberFormatException nfe) {
	    throw new Exception(msg("clanAbhebenFail"));
	}

	if (user.isInClan() == false) {
	    throw new Exception(msg("clanAbhebenFail1"));
	}

	if (user.hasClanRight("bankgive") == false) {
	    throw new Exception(msg("clanAbhebenFail2"));
	}

	if (checkDouble(summe) == false) {
	    throw new Exception(msg("clanAbhebenFail3"));
	}

	if (user.getClan().getClanMoney() < Double.parseDouble(args[1])) {
	    throw new Exception(msg("clanAbhebenFail4", args[1]));
	}

	// Geld abzeihen
	user.getClan().delClanMoney(Double.parseDouble(args[1]));

	// Geld adden
	user.setMoney(Double.parseDouble(args[1]) + user.getMoney());

	// MSG senden
	user.getPlayer().sendMessage(
		ChatColor.GREEN + "Es wurden " + ChatColor.GOLD + args[1] + ChatColor.GREEN + " MineTaler abgehoben.");
    }

    private void c_einzahlen(IUser user, String[] args) throws Exception {
	double summe = 0.0;

	try {
	    summe = Double.parseDouble(args[1]);
	} catch (NumberFormatException nfe) {
	    throw new Exception(msg("clanEinzahlenFail"));
	}

	if (user.isInClan() == false) {
	    throw new Exception(msg("clanEinzahlenFail1"));
	}

	if (user.hasClanRight("bankgive") == false) {
	    throw new Exception(msg("clanEinzahlenFail2"));
	}

	if (checkDouble(summe) == false) {
	    throw new Exception(msg("clanEinzahlenFail3"));
	}

	if (user.getMoney() < Double.parseDouble(args[1])) {
	    throw new Exception(msg("clanEinzahlenFail4", args[1]));
	}

	// Geld abzeihen
	user.setMoney(user.getMoney() - Double.parseDouble(args[1]));

	// Geld adden
	user.getClan().addClanMoney(Double.parseDouble(args[1]));

	// MSG senden
	user.getPlayer().sendMessage(
		ChatColor.GREEN + "Es wurden " + ChatColor.GOLD + args[1] + ChatColor.GREEN + " MineTaler eingezahlt.");
    }

    private void c_levelup(IUser u, String[] args) throws Exception {
	Player player = u.getPlayer();

	boolean stat = true;
	int nextLevel = u.getClan().getClanLevel() + 1;

	if (stat == true) {
	    if (u.getClan() == null) {
		stat = false;
		player.sendMessage(ChatColor.RED + "Du bist in keinem Clan!");
	    }
	}

	if (stat == true) {
	    if (u.equals(u.getClan().getClanOwner())) {
		stat = false;
		player.sendMessage(ChatColor.RED + "Du bist nicht der Owner deines Clans!");
	    }
	}

	if (stat == true) {
	    if (u.getClan().getClanLevel() == 10) {
		stat = false;
		player.sendMessage(ChatColor.RED + "Dein Clan hat breits das maximale Clanlevel erreicht!");
	    }
	}

	if (stat == true) {
	    // Bedingungen für nächstes clanlevel
	    boolean right = true;

	    if (u.getClan().getClanMoney() < MTSData.getClanlevelMoney(nextLevel)) {
		right = false;
		player.sendMessage(ChatColor.RED + "Aufstieg nicht m\u00F6glich! Dein Clan muss mindestens "
			+ ChatColor.GOLD + MTSData.getClanlevelMoney(nextLevel) + " MineTaler" + ChatColor.RED
			+ " auf der Clanbank haben. Aber Ihr habt erst " + ChatColor.GOLD + u.getClan().getClanMoney()
			+ " MineTaler");
	    }

	    if (u.getClan().getClanKills() < MTSData.getClanlevelKills(nextLevel) && right == true) {
		right = false;
		player.sendMessage(ChatColor.RED + "Aufstieg nicht m\u00F6glich! Dein Clan braucht mindestens "
			+ ChatColor.GOLD + MTSData.getClanlevelKills(nextLevel) + " Kills" + ChatColor.RED
			+ ". Aber Ihr habt erst " + ChatColor.GOLD + u.getClan().getClanKills() + " Kills");
	    }

	    if (right == false) {
		stat = false;
	    }
	}

	if (stat == true) {
	    // Clanlevel aufsteigen!
	    u.getClan().delClanMoney(MTSData.getClanlevelMoney(nextLevel));

	    // Level +1 machen
	    u.getClan().setClanLevel(nextLevel);

	    // Jedem aus clan ne MSG senden
	    u.getClan()
		    .sendClanMessage(
			    ChatColor.GOLD + "" + ChatColor.BOLD + "[Clan] " + ChatColor.RESET + ChatColor.AQUA
				    + "Der Clan ist nun auf Level " + ChatColor.GOLD + nextLevel + ChatColor.AQUA
				    + " aufgestiegen!");
	}
    }

    private void c_clan(IUser user, String[] args) throws Exception {
	String from = "";

	if (args.length == 1) {
	    from = user.getPlayer().getName();
	}
	if (args.length == 2) {
	    from = args[1];
	}

	if (new User(from).isInClan() == false) {
	    throw new Exception(msg("clanClanFail"));
	}

	user.getPlayer().sendMessage(
		ChatColor.GREEN + "Clan des Spielers " + ChatColor.GOLD + from + ChatColor.GREEN + ": " + ChatColor.GOLD
			+ new User(from).getClan().getClanName());
    }

    private void c_list(IUser user, String[] args) throws Exception {

	if (user.isInClan() == false) {
	    throw new Exception(msg("clanListFail"));
	}

	if (args.length > 0) {
	    IClan clan = user.getClan();
	    List<IUser> clanmembers = clan.getClanMembers();
	    user.getPlayer()
		    .sendMessage(
			    ChatColor.AQUA + "[]------- " + ChatColor.GOLD + "Liste der Spieler im Clan ("
				    + clanmembers.size() + "/" + MTSData.getClanLevelMaxMember(clan.getClanLevel()) + ") "
				    + ChatColor.AQUA + "-------[]");

	    String msg = "";
	    for (IUser u : clanmembers) {
		if (u.isOnline()) {
		    msg = msg + ChatColor.GREEN + u.getName() + ChatColor.AQUA + " | ";
		} else {
		    msg = msg + ChatColor.GRAY + u.getName() + ChatColor.AQUA + " | ";
		}
	    }

	    msg = msg.substring(0, msg.length() - 3);
	    user.getPlayer().sendMessage(msg);
	}
    }

    private void c_kick(IUser user, String[] args) throws Exception {

	if (user.getPlayer().getName().equalsIgnoreCase(args[1])) {
	    throw new Exception(msg("clanKickFail"));
	}

	if (user.isInClan() == false) {
	    throw new Exception(msg("clanKickFail1"));
	}

	if (user.hasClanRight("kick") == false) {
	    throw new Exception(msg("clanKickFail2"));
	}

	if (user.getClan().getClanOwner().getPlayer().getName().equalsIgnoreCase(args[1])) {
	    throw new Exception(msg("clanKickFail3"));
	}

	if (new User(args[1]).isInClan()) {
	    if (new User(args[1]).getClan().getId().equals(user.getClan().getId()) == false) {
		throw new Exception(msg("clanKickFail4", args[1]));
	    }
	} else {
	    throw new Exception(msg("clanKickFail4", args[1]));
	}

	MySQL.Update("DELETE FROM `mt_clans_members` WHERE `name`='" + args[1] + "';");
	user.getPlayer().sendMessage(
		ChatColor.GREEN + "Du hast den Spieler " + ChatColor.AQUA + args[1] + ChatColor.GREEN
			+ " aus dem Clan gekickt.");

	if (new User(args[1]).isOnline()) {
	    new User(args[1]).getPlayer().sendMessage(
		    ChatColor.GREEN + "Du wurdest von " + ChatColor.AQUA + user.getPlayer().getName() + ChatColor.GREEN
			    + " aus dem Clan gekickt.");
	}
    }

    private void c_verlassen(IUser user, String[] args) throws Exception {

	if (!user.isInClan())
	    throw new Exception(msg("clanVerlassenFail"));
	if (user.getClan().getuncolouredClanTag().equalsIgnoreCase("MTT")) {
	    user.getPlayer().sendMessage(ChatColor.RED + "Boese!");
	} else {
	    if (user.getClan().getClanOwner() != null) {
		if (user.getClan().getClanOwner().getName().equalsIgnoreCase(user.getName())) {
		    user.getPlayer()
			    .sendMessage(
				    ChatColor.AQUA
					    + "Du bist der Besitzer des Clans. Wenn du ihn verl\u00E4sst wird der Clan aufge\u00F6st.");
		    user.getPlayer().sendMessage(
			    ChatColor.AQUA + "Wenn du ihn verlassen willst, Tippe: '/annehmen verlassen'");

		    if (MTSData.requestClanverlassen.contains(user.getPlayer().getName())) {
			MTSData.requestClanverlassen.remove(user.getPlayer().getName());
		    }
		    MTSData.requestClanverlassen.add(user.getPlayer().getName());
		} else {
		    MySQL.Update("DELETE FROM `mt_clans_members` WHERE `name`='" + user.getPlayer().getName() + "';");
		    user.getPlayer().sendMessage(ChatColor.GREEN + "Du hast den Clan verlassen.");
		    user.loadFriede();
		}
	    } else {
		MySQL.Update("DELETE FROM `mt_clans_members` WHERE `name`='" + user.getPlayer().getName() + "';");
		user.getPlayer().sendMessage(ChatColor.GREEN + "Du hast den Clan verlassen.");
		user.loadFriede();
	    }
	}

    }

    private void c_warpaendern(IUser user, String[] args) throws Exception {

	if (user.isInClan() == false) {
	    throw new Exception(msg("clanWarpaendernFail"));
	}

	if (!user.hasClanRight("warp")) {
	    throw new Exception(msg("clanWarpaendernFail1"));
	}

	user.getClan().setClanWarp(user.getPlayer().getLocation());

	user.getPlayer().sendMessage(ChatColor.GREEN + "Der ClanWarp wurde erfolgreich ge\u00e4ndert.");
    }

    private void c_einladen(IUser user, String[] args) throws Exception {
	// Prüfen, ob spieler in nem clan ist
	if (user.isInClan() == false) {
	    throw new Exception(msg("clanEinladenFail"));
	}

	if (user.hasClanRight("invite") == false) {
	    throw new Exception(msg("clanEinladenFail1"));
	}

	if (new User(args[1]).isOnline() == false) {
	    throw new Exception(msg("clanEinladenFail2", args[1]));
	}

	if (new User(args[1]).isInClan()) {
	    throw new Exception(msg("clanEinladenFail3", args[1]));
	}

	if (MTSData.requestClaneinladungen.containsKey(args[1])) {
	    MTSData.requestClaneinladungen.remove(args[1]);
	}

	String clanname = user.getClan().getClanName();

	MTSData.requestClaneinladungen.put(args[1].toLowerCase(), user.getClan().getId());

	user.getPlayer().sendMessage(ChatColor.AQUA + "Du hast dem Spieler eine Einladung gesendet.");
	Bukkit.getPlayer(args[1]).sendMessage(
		ChatColor.DARK_AQUA + "Der Spieler " + ChatColor.GRAY + user.getPlayer().getName() + ChatColor.DARK_AQUA
			+ " hat dich in den Clan " + ChatColor.AQUA + clanname + ChatColor.DARK_AQUA + " eingeladen.");
	Bukkit.getPlayer(args[1]).sendMessage(ChatColor.DARK_AQUA + "Zum beitreten, Tippe: '/annehmen einladung'");

    }

    private void c_warp(IUser user, String[] args) throws Exception {

	if (user.isInClan() == false)
	    throw new Exception(msg("clanWarpFail"));

	NotMove nm = new NotMove(user, user.getClan().getClanWarp(), "deinem " + ChatColor.YELLOW + "Clan Warp" + ChatColor.RESET);
	nm.go(false);
    }

    private void c_erstellen(IUser user, String[] args) throws Exception {
	Player player = user.getPlayer();

	// benötigte Variablen definieren
	String clanname = args[1];
	String clankuerzel = args[2];

	if ((clanname.length() < 2) || (clanname.length() > 12)) {
	    throw new Exception(msg("clannameLaengeFail"));
	}

	if ((clankuerzel.length() < 1) || (clankuerzel.length() > 4)) {
	    throw new Exception(msg("clankuerzelLaengeFail"));

	}

	if (MTSData.clannameExist(clanname)) {
	    throw new Exception(msg("clannameExist"));
	}

	if (MTSData.clantagExist(clankuerzel)) {
	    throw new Exception(msg("clankuerzelExist"));
	}

	if (MTSData.requestClaneinladungen.containsKey(player.getName())) {
	    MTSData.requestClaneinladungen.remove(player.getName());
	}

	// Clan in DB einfügen
	MySQL.Update("INSERT INTO `mt_clans` "
		+ "(name, short, founder, owner, world, x, y, z, money, kills, deaths, level)" + " VALUES " + "('"
		+ clanname + "', '" + clankuerzel + "', '" + player.getName() + "', '" + player.getName() + "', " + "'"
		+ player.getWorld().getName() + "', '" + player.getLocation().getX() + "', '" + player.getLocation().getY()
		+ "', '" + player.getLocation().getZ() + "'," + "'0', '0', '0', '1');");

	// Id des gerade erstellen clans finden
	int clanId = 0;

	try {
	    ResultSet rs = MySQL.Query("SELECT `id` FROM `mt_clans` WHERE `owner`='" + player.getName() + "';");

	    while (rs.next()) {
		clanId = rs.getInt(1);
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	// Ihn als mitglied des clans adden
	MySQL.Update("INSERT INTO `mt_clans_members` (`id`, `name`, `invite`, `kick`, `warp`, `banktake`, `bankgive`, `rechte`) VALUES"
		+ " ('" + clanId + "', '" + player.getName() + "', '1', '1', '1', '1', '1', '1');");

	// ErfolgsMSGS ausgeben
	player.sendMessage(msg("clanErstellenErfolg", clanname));

    }

    public void c_ranking(IUser user, String[] args) {
	Player player = user.getPlayer();

	int zahl = 1;

	player.sendMessage(ChatColor.AQUA + "---------- Clanranking | Top 10 ----------");
	player.sendMessage(ChatColor.AQUA + "Platz | Name | Owner | Kills | Tode");
	try {
	    ResultSet rs = MySQL
		    .Query("SELECT `name`, `owner`, `kills`, `deaths` FROM `mt_clans` ORDER BY `kills` DESC LIMIT 10;");

	    while (rs.next()) {
		player.sendMessage(ChatColor.AQUA + "#" + zahl + ChatColor.DARK_AQUA + " | " + ChatColor.GOLD
			+ rs.getString(1) + ChatColor.DARK_AQUA + " | " + ChatColor.GOLD + rs.getString(2)
			+ ChatColor.DARK_AQUA + " | " + ChatColor.GOLD + rs.getInt(3) + ChatColor.DARK_AQUA + " | "
			+ ChatColor.GOLD + rs.getInt(4));
		zahl++;
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}
    }

    private boolean checkDouble(double d) {
	boolean g = false;
	try {
	    if ((String.valueOf(d).length() - String.valueOf(d).indexOf('.')) > 3) {
		g = false;
	    } else {
		g = true;
	    }

	    if (d < 0) {
		g = false;
	    }
	} catch (Exception ex) {
	    System.out.println("MTS-Fehler: " + ex.getMessage());
	}
	return g;
    }

}
