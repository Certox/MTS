package me.minetime.MTS.commands;

import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Commandvote extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) {
	Player player = user.getPlayer();

	player.sendMessage(ChatColor.AQUA + "[]----- " + ChatColor.GOLD + "Vote" + ChatColor.AQUA + " -----[]");
	player.sendMessage(ChatColor.GOLD + "1. " + ChatColor.AQUA + "Klicke auf " + ChatColor.RED
		+ "http://www.minetime.me/vote/?n=" + player.getName() + " " + ChatColor.AQUA + "!");
	player.sendMessage(ChatColor.GOLD + "2. " + ChatColor.AQUA
		+ "Lass Minecraft den Link in deinem Browser \u00f6ffnen.");
	player.sendMessage(ChatColor.GOLD + "3. " + ChatColor.AQUA + "Gebe den Code ein.");
	player.sendMessage(ChatColor.GOLD + "4. " + ChatColor.AQUA + "Klicke auf bewerten.");
	player.sendMessage(ChatColor.GOLD + "5. " + ChatColor.AQUA + "Freue dich auf die Belohnung.");
    }

}
