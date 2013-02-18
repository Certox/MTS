package me.minetime.MTS.commands;

import static me.minetime.MTS.clazz.Message.msg;

import java.sql.ResultSet;

import me.minetime.MTS.MySQL;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.command.CommandSender;

public class Commanddelwarn extends CommandMT {

    @Override
    public void run(CommandSender sender, String commandLabel, String[] args) throws Exception {
	int anzahl = 0;

	if (!(args.length == 2)) {
	    throw new VerwendungsException("/delwarn [name] [anzahl]");
	}

	try {
	    anzahl = Integer.parseInt(args[1]);
	} catch (Exception ex) {
	    sender.sendMessage(msg("delwarnFail"));
	}

	int warnungen = 0;

	try {
	    ResultSet rs = MySQL.Query("SELECT * FROM `mt_warns` WHERE name='" + args[0] + "';");

	    while (rs.next()) {
		warnungen++;
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	if (warnungen < anzahl) {
	    sender.sendMessage(msg("delwarnTooMuch", anzahl, warnungen));
	}

	MySQL.Update("DELETE FROM `mt_warns` WHERE name='" + args[0] + "' LIMIT " + anzahl + ";");

	sender.sendMessage(msg("delwarnSuccess", args[0], anzahl));

    }

}
