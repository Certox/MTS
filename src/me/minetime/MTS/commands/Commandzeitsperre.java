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
import org.bukkit.entity.Player;

public class Commandzeitsperre extends CommandMT {

    @Override
    public void run(CommandSender sender, String commandLabel, String[] args) throws Exception {
	if (!(args.length > 2)) {
	    throw new VerwendungsException("/zeitsperre [name] [zeit in stunden] [grund]");
	}

	Date myDate = new Date();
	SimpleDateFormat date_now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	date_now.setTimeZone(TimeZone.getDefault());
	date_now.format(myDate);
	Calendar gc = new GregorianCalendar();
	gc.setTime(myDate);
	gc.add(Calendar.HOUR, Integer.parseInt(args[1]));
	Date d2 = gc.getTime();

	String ip = "";
	for (Player p : Bukkit.getServer().getOnlinePlayers()) {
	    if (p.getName().equals(args[0])) {
		ip = p.getAddress().getAddress().toString();
		ip = ip.substring(1);
	    }
	}

	StringBuilder sb = new StringBuilder();
	for (int i = 2; i < args.length; i++) {
	    if (i != 0) {
		sb.append(' ');
		sb.append(args[i]);
	    }
	}
	String grund = sb.toString();

	String banner = sender.getName();
	MySQL.Update("DELETE FROM `mt_timebans` WHERE name='" + args[0] + "' OR ip='" + ip + "';");
	MySQL.Update("INSERT INTO `mt_timebans` (name, ip, time, reason, `from`) VALUES ('" + args[0] + "', '" + ip + "', '"
		+ date_now.format(d2) + "', '" + grund + "', '" + banner + "');");

	for (Player p : Bukkit.getServer().getOnlinePlayers()) {
	    if (p.getName().equals(args[0])) {
		MTSData.authToLogout.add(args[0]);
		Bukkit.getServer().getPlayer(args[0]).kickPlayer(msg("zeitsperreKickMessage", grund));
	    }
	}

	Bukkit.broadcastMessage(ChatColor.BLUE + "Der Spieler " + ChatColor.GRAY + args[0] + ChatColor.BLUE + " wurde für "
		+ args[1] + " Stunden gebannt. Grund:" + ChatColor.AQUA + grund);

    }
}
