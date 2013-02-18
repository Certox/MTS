package me.minetime.MTS.commands;

import org.bukkit.ChatColor;

import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.clazz.NotMove;

public class Commandcw extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {

	if (!user.isInClan())
	    throw new Exception("Du bist in keinem Clan!");

	NotMove nm = new NotMove(user, user.getClan().getClanWarp(), "deinem " + ChatColor.YELLOW + "Clan Warp"
		+ ChatColor.RESET);
	nm.go(false);
    }

}
