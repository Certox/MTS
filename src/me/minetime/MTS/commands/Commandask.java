package me.minetime.MTS.commands;

import java.sql.ResultSet;

import me.minetime.MTS.MTSData;
import me.minetime.MTS.MySQL;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.clazz.User;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Commandask extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {
	Player p = user.getPlayer();
	PermissionManager pex = PermissionsEx.getPermissionManager();

	if (pex.has(p, "MTS.warnen")) {
	    if (args.length == 0) {
		showMenu(p);

	    } else {
		if (args[0].equalsIgnoreCase("chat")) {
		    if (args.length == 2)
			chat(args[1], user.getName());

		    else
			throw new VerwendungsException("/ask chat [id|username|last]");

		} else if (args[0].equalsIgnoreCase("solve") || args[0].equalsIgnoreCase("done")) {
		    if (args.length == 1)
			solve(user.getName());

		    else
			throw new VerwendungsException("/ask solve");

		} else if (args[0].equalsIgnoreCase("list")) {
		    if (args.length > 1)
			throw new VerwendungsException("/ask list");

		    else
			list(p);

		} else
		    showMenu(p);
	    }
	} else

	    support(p, args);

    }

    public void showMenu(Player p) {
	p.sendMessage(ChatColor.AQUA + "-------------|Support Befehle|-------------");
	p.sendMessage(ChatColor.GOLD + "Aktuelle Supportanfragen sehen: " + ChatColor.AQUA + "/ask list");
	p.sendMessage(ChatColor.GOLD + "Eine Supportanfrage bearbeiten: " + ChatColor.AQUA + "/ask chat [id|username]");
	p.sendMessage(ChatColor.GOLD + "Eine Supportanfrage fertigstellen: " + ChatColor.AQUA + "/ask solve");
	p.sendMessage(ChatColor.GOLD + "---> Geht nur wenn man mit der Person im Supportchat ist!");

    }

    public void support(Player p, String args[]) throws Exception {
	int ii = 0;
	try {
	    ResultSet rs = MySQL
		    .Query("SELECT `name` FROM `mt_support` WHERE `solver`='' AND `name`='" + p.getName() + "';");

	    while (rs.next())
		ii = 1;

	    rs.close();
	} catch (Exception err) {
	    System.out.println("Report-Error: " + err);
	}
	if (ii == 1)
	    throw new Exception("Du hast bereits eine Supportanfrage gesendet!");
	StringBuilder sb = new StringBuilder();
	for (int i = 0; i < args.length; i++) {
	    if (i != 0)
		sb.append(' ');
	    sb.append(args[i]);
	}
	String message = sb.toString();
	MySQL.Update("INSERT INTO `mt_support` (name, message,solver) VALUES ('" + p.getName() + "', '" + message + "','');");

	p.sendMessage(ChatColor.RED + "[Support] " + ChatColor.YELLOW
		+ "Dein Supportticket wurde abgeschickt! Das Team wird dir so schnell wie möglich helfen!");

	sendAllOps(p.getName(), message);
    }

    public void chat(String string, String user) throws Exception {
	if (string.equalsIgnoreCase(user)) {
	    throw new Exception("Nicht du selbst, junge!");
	}
	String name = "";
	String message = "";
	if (string.equalsIgnoreCase("last")) {

	    try {
		ResultSet rs = MySQL
			.Query("SELECT `name`, `message` FROM `mt_support` WHERE `solver`='' ORDER BY `id` DESC LIMIT 1;");

		if (rs.next()) {
		    name = rs.getString(1);
		    message = rs.getString(2);
		}

		rs.close();
	    } catch (Exception err) {
		System.out.println("Report-Error: " + err);
	    }
	    if (name == "")
		throw new Exception("Keine Tickets vorhanden!");

	} else if (string.matches("\\d+")) {
	    try {
		ResultSet rs = MySQL.Query("SELECT `name`, `message` FROM `mt_support` WHERE `solver`='' AND `id`='"
			+ Integer.parseInt(string) + "';");

		while (rs.next()) {
		    name = rs.getString(1);
		    message = rs.getString(2);
		}

		rs.close();
	    } catch (Exception err) {
		System.out.println("Report-Error: " + err);
	    }
	    if (name == "")
		throw new Exception("Ticket nicht vorhanden!");
	} else {
	    MySQL.Update("INSERT INTO `mt_support` (name, message,solver) VALUES ('" + string + "', '','');");
	    name = string;
	}
	if (Bukkit.getServer().getPlayer(name) == null) {
	    MySQL.Update("DELETE FROM `mt_support` WHERE `solver`='' AND `name`='" + name + "';");
	    throw new Exception("Der Spieler ist nicht online und sein Ticket wurde gelöscht!");
	}
	if (MTSData.support.containsValue(name))
	    throw new Exception("Der Spieler wird bereits supportet!");
	if (MTSData.support.containsKey(user))
	    throw new Exception("Du bist bereits am supporten!");

	if (message != "" && message != null) {
	    Bukkit.getServer()
		    .getPlayer(user)
		    .sendMessage(
			    ChatColor.GOLD + "Du bist nun in einem Chat mit " + ChatColor.RED + name + ChatColor.GOLD
				    + ". Nachricht: " + ChatColor.RED + message);
	} else {
	    Bukkit.getServer()
		    .getPlayer(user)
		    .sendMessage(
			    ChatColor.GOLD + "Du bist nun in einem Chat mit " + ChatColor.RED + name + ChatColor.GOLD
				    + ". Er hat keine Nachricht hinterlassen!");
	}
	Player pl = Bukkit.getServer().getPlayer(name);
	for (int i = 0; i < 100; i++)
	    pl.sendMessage("");
	pl.sendMessage(ChatColor.GOLD + "Nun kannst du deine Fragen an " + user + " stellen!");
	MTSData.support.put(user, name);

    }

    public void solve(String name) {

	if (MTSData.support.containsKey(name)) {

	    String user = MTSData.support.get(name);
	    MySQL.Update("UPDATE `mt_support` SET solver='" + name + "' WHERE name='" + user + "';");
	    MTSData.support.remove(name);
	    new User(name).getPlayer().sendMessage(ChatColor.GREEN + "Danke, dass du supportet hast :)");
	    new User(user).getPlayer().sendMessage(
		    ChatColor.GREEN + "Vielen Dank, dass du das Support-System in Anspruch genommen hast.");
	    new User(user).getPlayer().sendMessage(ChatColor.GREEN + "Wir wünschen dir weiterhin viel Spaß auf MineTime ;)");

	} else {
	    new User(name).getPlayer().sendMessage(ChatColor.RED + "Verarsch mich nicht, du bist nicht am Supporten JUNGE!");
	}

    }

    public void list(Player p) {
	boolean available = false;
	try {
	    ResultSet rs = MySQL.Query("SELECT `name`,`id`,`message` FROM `mt_support` WHERE `solver`='' ORDER BY id;");

	    while (rs.next()) {
		p.sendMessage(ChatColor.GOLD + "ID: " + ChatColor.YELLOW + String.valueOf(rs.getInt(2)) + ChatColor.GOLD
			+ " Name: " + ChatColor.YELLOW + rs.getString(1) + ChatColor.GOLD + "   Nachricht: "
			+ ChatColor.YELLOW + rs.getString(3));
		available = true;
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("Report-Error: " + err);
	}
	if (!available) {
	    p.sendMessage(ChatColor.RED + "[Support] " + ChatColor.YELLOW
		    + "Gute Arbeit Leute! Keine ausstehenden Supportanfragen!");
	}
    }

    public void sendAllOps(String name, String msg) {
	PermissionManager pex = PermissionsEx.getPermissionManager();
	if (msg.length() > 1) {
	    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
		if (pex.has(p, "MTS.warnen"))
		    p.sendMessage(ChatColor.RED + "[Support] " + ChatColor.YELLOW + name + ChatColor.GOLD + " fragt: " + msg);

	    }
	} else
	    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
		if (pex.has(p, "MTS.warnen"))
		    p.sendMessage(ChatColor.RED + "[Support] " + ChatColor.YELLOW + name + ChatColor.GOLD
			    + " braucht DEINE Hilfe!");

	    }
    }

}
