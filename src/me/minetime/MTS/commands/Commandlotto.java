package me.minetime.MTS.commands;

import static me.minetime.MTS.clazz.Message.msg;

import java.sql.ResultSet;

import me.minetime.MTS.MTSData;
import me.minetime.MTS.MySQL;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Commandlotto extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) {
	if (args.length == 0 || args[0].equalsIgnoreCase("hilfe")) {
	    Player p = user.getPlayer();
	    p.sendMessage(ChatColor.AQUA + "---------- Lotto Befehle ----------");
	    p.sendMessage(ChatColor.GOLD + "Lottoschein kaufen: " + ChatColor.DARK_AQUA + "/lotto kaufen");
	    p.sendMessage(ChatColor.GOLD + "Den Kauf best\u00e4tigen: " + ChatColor.DARK_AQUA + "/lotto best\u00e4tigen");
	}
	if (args[0].equalsIgnoreCase("kaufen")) {
	    boolean machtMit = false;
	    try {
		ResultSet rs = MySQL.Query("SELECT `name` FROM `lotto` WHERE `name`='" + user.getName() + "';");

		while (rs.next()) {
		    machtMit = true;
		}

		rs.close();
	    } catch (Exception err) {
		System.out.println("MTS-Fehler: " + err);
	    }
	    if (machtMit) {
		user.getPlayer().sendMessage(msg("lottoHasTicket"));
	    } else {

		if (MTSData.requestLotto.contains(user.getName())) {
		    user.getPlayer().sendMessage(msg("lottoAccept"));
		} else {
		    MTSData.requestLotto.add(user.getName());
		    user.getPlayer().sendMessage(msg("lottoAccept"));
		}

	    }
	}
	if (args[0].equalsIgnoreCase("best\u00e4tigen")) {
	    if (MTSData.requestLotto.contains(user.getName())) {
		if (!(user.getMoney() > 1000)) {
		    user.getPlayer().sendMessage(msg("lottoNotEnoughMoney"));
		} else {
		    user.removeMoney(1000D);
		    MySQL.Update("INSERT INTO `lotto` (name, money) VALUES ('" + user.getName() + "', '1000')");
		    user.getPlayer().sendMessage(msg("lottoSuccess"));
		}
	    } else {
		user.getPlayer().sendMessage(msg("lottoFail1"));
	    }
	}

    }

}
