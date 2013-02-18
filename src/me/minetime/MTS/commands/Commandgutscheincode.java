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

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Commandgutscheincode extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {
	Player player = user.getPlayer();

	if (player == null) { // Nur verwendbar, wenn Konsole Befehl ausführt
	    // Gutscheincode definiren
	    String code = args[0];
	    String playername = args[1];
	    boolean isAvailableCode = false;
	    int giftID = 0;

	    // Prüfen ob Code existiert und Available ist, wenn existiert giftID
	    // ablesen
	    try {
		ResultSet rs = MySQL.Query("SELECT * FROM `gutschein` WHERE `code`='" + code.toLowerCase()
			+ "' AND `available`='1';");
		while (rs.next()) {
		    isAvailableCode = true;
		    giftID = rs.getInt(5);
		}
	    } catch (Exception err) {
		System.out.println("MTS-Fehler: " + err);
	    }

	    if (isAvailableCode == false) {
	    } else {
		// Jetzige Zeit definieren
		Date myDate = new Date();
		SimpleDateFormat date_now = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		date_now.setTimeZone(TimeZone.getDefault());
		date_now.format(myDate);
		Calendar gc = new GregorianCalendar();
		gc.setTime(myDate);
		Date d2 = gc.getTime();

		// Code deaktivieren

		MySQL.Update("UPDATE `gutschein` SET `available`='0' WHERE `code`='" + code.toLowerCase() + "';");
		MySQL.Update("UPDATE `gutschein` SET `player`='" + playername + "' WHERE `code`='" + code.toLowerCase()
			+ "';");
		MySQL.Update("UPDATE `gutschein` SET `time`='" + date_now.format(d2) + "' WHERE `code`='"
			+ code.toLowerCase() + "';");

		// Preis ausgeben, jeden Befehl in while schleife ausführen
		// -> WICHTIG IN DEN BEFEHLEN WIRD "-name-" DURCH DEN
		// SPIELERNAMEN ERSETZT!
		try {
		    ResultSet rs = MySQL.Query("SELECT * FROM `giftid` WHERE `giftid`='" + giftID + "';");
		    while (rs.next()) {
			// Befehl auslesen
			String befehl = rs.getString(3);

			// -name- durch den Spielername ersetzen
			String neuerbefehl = befehl.replace("-name-", playername);

			// Befehl ausführen
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), neuerbefehl);
		    }
		} catch (Exception err) {
		    System.out.println("MTS-Fehler: " + err);
		}

		if (Bukkit.getServer().getPlayer(playername).isOnline()) {
		    Bukkit.getServer().getPlayer(playername).sendMessage(msg("gutscheincodeErfolg"));
		}
	    }

	    // ENDE
	} else {
	    throw new Exception(msg("gutscheincodeVerwendung"));
	}
    }

}
