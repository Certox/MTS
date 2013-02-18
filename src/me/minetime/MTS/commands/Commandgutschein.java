package me.minetime.MTS.commands;

import static me.minetime.MTS.clazz.Message.msg;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import me.minetime.MTS.MySQL;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Commandgutschein extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {
	Player player = user.getPlayer();

	// Argumente prüfen
	if (args.length == 0) {

	    throw new VerwendungsException("/gutschein [code]");
	}

	// Gutscheincode argument prüfen
	if (args[0].length() != 12) {

	    throw new Exception(msg("gutscheinLaengeFail"));
	}

	// giftID definieren
	int giftID = 0;

	// Gutschein auf Gültigkeit prüfen
	boolean isAvailable = false;

	try {
	    ResultSet rs = MySQL.Query("SELECT `giftid` FROM `gutschein` WHERE `code`='" + args[0].toLowerCase()
		    + "' AND `available`='1';");
	    while (rs.next()) {
		giftID = rs.getInt(1);
		isAvailable = true;
	    }
	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}
	if (isAvailable == false) {
	    throw new Exception(msg("gutscheinFail"));
	}

	// Gutschein einlösem
	// Jetzige Zeit definieren
	Date myDate = new Date();
	SimpleDateFormat date_now = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	date_now.setTimeZone(TimeZone.getDefault());
	date_now.format(myDate);
	Calendar gc = new GregorianCalendar();
	gc.setTime(myDate);
	Date d2 = gc.getTime();

	// Code deaktivieren
	MySQL.Update("UPDATE `gutschein` SET `available`='0', `player`='" + player.getName() + "', `time`='"
		+ date_now.format(d2) + "' WHERE `code`='" + args[0].toLowerCase() + "';");

	// Preis ausgeben, jeden Befehl in while schleife ausführen
	// -> WICHTIG IN DEN BEFEHLEN WIRD "-name-" DURCH DEN SPIELERNAMEN
	// ERSETZT!
	try {
	    ResultSet rs = MySQL.Query("SELECT `befehl` FROM `giftid` WHERE `giftid`='" + giftID + "';");
	    while (rs.next()) {
		// Befehl auslesen
		String befehl = rs.getString(1);

		// -name- durch den Spielername ersetzen
		String neuerbefehl = befehl.replace("-name-", player.getName());

		// Befehl ausführen
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), neuerbefehl);
	    }

	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	// Ausgabe
	player.sendMessage(ChatColor.GREEN + "Der Gutscheincode wurde eingel\u00F6st.");

    }

}
