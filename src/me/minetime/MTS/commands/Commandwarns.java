package me.minetime.MTS.commands;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import me.minetime.MTS.MySQL;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;

import org.bukkit.ChatColor;

public class Commandwarns extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {
	boolean zeitsperre = false;
	String zeitsperreDateTime = "";
	boolean sperre = false;
	int warnungen = 0;
	boolean gsperre = false;
	String p = user.getName();

	if (args.length != 0) {
	    p = args[0];
	}

	try {
	    ResultSet rs = MySQL.Query("SELECT `id` FROM `mt_warns` WHERE name='" + p + "';");

	    while (rs.next()) {
		warnungen++;
	    }

	    rs.close();

	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	try {
	    ResultSet rs = MySQL.Query("SELECT time, reason FROM `mt_timebans` WHERE name='" + p + "';");

	    while (rs.next()) {
		// Zeit aus Datenbank in Zeitstempel umwandeln
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = df.parse(rs.getString(1));
		Calendar gc = new GregorianCalendar();
		gc.setTime(d);
		Date d2 = gc.getTime();

		// Jetzige Zeit definieren
		Date myDate = new Date();
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df2.setTimeZone(TimeZone.getDefault());
		df2.format(myDate);
		Calendar gc2 = new GregorianCalendar();
		gc2.setTime(myDate);
		Date now = gc2.getTime();

		// Falls Jetzt hinter dem Datum aus der DB liegt
		if (now.after(d2)) {
		} else {
		    // Zeitsperre auf True setzen
		    zeitsperre = true;
		    zeitsperreDateTime = rs.getString(1);
		}

	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	try {
	    ResultSet rs = MySQL.Query("SELECT `id` FROM `mt_bans` WHERE name='" + p + "';");

	    while (rs.next()) {
		sperre = true;
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	try {
	    ResultSet rs = MySQL.Query("SELECT `id` FROM `mt_globalbans` WHERE name='" + p + "';");

	    while (rs.next()) {
		gsperre = true;
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	String sZeitsperre = "Nein";
	String sSperre = "Nein";
	String sDateTime = "";
	String sGSperre = "Nein";

	if (zeitsperre == true) {
	    sZeitsperre = "Ja";
	    sDateTime = "(bis " + zeitsperreDateTime + ")";
	}
	if (sperre == true) {
	    sSperre = "Ja";
	}
	if (gsperre == true) {
	    sGSperre = "Ja";
	}

	user.getPlayer().sendMessage(ChatColor.BLUE + "Warnung/Ban Status des Spielers: " + ChatColor.GRAY + p);
	user.getPlayer().sendMessage(
		ChatColor.GRAY + "Warnungen: " + ChatColor.AQUA + warnungen + ChatColor.GRAY + " Perm-Bann: "
			+ ChatColor.AQUA + sSperre + ChatColor.GRAY + " Zeit-Bann: " + ChatColor.AQUA + sZeitsperre
			+ sDateTime + ChatColor.GRAY + " Global-Bann: " + ChatColor.AQUA + sGSperre);

	int zahl = 1;
	try {
	    ResultSet rs = MySQL.Query("SELECT `reason`, `from` FROM `mt_warns` WHERE name='" + p
		    + "' ORDER BY id ASC LIMIT 10;");

	    while (rs.next()) {
		user.getPlayer().sendMessage(
			ChatColor.GRAY + String.valueOf(zahl) + ChatColor.BLUE + " | Grund: " + ChatColor.GRAY
				+ rs.getString(1) + ChatColor.BLUE + "  von " + ChatColor.GRAY + rs.getString(2));
		zahl++;
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}
    }

}
