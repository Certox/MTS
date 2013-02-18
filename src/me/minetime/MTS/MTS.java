package me.minetime.MTS;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import me.minetime.MTS.api.ICommandMTHandler;
import me.minetime.MTS.api.IConfig;
import me.minetime.MTS.clazz.CommandMTHandler;
import me.minetime.MTS.clazz.Config;
import me.minetime.MTS.clazz.Message;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class MTS extends JavaPlugin {

    // //////////////////////////////////////////
    // Listeners deklarieren
    public final MTSPlayerListener playerListener = new MTSPlayerListener();
    public final MTSEntityListener entityListener = new MTSEntityListener();
    public final MTSBlockListener blockListener = new MTSBlockListener();

    // MOAR
    public static List<String> spy = new ArrayList<String>();

    // //////////////////////////////////////////
    // MySQL-Variablen deklarieren
    public static String MySQL_host = "";
    public static String MySQL_user = "";
    public static String MySQL_pass = "";
    public static String MySQL_db = "";

    // ///////////////////////////////

    private transient ICommandMTHandler commandHandler;

    
    // /pushtest

    @Override
    public void onDisable() {

	MySQL.Update("DELETE FROM `mt_onlineusers`;");

	MySQL.close();
	Bukkit.broadcastMessage(ChatColor.GOLD + "[MTS] " + ChatColor.AQUA + "MTS deaktiviert!");
	
    }

    @Override
    public void onEnable() {
	new Message();
	/* CommandHandler initialisieren */
	commandHandler = new CommandMTHandler(this.getClassLoader(), "me.minetime.MTS.commands.Command");

	/* Alle 5 Minuten save-all ausfuehren */
	Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
	    public void run() {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "save-all");
		MTSData.antispam.clear();
	    }
	}, 40L, 6000L);
	

	/* Listener aktivieren:EntityListener, PlayerListener */
	getServer().getPluginManager().registerEvents(this.playerListener, this);
	getServer().getPluginManager().registerEvents(this.entityListener, this);
	getServer().getPluginManager().registerEvents(this.blockListener, this);

	Bukkit.broadcastMessage(ChatColor.GOLD + "[MTS] " + ChatColor.AQUA + "MTS aktiviert!");

	// //////////////////////////////////////////
	// MySQL-Daten einlesen
	IConfig config = new Config();
	MySQL_host = config.getString("MySQL.MineTime.Host");
	MySQL_user = config.getString("MySQL.MineTime.User");
	MySQL_pass = config.getString("MySQL.MineTime.Pass");
	MySQL_db = config.getString("MySQL.MineTime.DB");

	// Mysql-connect
	MySQL.connect();

	/* LABYRINTH-EVENT koordinaten */
	//-> Wegen mapreset veraltet
	
	//Add adventsteile
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 427, 69, 280), 1);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 429, 69, 280), 2);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 431, 69, 280), 3);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 433, 69, 280), 4);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 435, 69, 280), 5);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 428, 70, 280), 6);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 428, 70, 280), 7);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 428, 70, 280), 8);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 428, 70, 280), 9);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 429, 71, 280), 10);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 431, 71, 280), 11);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 433, 71, 280), 12);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 430, 72, 280), 13);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 432, 72, 280), 14);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 428, 73, 280), 15);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 431, 73, 280), 16);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 434, 73, 280), 17);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 430, 74, 280), 18);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 432, 74, 280), 19);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 431, 75, 280), 20);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 429, 76, 280), 21);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 431, 76, 280), 22);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 433, 76, 280), 23);
	MTSData.adventsSchilder.put(new Location(Bukkit.getWorld("world"), 431, 78, 280), 24);
	
	
	

	
	// Read BroadcastMessages from DB
	try {
	    ResultSet rs = MySQL.Query("SELECT `value` FROM `mt_res` WHERE `type`='broadcastMessage';");

	    int i = 0;
	    while (rs.next()) {
		i++;

		MTSData.broadcastMessages.put(i, rs.getString(1));
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	// Scheduler starten (3min)
	Bukkit.getServer().getScheduler()
		.scheduleAsyncRepeatingTask(Bukkit.getPluginManager().getPlugin("MTS"), new Runnable() {

		    int i = 0;

		    public void run() {
			i++;

			if (i > MTSData.broadcastMessages.size()) {
			    i = 1;
			}
			Bukkit.broadcastMessage(MTSData.convertColorcodes(MTSData.broadcastMessages.get(i)));
		    }
		}, 20 * 60 * 3L, 20 * 60 * 3L);
    }

    // Reload-Funktion
    public static void reload() {

	IConfig config = new Config();
	MySQL_host = config.getString("MySQL.MineTime.Host");
	MySQL_user = config.getString("MySQL.MineTime.User");
	MySQL_pass = config.getString("MySQL.MineTime.Pass");
	MySQL_db = config.getString("MySQL.MineTime.DB");

	MySQL.close();
	MySQL.connect();

	MTSData.broadcastMessages.clear();
	try {
	    ResultSet rs = MySQL.Query("SELECT `value` FROM `mt_res` WHERE `type`='broadcastMessage';");

	    int i = 0;
	    while (rs.next()) {
		i++;

		MTSData.broadcastMessages.put(i, rs.getString(1));
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

	return commandHandler.handleCommand(sender, cmd, label, args);

    }

}
