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
import me.minetime.MTS.clazz.User;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commandgsperre extends CommandMT {

    @Override
    public void run(final CommandSender sender, final String commandLabel, final String[] args) throws Exception {

	if (!(args.length > 1))
	    throw new VerwendungsException("/gsperre [name]  [grund]");

	String ip = "";
	for (Player p : Bukkit.getServer().getOnlinePlayers()) {
	    if (p.getName().equals(args[0])) {
		ip = p.getAddress().getAddress().toString();
		ip = ip.substring(1);
	    }
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
	MySQL.Update("DELETE FROM `mt_globalbans` WHERE name='" + args[0] + "' OR ip='" + ip + "';");
	MySQL.Update("INSERT INTO `mt_globalbans` (name, datetime, ip, reason, `from`) VALUES ('" + args[0] + "', '"
		+ df2.format(now) + "', '" + ip + "', '" + grund + "', '" + banner + "');");

	if (new User(args[0]).isOnline()) {
	    MTSData.authToLogout.add(args[0]);
	    Bukkit.getPlayer(args[0]).kickPlayer(msg("gsperreKickMessage", grund));
	}

	Bukkit.broadcastMessage(msg("gsperreBroadcast", args[0], grund));
    }

}
