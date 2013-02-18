package me.minetime.MTS.commands;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class Commandxray extends CommandMT {

    @Override
    public void run(CommandSender sender, String commandLabel, String[] args) throws Exception {
	if (args.length == 0) {
	    throw new VerwendungsException("/xray [name]");
	}

	// Jetzige Zeit definieren
	Date myDate = new Date();
	SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	df2.setTimeZone(TimeZone.getDefault());
	df2.format(myDate);
	Calendar gc2 = new GregorianCalendar();
	gc2.setTime(myDate);
	Date now = gc2.getTime();

	Bukkit.dispatchCommand(sender,
		"gsperre " + args[0] + " Detected Hackclient (running Tool: X-Ray) [" + df2.format(now) + " Uhr]");

    }
}
