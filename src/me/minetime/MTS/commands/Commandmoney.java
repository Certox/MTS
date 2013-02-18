package me.minetime.MTS.commands;

import static me.minetime.MTS.clazz.Message.msg;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.clazz.User;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Commandmoney extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {
	Player player = user.getPlayer();
	if (args.length == 0) {
	    player.sendMessage(ChatColor.AQUA + "Dein Geld: " + ChatColor.GOLD + user.getMoney() + " MineTaler");
	} else {

	    if (args[0].equalsIgnoreCase("pay")) {

		if (args.length != 3)
		    throw new VerwendungsException("/money pay [name] [geld]");

		double summe = 0.00;

		try {
		    summe = Double.parseDouble(args[2]);
		} catch (NumberFormatException nfe) {
		    throw new Exception("Die Geldsumme muss eine Zahl sein!");
		}

		if (summe > user.getMoney())
		    throw new Exception("Du kannst nicht mehr Geld überweisen, als du besitzt!");

		IUser u = new User(args[1]);
		if (!u.hasAccount())
		    throw new Exception("Der Account von " + args[1] + " existiert nicht!");

		if (summe <= 0)
		    throw new Exception("Der Wert darf nicht negativ sein!");

		if (String.valueOf(summe).length() - String.valueOf(summe).indexOf('.') > 3)
		    throw new Exception("Der Wert darf nicht mehr als 2 Nachkommastellen haben!");

		user.removeMoney(summe);
		u.addMoney(summe);
		player.sendMessage(ChatColor.GREEN + "Du hast dem Spieler " + ChatColor.GOLD + args[1] + ChatColor.GREEN
			+ " erfolgreich " + ChatColor.GOLD + summe + ChatColor.GREEN + " MineTaler überwiesen.");
		if (u.isOnline())
		    u.getPlayer().sendMessage(
			    ChatColor.GREEN + "Der Spieler " + ChatColor.GOLD + user.getName() + ChatColor.GREEN
				    + " hat dir " + ChatColor.GOLD + summe + ChatColor.GREEN + " MineTaler überwiesen.");

	    }

	    else if (args[0].equalsIgnoreCase("give")) {
		PermissionManager pex = PermissionsEx.getPermissionManager();
		if (pex.has(player, "MTS.money")) {
		    if (args.length != 3)
			throw new VerwendungsException("/money give [name] [geld]");

		    double summe = 0.00;

		    try {
			summe = Double.parseDouble(args[2]);
		    } catch (NumberFormatException nfe) {

			throw new Exception("Die Geldsumme muss eine Zahl sein!");

		    }

		    if (!new User(args[1]).hasAccount())
			throw new Exception("Der Account von " + args[1] + " existiert nicht!");

		    if (summe < 0)

			throw new Exception("Der Wert darf nicht negativ sein!");

		    if (String.valueOf(summe).length() - String.valueOf(summe).indexOf('.') > 3)

			throw new Exception("Der Wert darf nicht mehr als 2 Nachkommastellen haben!");

		    new User(args[1]).addMoney(summe);

		    player.sendMessage(ChatColor.GREEN + "Du hast dem Spieler " + ChatColor.GOLD + args[1] + ChatColor.GREEN
			    + " erfolgreich " + ChatColor.GOLD + summe + ChatColor.GREEN + " MineTaler gegeben.");
		    if (Bukkit.getServer().getPlayer(args[1]) != null)
			new User(args[1]).getPlayer().sendMessage(
				ChatColor.GREEN + "Dir wurden " + ChatColor.GOLD + summe + ChatColor.GREEN
					+ " MineTaler geschenkt!");

		} else {
		    throw new Exception(msg("noPermissions"));
		}
	    }

	    else if (args[0].equalsIgnoreCase("take")) {

		PermissionManager pex = PermissionsEx.getPermissionManager();
		if (pex.has(player, "MTS.money")) {
		    if (args.length != 3) {

			throw new VerwendungsException("/money take [name] [geld]");

		    }
		} else {
		    throw new Exception(msg("noPermissions"));
		}
	    }

	    else if (args[0].equalsIgnoreCase("set")) {

		PermissionManager pex = PermissionsEx.getPermissionManager();
		if (pex.has(player, "MTS.money")) {
		    if (args.length != 3)
			throw new VerwendungsException("/money set [name] [geld]");

		    double summe = 0.00;

		    try {
			summe = Double.parseDouble(args[2]);
		    } catch (NumberFormatException nfe) {
			throw new Exception("Die Geldsumme muss eine Zahl sein!");

		    }

		    IUser u = new User(args[1]);
		    if (!u.hasAccount())
			throw new Exception("Der Account von " + ChatColor.GOLD + args[1] + ChatColor.RED
				+ " existiert nicht!");

		    if (summe < 0)
			throw new Exception("Der Wert darf nicht negativ sein!");

		    if (String.valueOf(summe).length() - String.valueOf(summe).indexOf('.') > 3)
			throw new Exception("Der Wert darf nicht mehr als 2 Nachkommastellen haben!");

		    u.setMoney(summe);
		    player.sendMessage(ChatColor.GREEN + "Du hast den Kontostand von " + ChatColor.GOLD + args[1]
			    + ChatColor.GREEN + " auf " + ChatColor.GOLD + summe + ChatColor.GREEN + " MineTaler gesetzt.");
		    if (u.isOnline())
			u.getPlayer().sendMessage(
				ChatColor.DARK_RED + "Dein Kontostand wurde auf " + ChatColor.GOLD + summe + ChatColor.GOLD
					+ " gesetzt!");

		} else {
		    throw new Exception(msg("noPermissions"));
		}
	    } else {

		User u = new User(args[0]);
		if (!u.hasAccount()) {
		    player.sendMessage(ChatColor.RED + "Der Account von " + ChatColor.GOLD + args[0] + ChatColor.RED
			    + " existiert nicht!");
		} else {
		    player.sendMessage(ChatColor.AQUA + "Geld von " + ChatColor.GOLD + args[0] + ChatColor.AQUA + ": "
			    + ChatColor.GOLD + u.getMoney() + " MineTaler");
		}
	    }
	}
    }

    @Override
    public void run(final CommandSender sender, final String commandLabel, final String[] args) throws Exception {

	if (args.length != 3)
	    throw new VerwendungsException("/money <set|give> [name] [anzahl]");

	if (args[0].equalsIgnoreCase("give")) {
	    double summe = 0.00;

	    try {
		summe = Double.parseDouble(args[2]);
	    } catch (NumberFormatException nfe) {

		throw new Exception("Die Geldsumme muss eine Zahl sein!");

	    }

	    if (!new User(args[1]).hasAccount())
		throw new Exception("Der Account von " + args[1] + " existiert nicht!");

	    if (summe < 0)

		throw new Exception("Der Wert darf nicht negativ sein!");

	    if (String.valueOf(summe).length() - String.valueOf(summe).indexOf('.') > 3)

		throw new Exception("Der Wert darf nicht mehr als 2 Nachkommastellen haben!");

	    new User(args[1]).addMoney(summe);

	    sender.sendMessage(ChatColor.GREEN + "Du hast dem Spieler " + ChatColor.GOLD + args[1] + ChatColor.GREEN
		    + " erfolgreich " + ChatColor.GOLD + summe + ChatColor.GREEN + " MineTaler gegeben.");
	    if (Bukkit.getServer().getPlayer(args[1]) != null)
		new User(args[1]).getPlayer()
			.sendMessage(
				ChatColor.GREEN + "Dir wurden " + ChatColor.GOLD + summe + ChatColor.GREEN
					+ " MineTaler geschenkt!");

	}

	else if (args[0].equalsIgnoreCase("set")) {

	    double summe = 0.00;

	    try {
		summe = Double.parseDouble(args[2]);
	    } catch (NumberFormatException nfe) {
		throw new Exception("Die Geldsumme muss eine Zahl sein!");

	    }

	    IUser u = new User(args[1]);
	    if (u.hasAccount())
		throw new Exception("Der Account von " + ChatColor.GOLD + args[1] + ChatColor.RED + " existiert nicht!");

	    if (summe < 0)
		throw new Exception("Der Wert darf nicht negativ sein!");

	    if (String.valueOf(summe).length() - String.valueOf(summe).indexOf('.') > 3)
		throw new Exception("Der Wert darf nicht mehr als 2 Nachkommastellen haben!");

	    u.setMoney(summe);
	    sender.sendMessage(ChatColor.GREEN + "Du hast den Kontostand von " + ChatColor.GOLD + args[1] + ChatColor.GREEN
		    + " auf " + ChatColor.GOLD + summe + ChatColor.GREEN + " MineTaler gesetzt.");
	    if (u.isOnline())
		u.getPlayer().sendMessage(
			ChatColor.DARK_RED + "Dein Kontostand wurde auf " + ChatColor.GOLD + summe + ChatColor.GOLD
				+ " gesetzt!");

	} else
	    throw new VerwendungsException("/money <set|give> [name] [anzahl]");

    }

}
