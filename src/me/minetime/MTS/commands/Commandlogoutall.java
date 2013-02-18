package me.minetime.MTS.commands;

import static me.minetime.MTS.clazz.Message.msg;
import me.minetime.MTS.MTSData;
import me.minetime.MTS.clazz.CommandMT;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Commandlogoutall extends CommandMT {

    @Override
    public void run(final CommandSender sender, final String commandLabel, final String[] args) {

	String reason = "Neustart";
	if (args.length > 0) {
	    for (String argumente : args) {
		if (reason.equalsIgnoreCase("Neustart")) {
		    reason = argumente;
		} else {
		    reason = reason + " " + argumente;
		}
	    }
	}

	int counter = 0;
	PermissionManager pex = PermissionsEx.getPermissionManager();
	for (Player p : Bukkit.getOnlinePlayers()) {

	    if (!pex.has(p, "MTS.logoutall")) {
		counter++;
		MTSData.authToLogout.add(p.getName());
		p.kickPlayer(msg("logoutall", reason));
	    }
	}
	Bukkit.getServer().broadcastMessage(
		ChatColor.GOLD + "[MTS]" + ChatColor.AQUA + " Es wurden " + counter + " Spieler ausgeloggt.");
    }
}
