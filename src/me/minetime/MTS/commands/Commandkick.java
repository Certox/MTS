package me.minetime.MTS.commands;

import me.minetime.MTS.MTSData;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.clazz.User;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Commandkick extends CommandMT {
    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {

	if (args.length < 2) {
	    throw new VerwendungsException("/kick [Spielername] [Grund]");
	}

	if (new User(args[0]).isOnline() == false) {
	    throw new Exception(ChatColor.GOLD + args[0] + ChatColor.RED + " ist nicht online.");
	}

	if (user.getPlayer().getName().equalsIgnoreCase(args[0])) {
	    throw new Exception(ChatColor.RED + "Du kannst dich nicht selbst kicken.");
	}

	PermissionManager pex = PermissionsEx.getPermissionManager();
	for (PermissionGroup pg : pex.getUser(new User(args[0]).getPlayer()).getGroups()) {
	    if (pg.getName().equalsIgnoreCase("Dev")) {
		MTSData.authToLogout.add(user.getName());
		user.getPlayer().kickPlayer(ChatColor.RED + "Never kick a Developer!");
		Bukkit.broadcastMessage(ChatColor.BLUE + "Der Spieler " + ChatColor.GRAY + user.getName() + ChatColor.BLUE
			+ " hat versucht einen Developer zu kicken.");

		throw new Exception("Access denied!");
	    }
	}

	StringBuilder sb = new StringBuilder();
	for (int i = 1; i < args.length; i++) {
	    if (i != 0)
		sb.append(' ');
	    sb.append(args[i]);
	}
	String grund = sb.toString();

	MTSData.authToLogout.add(args[0]);
	Bukkit.getServer()
		.getPlayer(args[0])
		.kickPlayer(
			ChatColor.RED + "Du wurdest von " + ChatColor.GOLD + user.getPlayer().getName() + ChatColor.RED
				+ " gekickt. Grund:\n" + ChatColor.AQUA + grund);
	Bukkit.broadcastMessage(ChatColor.BLUE + "Der Spieler " + ChatColor.GRAY + args[0] + ChatColor.BLUE + " wurde von "
		+ ChatColor.YELLOW + user.getName() + ChatColor.BLUE + " gekickt. Grund:" + ChatColor.AQUA + grund);
    }

}