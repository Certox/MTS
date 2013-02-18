package me.minetime.MTS.commands;

import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Commandteam extends CommandMT {

    @Override
    public void run(final IUser user, final String CommandLabel, final String[] args) {
	PermissionManager pex = PermissionsEx.getPermissionManager();
	String dev, admin, owner, payment, supp, cs, sponsor, mod;
	dev = admin = owner = payment = supp = cs = sponsor = mod = "";
	Player p = user.getPlayer();

	for (int i = 0; i < pex.getGroup("Dev").getUsers().length; i++) {

	    dev = dev + isOnline(pex.getGroup("Dev").getUsers()[i].getName()) + " " + ChatColor.AQUA + "| ";
	}

	for (int i = 0; i < pex.getGroup("Admin").getUsers().length; i++) {

	    admin = admin + isOnline(pex.getGroup("Admin").getUsers()[i].getName()) + " " + ChatColor.AQUA + "| ";
	}

	for (int i = 0; i < pex.getGroup("Sponsor").getUsers().length; i++) {

	    sponsor = sponsor + isOnline(pex.getGroup("Sponsor").getUsers()[i].getName()) + " " + ChatColor.AQUA + "| ";
	}

	for (int i = 0; i < pex.getGroup("Owner").getUsers().length; i++) {

	    owner = owner + isOnline(pex.getGroup("Owner").getUsers()[i].getName()) + " " + ChatColor.AQUA + "| ";
	}

	for (int i = 0; i < pex.getGroup("Payment").getUsers().length; i++) {

	    payment = payment + isOnline(pex.getGroup("Payment").getUsers()[i].getName()) + " " + ChatColor.AQUA + "| ";
	}

	for (int i = 0; i < pex.getGroup("Mod").getUsers().length; i++) {

	    mod = mod + isOnline(pex.getGroup("Mod").getUsers()[i].getName()) + " " + ChatColor.AQUA + "| ";
	}

	for (int i = 0; i < pex.getGroup("Supporter").getUsers().length; i++) {

	    supp = supp + isOnline(pex.getGroup("Supporter").getUsers()[i].getName()) + " " + ChatColor.AQUA + "| ";
	}

	for (int i = 0; i < pex.getGroup("CS").getUsers().length; i++) {

	    cs = cs + isOnline(pex.getGroup("CS").getUsers()[i].getName()) + " " + ChatColor.AQUA + "| ";
	}

	p.sendMessage(ChatColor.AQUA + "[]-------- " + ChatColor.GOLD + "Teammitglieder" + ChatColor.AQUA + " --------[]");
	p.sendMessage(ChatColor.DARK_RED + "Owner: " + owner.substring(0, owner.length() - 2) + ChatColor.DARK_RED
		+ "Sponsor: " + sponsor.substring(0, sponsor.length() - 2) + ChatColor.DARK_PURPLE + "Payment: "
		+ payment.substring(0, payment.length() - 2));
	p.sendMessage(ChatColor.DARK_AQUA + "Developer: " + dev.substring(0, dev.length() - 2));
	if (admin != "")
	    p.sendMessage(ChatColor.RED + "Game-Admin: " + admin.substring(0, admin.length() - 2));
	if (mod != "")
	    p.sendMessage(ChatColor.DARK_PURPLE + "Mod: " + mod.substring(0, mod.length() - 2));
	if (supp != "")
	    p.sendMessage(ChatColor.GREEN + "Supporter: " + supp.substring(0, supp.length() - 2));
	if (cs != "")
	    p.sendMessage(ChatColor.DARK_GREEN + "CS: " + cs.substring(0, cs.length() - 2));
    }

    private String isOnline(String string) {
	if (Bukkit.getServer().getPlayer(string) == null) {
	    string = ChatColor.GRAY + string;
	} else {
	    string = ChatColor.YELLOW + string;
	}
	return string;
    }

}
