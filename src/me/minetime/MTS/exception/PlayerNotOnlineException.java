package me.minetime.MTS.exception;

import org.bukkit.ChatColor;

public class PlayerNotOnlineException extends Exception {

    private static final long serialVersionUID = 2089589855029204796L;

    public PlayerNotOnlineException(String player) {
	super(ChatColor.RED + "Der Spieler " + ChatColor.YELLOW + player + ChatColor.RED + " ist nicht online.");
    }
}
