package me.minetime.MTS.commands;

import static me.minetime.MTS.clazz.Message.msg;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import me.minetime.MTS.MySQL;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class Commandactivate extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws ParseException {
	boolean stat = false;
	String u = "";
	String p = "";
	String e = "";
	String da = "";

	try {
	    ResultSet rs = MySQL.Query("SELECT `user`, `pass`, `email`, `datetime` FROM `mt_hp_users_adds` WHERE user='"
		    + user.getPlayer().getName() + "';");

	    while (rs.next()) {
		stat = true;
		u = rs.getString(1);
		p = rs.getString(2);
		e = rs.getString(3);
		da = rs.getString(4);
	    }
	}

	catch (Exception err) {
	    System.out.println("MTS-Activate: " + err);
	}

	if (stat == false) {
	    user.getPlayer().sendMessage(msg("activateFail"));
	} else {

	    // Zeit aus Datenbank in Zeitstempel umwandeln
	    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	    Date d = df.parse(da);
	    Calendar gc = new GregorianCalendar();
	    gc.setTime(d);
	    gc.add(Calendar.HOUR, 24);
	    Date d2 = gc.getTime();

	    // Jetzige Zeit definieren
	    Date myDate = new Date();
	    SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    df2.setTimeZone(TimeZone.getDefault());
	    df2.format(myDate);
	    Calendar gc2 = new GregorianCalendar();
	    gc2.setTime(myDate);
	    Date now = gc2.getTime();

	    if (now.after(d2)) {
		MySQL.Update("DELETE FROM mt_hp_users_adds WHERE `user`='" + user.getPlayer().getName() + "';");
		user.getPlayer()
			.sendMessage(
				ChatColor.RED
					+ "Fehler: Die Registration ist bereits \u00E4lter als 1 Tag, bitte registriere dich auf der Homepage erneut.");
	    } else {
		MySQL.Update("INSERT INTO `mt_hp_users` (user, pass, email, op, ban) VALUES ('" + u + "', '" + p + "', '"
			+ e + "', '0', '0');");
		MySQL.Update("DELETE FROM mt_hp_users_adds WHERE `user`='" + user.getPlayer().getName() + "';");
		user.getPlayer().sendMessage(msg("activateSuccess"));
		user.getPlayer().getInventory().addItem(new ItemStack(264, 1));
		user.getPlayer().getInventory().addItem(new ItemStack(265, 10));
	    }
	}

    }

}
