package me.minetime.MTS.commands;

import static me.minetime.MTS.clazz.Message.msg;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.clazz.Config;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Commandwl extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {

	Config mts_config = new Config();

	if (args.length == 0)
	    throw new VerwendungsException("/wl <add:remove:on:off:set>");

	if (args[0].equalsIgnoreCase("help"))
	    throw new VerwendungsException("/wl <add:remove:on:off:set>");

	if (args.length == 1) {
	    if (args[0].equalsIgnoreCase("add")) {
		throw new VerwendungsException("/wl add Spielername");
	    } else if (args[0].equalsIgnoreCase("remove")) {
		throw new VerwendungsException("/wl remove Spielername");
	    } else if (args[0].equalsIgnoreCase("on")) {
		mts_config.setBoolean("Whitelist.Enabled", true);
		user.getPlayer().sendMessage(ChatColor.GREEN + "Whitelist eingeschaltet.");
	    } else if (args[0].equalsIgnoreCase("off")) {
		mts_config.setBoolean("Whitelist.Enabled", false);
		user.getPlayer().sendMessage(ChatColor.GREEN + "Whitelist ausgeschaltet.");
	    } else if (args[0].equalsIgnoreCase("set")) {
		throw new Exception(msg("verwendung", "/wl set <Nachricht>"));
	    } else {
		throw new Exception(msg("verwendung", "/wl <add:remove:on:off:set>"));
	    }
	} else if (args.length == 2) {
	    PermissionManager pex = PermissionsEx.getPermissionManager();
	    if (args[0].equalsIgnoreCase("add")) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + args[1]);
		for (Player p : Bukkit.getOnlinePlayers()) {
		    if (pex.has(p, "MTS.Whitelist") && p != user.getPlayer()) {
			p.sendMessage(ChatColor.AQUA + user.getPlayer().getDisplayName() + ChatColor.GOLD + "hat "
				+ ChatColor.DARK_AQUA + args[1] + ChatColor.GOLD + " zur Whitelist dazugef\u00dcgt.");
		    }
		}
		user.getPlayer().sendMessage(
			ChatColor.GOLD + "Du hast " + ChatColor.DARK_AQUA + args[1] + ChatColor.GOLD
				+ " zur Whitelist dazugef\u00dcgt.");
	    } else if (args[0].equalsIgnoreCase("remove")) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist remove " + args[1]);
		for (Player p : Bukkit.getOnlinePlayers()) {
		    if (pex.has(p, "MTS.Whitelist") && p != user.getPlayer()) {
			p.sendMessage(ChatColor.AQUA + user.getPlayer().getDisplayName() + ChatColor.GOLD + " hat "
				+ ChatColor.DARK_AQUA + args[1] + ChatColor.GOLD + " von der Whitelist entfernt.");
		    }
		}
		user.getPlayer().sendMessage(
			ChatColor.GOLD + "Du hast " + ChatColor.DARK_AQUA + args[1] + ChatColor.GOLD
				+ " von der Whitelist entfernt.");
	    } else if (args[0].equalsIgnoreCase("set")) {
		String nachricht = "";
		nachricht = args[1];
		nachricht = nachricht.replace('#', '\u00a7');
		mts_config.setString("Whitelist.Nachricht", nachricht);
		user.getPlayer().sendMessage(ChatColor.GOLD + "Du hast die Nachricht abgeändert zu:");
		user.getPlayer().sendMessage(nachricht);
	    } else {
		throw new Exception(msg("verwendung", "/wl <add:remove:on:off:set>"));
	    }
	} else {
	    if (args[0].equalsIgnoreCase("set")) {
		String nachricht = "";

		for (int i = 1; i < args.length; i++) {
		    if (i == 1) {
			nachricht += args[i];
		    } else {
			nachricht += " " + args[i];
		    }
		}

		nachricht = nachricht.replace('#', '\u00a7');
		mts_config.setString("Whitelist.Nachricht", nachricht);
		user.getPlayer().sendMessage(ChatColor.GOLD + "Du hast die Nachricht abgeändert zu:");
		user.getPlayer().sendMessage(nachricht);
	    } else {
		throw new Exception(msg("verwendung", "/wl <add:remove:on:off:set>"));
	    }
	}

    }

}
