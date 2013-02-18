package me.minetime.MTS.commands;

import static me.minetime.MTS.clazz.Message.msg;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import me.minetime.MTS.MTSData;
import me.minetime.MTS.MySQL;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Commandsperre extends CommandMT {

    @Override
    public void run(CommandSender sender, String commandLabel, String[] args) throws Exception {
	if (!(args.length > 1)) {
	    throw new VerwendungsException("/sperre [name] [grund]");
	}

	String ip = "";

	if (Bukkit.getServer().getPlayer(args[0]) != null) {
	    ip = Bukkit.getServer().getPlayer(args[0]).getAddress().getAddress().toString();
	    ip = ip.substring(1);

	}

	StringBuilder sb = new StringBuilder();
	for (int i = 1; i < args.length; i++) {
	    if (i != 0)
		sb.append(' ');
	    sb.append(args[i]);
	}
	String grund = sb.toString();

	String banner = sender.getName();

	// Jetzige Zeit definieren
	Date myDate = new Date();
	SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	df2.setTimeZone(TimeZone.getDefault());
	df2.format(myDate);
	Calendar gc2 = new GregorianCalendar();
	gc2.setTime(myDate);
	Date now = gc2.getTime();

	MySQL.Update("DELETE FROM `mt_bans` WHERE name='" + args[0] + "' OR ip='" + ip + "';");
	MySQL.Update("INSERT INTO `mt_bans` (name, datetime, ip, reason, `from`) VALUES ('" + args[0] + "', '"
		+ df2.format(now) + "', '" + ip + "', '" + grund + "', '" + banner + "');");

	if (Bukkit.getServer().getPlayer(args[0]) != null) {
	    MTSData.authToLogout.add(args[0]);
	    Bukkit.getServer().getPlayer(args[0]).kickPlayer(msg("sperreKickMessage", grund));

	}

	Bukkit.broadcastMessage(ChatColor.DARK_RED + "Der Spieler " + ChatColor.BLUE + args[0] + ChatColor.DARK_RED
		+ " wurde permanent gebannt. Grund:" + ChatColor.AQUA + grund);
    }
}
