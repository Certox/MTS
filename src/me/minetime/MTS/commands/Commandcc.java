package me.minetime.MTS.commands;

import static me.minetime.MTS.clazz.Message.msg;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Commandcc extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) {

	PermissionManager pex = PermissionsEx.getPermissionManager();

	for (Player p : Bukkit.getOnlinePlayers()) {
	    if (!pex.has(p, "MTS.cc"))
		for (int i = 0; i < 120; i++)
		    p.sendMessage("");
	    p.sendMessage(msg("chatclear", user.getPlayer().getName()));
	}

    }

    @Override
    public void run(final CommandSender sender, final String commandLabel, final String[] args) {

	PermissionManager pex = PermissionsEx.getPermissionManager();

	for (Player p : Bukkit.getOnlinePlayers()) {
	    for (int i = 0; i < 120; i++)
		if (!pex.has(p, "MTS.cc"))
		    p.sendMessage("");
	    p.sendMessage(msg("chatclear", "der Konsole"));
	}

    }

}
