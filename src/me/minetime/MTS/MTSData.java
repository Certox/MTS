package me.minetime.MTS;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.minetime.MTS.clazz.User;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet20NamedEntitySpawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class MTSData {

    public static List<String> authToLogout = new ArrayList<String>();
    public static boolean globalmute = false;
    public static boolean commandmute = false;
    public static HashMap<String, String> antispam = new HashMap<String, String>();
    public static List<String> requestFriede = new ArrayList<String>();
    public static List<String> marriedChatTags = new ArrayList<String>();
    public static HashMap<String, List<String>> TBL_friede = new HashMap<String, List<String>>();
    public static HashMap<String, String> TBL_neuespieler = new HashMap<String, String>(); // NeueSpieler,
											   // bzw
											   // neuspielerschutz
    public static HashMap<String, Integer> requestClaneinladungen = new HashMap<String, Integer>();
    public static List<String> requestClanverlassen = new ArrayList<String>();
    public static HashMap<String, Integer> requestAllianz = new HashMap<String, Integer>();
    public static HashMap<String, String> requestAntrag = new HashMap<String, String>();
    public static HashMap<String, String> chatColors = new HashMap<String, String>();
    public static List<Location> foundblocks = new ArrayList<Location>();
    public static List<String> requestLotto = new ArrayList<String>();
    public static HashMap<String, String> chatrooms = new HashMap<String, String>();
    public static HashMap<String, Integer> lastLogin = new HashMap<String, Integer>();
    public static HashMap<String, Location> customRespawn = new HashMap<String, Location>();
    public static List<String> knifer = new ArrayList<String>();
    public static HashMap<String, PlayerInventory> knife = new HashMap<String, PlayerInventory>();
    public static HashMap<Location, Integer> adventsSchilder = new HashMap<Location, Integer>();
    
    public static HashMap<String, String> support = new HashMap<String, String>();

    public static HashMap<Integer, String> broadcastMessages = new HashMap<Integer, String>();

    
    public static boolean pvp = true;
    public static boolean kfenabled = true;
    public static boolean kfrunning = false;

    public static String convertConsoleColorcodes(String message) {
	String msg = message;

	msg = msg.replace(ChatColor.BLACK + "", "\u001B[30m");
	msg = msg.replace(ChatColor.DARK_BLUE + "", "\u001B[34m");
	msg = msg.replace(ChatColor.DARK_GREEN + "", "\u001B[32m");
	msg = msg.replace(ChatColor.DARK_AQUA + "", "\u001B[36m");
	msg = msg.replace(ChatColor.DARK_PURPLE + "", "\u001B[35m");
	msg = msg.replace(ChatColor.GOLD + "", "\u001B[33m");
	msg = msg.replace(ChatColor.GRAY + "", "\u001B[37m");
	msg = msg.replace(ChatColor.DARK_GRAY + "", "\u001B[37m");
	msg = msg.replace(ChatColor.BLUE + "", "\u001B[34m");
	msg = msg.replace(ChatColor.GREEN + "", "\u001B[32m");
	msg = msg.replace(ChatColor.AQUA + "", "\u001B[36m");
	msg = msg.replace(ChatColor.YELLOW + "", "\u001B[33m");
	msg = msg.replace(ChatColor.WHITE + "", "\u001B[37m");
	msg = msg.replace(ChatColor.DARK_RED + "", "\u001B[31m");
	msg = msg.replace(ChatColor.RED + "", "\u001B[31m");
	msg = msg.replace(ChatColor.LIGHT_PURPLE + "", "\u001B[35m");

	msg = msg.replace(ChatColor.BOLD + "", "");
	msg = msg.replace(ChatColor.ITALIC + "", "");
	msg = msg.replace(ChatColor.UNDERLINE + "", "");
	msg = msg.replace(ChatColor.RESET + "", "");
	msg = msg.replace(ChatColor.MAGIC + "", "");

	msg = msg + "\u001B[0m";

	return msg;
    }

    public static String convertColorcodes(String message) {
	String msg = message;

	msg = msg.replace("#0", ChatColor.BLACK + "");
	msg = msg.replace("#1", ChatColor.DARK_BLUE + "");
	msg = msg.replace("#2", ChatColor.DARK_GREEN + "");
	msg = msg.replace("#3", ChatColor.DARK_AQUA + "");
	msg = msg.replace("#5", ChatColor.DARK_PURPLE + "");
	msg = msg.replace("#6", ChatColor.GOLD + "");
	msg = msg.replace("#7", ChatColor.GRAY + "");
	msg = msg.replace("#8", ChatColor.DARK_GRAY + "");
	msg = msg.replace("#9", ChatColor.BLUE + "");
	msg = msg.replace("#a", ChatColor.GREEN + "");
	msg = msg.replace("#b", ChatColor.AQUA + "");
	msg = msg.replace("#e", ChatColor.YELLOW + "");
	msg = msg.replace("#f", ChatColor.WHITE + "");
	msg = msg.replace("#4", ChatColor.DARK_RED + "");
	msg = msg.replace("#c", ChatColor.RED + "");
	msg = msg.replace("#d", ChatColor.LIGHT_PURPLE + "");
	msg = msg.replace("#e", ChatColor.YELLOW + "");
	msg = msg.replace("#l", ChatColor.BOLD + "");
	msg = msg.replace("#i", ChatColor.ITALIC + "");
	msg = msg.replace("#u", ChatColor.UNDERLINE + "");
	msg = msg.replace("#k", ChatColor.MAGIC + "");
	msg = msg.replace("#r", ChatColor.RESET + "");

	return msg;
    }

    public static void changePlayerName(final Player player, final String newName) {
	final String oldPlayerName = player.getName();

	int interval = 40;

	Bukkit.getScheduler().scheduleAsyncRepeatingTask(new MTS(), new Runnable() {
	    public void run() {
		if (new User(player.getName()).isOnline()) {
		    EntityPlayer ePlayer = ((CraftPlayer) player).getHandle();
		    ePlayer.name = newName;
		    try {
			for (Player p : Bukkit.getOnlinePlayers()) {
			    if (p != player) {
				((CraftPlayer) p).getHandle().netServerHandler.sendPacket(new Packet20NamedEntitySpawn(
					ePlayer));
			    }
			}
		    } catch (Exception ignored) {
		    }
		    ;
		    ePlayer.name = oldPlayerName;
		}
	    }
	}, 100L, interval * 20L);
    }

    public static void sendChatroomMessage(String chatroom, String msg) {
	if (chatroom.equalsIgnoreCase("GLOBAL")) {
	    List<Player> globals = new ArrayList<Player>();

	    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
		globals.add(p);
	    }

	    for (String s : chatrooms.keySet()) {
		globals.remove(new User(s).getPlayer());
	    }

	    for (Player p : globals) {
		p.sendMessage(msg);
	    }
	    System.out.println(convertConsoleColorcodes(ChatColor.GRAY + "~GLOBAL-> " + ChatColor.RESET + msg));
	} else {
	    for (String s : chatrooms.keySet()) {
		if (chatrooms.get(s).equalsIgnoreCase(chatroom)) {
		    new User(s).getPlayer().sendMessage(msg);
		}
	    }
	    System.out.println(convertConsoleColorcodes(ChatColor.GRAY + "~" + chatroom + "-> " + ChatColor.RESET + msg));
	}
    }

    public static Integer getClanLevelMaxMember(Integer clanlevel) {
	Integer i = 0;

	switch (clanlevel) {
	case 1:
	    i = 10;
	    break;
	case 2:
	    i = 12;
	    break;
	case 3:
	    i = 15;
	    break;
	case 4:
	    i = 20;
	    break;
	case 5:
	    i = 20;
	    break;
	case 6:
	    i = 20;
	    break;
	case 7:
	    i = 22;
	    break;
	case 8:
	    i = 22;
	    break;
	case 9:
	    i = 22;
	    break;
	case 10:
	    i = 25;
	    break;
	default:
	    return 10;

	}

	return i;

    }

    public static Integer getClanlevelAllianzen(Integer clanlevel) {
	int allis = 0;
	switch (clanlevel) {
	case 1:
	    allis = 0;
	    break;
	case 2:
	    allis = 0;
	    break;
	case 3:
	    allis = 1;
	    break;
	case 4:
	    allis = 1;
	    break;
	case 5:
	    allis = 1;
	    break;
	case 6:
	    allis = 2;
	    break;
	case 7:
	    allis = 2;
	    break;
	case 8:
	    allis = 2;
	    break;
	case 9:
	    allis = 3;
	    break;
	case 10:
	    allis = 3;
	    break;
	default:
	    allis = 0;
	    break;
	}
	return allis;
    }

    public static Double getClanlevelMoney(Integer clanlevel) {
	double money = 0.0;
	switch (clanlevel) {
	case 2:
	    money = 60000.0;
	    break;
	case 3:
	    money = 90000.0;
	    break;
	case 4:
	    money = 130000.0;
	    break;
	case 5:
	    money = 175000.0;
	    break;
	case 6:
	    money = 250000.0;
	    break;
	case 7:
	    money = 500000.0;
	    break;
	case 8:
	    money = 750000.0;
	    break;
	case 9:
	    money = 1000000.0;
	    break;
	case 10:
	    money = 3000000.0;
	    break;
	default:
	    money = 60000.0;
	    break;
	}
	return money;
    }

    public static Integer getClanlevelKills(Integer clanlevel) {
	int allis = 0;
	switch (clanlevel) {
	case 2:
	    allis = 600;
	    break;
	case 3:
	    allis = 1200;
	    break;
	case 4:
	    allis = 3500;
	    break;
	case 5:
	    allis = 10000;
	    break;
	case 6:
	    allis = 28000;
	    break;
	case 7:
	    allis = 35000;
	    break;
	case 8:
	    allis = 50000;
	    break;
	case 9:
	    allis = 75000;
	    break;
	case 10:
	    allis = 100000;
	    break;
	default:
	    allis = 600;
	    break;
	}
	return allis;
    }

    public static boolean clannameExist(String clanname) {
	boolean b = false;

	try {
	    ResultSet rs = MySQL.Query("SELECT `id` FROM `mt_clans` WHERE `name`='" + clanname + "';");

	    while (rs.next()) {
		b = true;
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	return b;
    }

    public static boolean clantagExist(String clantag) {
	boolean b = false;

	try {
	    ResultSet rs = MySQL.Query("SELECT `id` FROM `mt_clans` WHERE `short`='" + clantag + "';");

	    while (rs.next()) {
		b = true;
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	return b;
    }

}
