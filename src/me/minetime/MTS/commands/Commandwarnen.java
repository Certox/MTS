package me.minetime.MTS.commands;

import static me.minetime.MTS.clazz.Message.msg;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TimeZone;

import me.minetime.MTS.MySQL;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.clazz.User;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Commandwarnen extends CommandMT {

    public long diff = 0;
    static HashMap<String, Date> hm = new HashMap<String, Date>();

    @Override
    public void run(final CommandSender sender, final String commandLabel, final String[] args) throws Exception {
	boolean go = true;

	if (args.length <= 1) {
	    go = false;
	    throw new VerwendungsException("/warnen [name] <anzahl> [grund]");
	}

	Boolean finished = true;
	finished = timerfinished(args);
	if (!finished) {

	    throw new Exception(msg("warnenWasWarned", args[0], diff));
	}

	if (go) {
	    int a = 1;
	    int b = 1;

	    try {
		a = Integer.parseInt(args[1]);
	    } catch (NumberFormatException e) {
		a = 1;
	    }

	    String anzahl = "";

	    if (a != 1) {
		if (a >= 2 && a <= 10) {
		    b = 2;
		    anzahl = ChatColor.YELLOW + Integer.toString(a) + " Mal " + ChatColor.BLUE;
		}
	    }

	    StringBuilder sb = new StringBuilder();
	    for (int i = b; i < args.length; i++) {
		if (i != 0)
		    sb.append(' ');
		sb.append(args[i]);
	    }
	    String grund = sb.toString();

	    String warner = sender.getName();

	    for (int i = 0; i < a; i++) {
		MySQL.Update("INSERT INTO `mt_warns` (name, reason, `from`) VALUES ('" + args[0] + "', '" + grund + "', '"
			+ warner + "');");
	    }

	    Bukkit.broadcastMessage(msg("warnenBroadcast", args[0], anzahl, grund));
	    new User(args[0]).checkWarns();
	}
    }

    private boolean timerfinished(String[] args) {

	if (!hm.containsKey(args[0])) {
	    Date myDate = new Date();
	    SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    df2.setTimeZone(TimeZone.getDefault());
	    df2.format(myDate);

	    Calendar gc2 = new GregorianCalendar();
	    gc2.setTime(myDate);
	    gc2.add(Calendar.SECOND, 10);
	    Date now = gc2.getTime();

	    hm.put(args[0], now);
	    return true;
	} else

	{
	    Date myDate = new Date();
	    SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    df2.setTimeZone(TimeZone.getDefault());
	    df2.format(myDate);

	    Calendar gc2 = new GregorianCalendar();
	    gc2.setTime(myDate);

	    Date now = gc2.getTime();

	    Date last = new Date();

	    for (Entry<String, Date> e : hm.entrySet()) {
		if (e.getKey().equals(args[0])) {
		    last = e.getValue();
		    break;
		}

	    }

	    long timeSpaeter = last.getTime();
	    long timeFrueher = now.getTime();
	    diff = (timeSpaeter - timeFrueher) / 1000;

	    if (diff > 10 || diff <= 0) {

		gc2.setTime(myDate);
		gc2.add(Calendar.SECOND, 10);
		Date jetzt = gc2.getTime();

		hm.remove(args[0]);
		hm.put(args[0], jetzt);
		return true;
	    } else {
		return false;
	    }
	}
    }
}
