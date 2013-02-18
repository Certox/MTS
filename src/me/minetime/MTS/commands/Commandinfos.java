package me.minetime.MTS.commands;

import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Commandinfos extends CommandMT {

    public void run(final IUser user, final String commandLabel, final String[] args) {

	Player p = user.getPlayer();
	/* Language pack direkt hier */
	p.sendMessage(ChatColor.AQUA + "[]---------------- " + ChatColor.GOLD + "MineTime Infos" + ChatColor.AQUA
		+ " ----------------[]");
	p.sendMessage(ChatColor.GOLD + "Besucht doch mal unsere Homepage: " + ChatColor.RED + "minetime.me");
	p.sendMessage(ChatColor.GOLD + "Schaut euch im Forum um: " + ChatColor.RED + "minetime.me/forum");
	p.sendMessage(ChatColor.GOLD + "Reden im TeamSpeak?: " + ChatColor.RED + "ts.minetime.eu");
	p.sendMessage(ChatColor.GOLD + "Abonniert unseren YouTube-Kanal: " + ChatColor.RED + "youtube.com/MineTimeDETV");
	p.sendMessage(ChatColor.GOLD + "Immer aktuell mit Twitter: " + ChatColor.RED + "twitter.com/MineTimeDotMe");
	p.sendMessage(ChatColor.GOLD + "Immernoch Fragen? " + ChatColor.RED + "ask.fm/McSender");
    }

}
