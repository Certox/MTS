package me.minetime.MTS.commands;

import static me.minetime.MTS.clazz.Message.msg;

import java.sql.ResultSet;

import me.minetime.MTS.MySQL;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;

public class Commandbewerbung extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) {

	String name = "";

	try {
	    ResultSet rs = MySQL.Query("SELECT `name` FROM `mt_bewerbungen` WHERE `name`='" + user.getPlayer().getName()
		    + "' AND `activ`='0';");
	    while (rs.next())
		name = rs.getString(1);

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	if (name.equals("")) {
	    user.getPlayer().sendMessage(msg("bewerbungFail"));
	} else {
	    MySQL.Update("UPDATE `mt_bewerbungen` SET `activ`='1' WHERE `name`='" + user.getPlayer().getName()
		    + "' AND `activ`='0';");

	    user.getPlayer().sendMessage(msg("bewerbungSuccess"));
	}
    }

}
