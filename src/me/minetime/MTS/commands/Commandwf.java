package me.minetime.MTS.commands;

import me.minetime.MTS.api.IConfig;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.clazz.Config;
import me.minetime.MTS.clazz.NotMove;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Commandwf extends CommandMT {

    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {
	boolean hasPerm = false;
	final PermissionManager pex = PermissionsEx.getPermissionManager();
	Player p = user.getPlayer();
	if (p.isOp() || pex.has(p, "MTS.wf.admin"))
	    hasPerm = true;
	IConfig config = new Config();

	if (hasPerm) {
	    if (args.length == 0 || args[0].equalsIgnoreCase("hilfe") || args[0].equalsIgnoreCase("help")) {
		p.sendMessage(ChatColor.BLUE + "/wf erkl\u00E4rung " + ChatColor.RED
			+ "Erkl\u00E4rung f\u00FCr die Spieler anzeigen");
		p.sendMessage(ChatColor.BLUE + "/wf rules " + ChatColor.RED + "Regeln f\u00FCr die Spieler anzeigen");
		p.sendMessage(ChatColor.BLUE + "/wf on|off " + ChatColor.RED + "Anschalten | Ausschalten");
		p.sendMessage(ChatColor.BLUE + "/wf join " + ChatColor.RED + "Befehl zum teilnehmen.");
		p.sendMessage(ChatColor.BLUE + "/wf setwarp " + ChatColor.RED + "Punkt f\u00FCr /wf join setzen");
		p.sendMessage(ChatColor.BLUE + "/wf ask w|f <Frage> " + ChatColor.RED + "Frage stellen | w=wahr f=falsch");
	    } else if (args[0].equalsIgnoreCase("rules")) {
		Bukkit.getServer().broadcastMessage("");
		Bukkit.getServer().broadcastMessage(
			ChatColor.LIGHT_PURPLE + "[Server] Die Regeln f\u00FCr das Wahr oder Falsch Event sind folgende:");
		Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] 1. Es gibt nur einen Gewinner");
		Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] 2. Gr\u00FCn = Wahr");
		Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] 3. Rot = Falsch");
		Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] 4. Kein Flyhack/Flymod");
		Bukkit.getServer()
			.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] 5. Der Rechtsweg ist ausgeschlossen.");
		Bukkit.getServer().broadcastMessage(
			ChatColor.LIGHT_PURPLE + "[Server] 6. Eine Barauszahlung ist nicht mšglich.");
		Bukkit.getServer().broadcastMessage(
			ChatColor.LIGHT_PURPLE
				+ "[Server] Bei Missachtung der Regeln ist eine Ausgabe des Gewinns nicht mšglich.");
	    } else if (args[0].equalsIgnoreCase("erkl\u00E4rung")) {
		Bukkit.getServer().broadcastMessage("");
		Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] So funktioniert Wahr oder Falsch:");
		Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] 1. Es gibt eine Aussage");
		Bukkit.getServer().broadcastMessage(
			ChatColor.LIGHT_PURPLE
				+ "[Server] 2. Ihr m\u00FCsst auf gr\u00FCn f\u00FCr wahr und rot f\u00FCr falsch gehen.");
		Bukkit.getServer().broadcastMessage(
			ChatColor.LIGHT_PURPLE
				+ "[Server] 3. Diejenigen, die richtig stehen bleiben oben, die anderen fallen runter.");
		Bukkit.getServer().broadcastMessage(
			ChatColor.LIGHT_PURPLE
				+ "[Server] 4. Mit /spawn kommen diejenigen, die runtergefallen sind wieder zum Spawn.");
		Bukkit.getServer().broadcastMessage(
			ChatColor.LIGHT_PURPLE + "[Server] Nun kšnnt ihr mit /wf join am Event teilnehmen.");
	    } else if (args[0].equalsIgnoreCase("setwarp")) {
		config.setString("WahrFalsch.Warp.World", p.getLocation().getWorld().getName());
		config.setString("WahrFalsch.Warp.X", "" + p.getLocation().getX());
		config.setString("WahrFalsch.Warp.Y", "" + p.getLocation().getY());
		config.setString("WahrFalsch.Warp.Z", "" + p.getLocation().getZ());
		config.setString("WahrFalsch.Warp.Yaw", "" + p.getLocation().getYaw());
		config.setString("WahrFalsch.Warp.Pitch", "" + p.getLocation().getPitch());
		p.sendMessage(ChatColor.GREEN + "WF Warp wurde gesetzt.");
	    } else if ((args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("off"))) {
		if (args[0].equalsIgnoreCase("on")) {
		    config.setBoolean("WahrFalsch.IsEnabled", true);
		    p.sendMessage(ChatColor.GREEN + "WF ist nun angeschaltet.");
		} else {
		    config.setBoolean("WahrFalsch.IsEnabled", false);
		    p.sendMessage(ChatColor.RED + "WF ist nun ausgeschaltet.");
		}
	    } else if (args[0].equalsIgnoreCase("join")) {
		boolean ison;
		ison = config.getBoolean("WahrFalsch.IsEnabled");
		double x, y, z;
		float yaw, pitch;
		String world;
		Location loc;
		try {
		    x = Double.valueOf(config.getString("WahrFalsch.Warp.X"));
		    y = Double.valueOf(config.getString("WahrFalsch.Warp.Y"));
		    z = Double.valueOf(config.getString("WahrFalsch.Warp.Z"));
		    yaw = Float.valueOf(config.getString("WahrFalsch.Warp.Yaw"));
		    pitch = Float.valueOf(config.getString("WahrFalsch.Warp.Pitch"));
		    world = config.getString("WahrFalsch.Warp.World");
		    loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
		} catch (Exception e) {
		    System.out.println("MTS-Fehler:\n");
		    e.printStackTrace();
		    throw new Exception("Fehler beim Laden der Location");
		}
		if (ison) {
		    NotMove nm = new NotMove(user, loc, "dem " + ChatColor.YELLOW + "Wahr oder Falsch Event"
			    + ChatColor.RESET);
		    nm.go(false);
		} else {
		    p.sendMessage(ChatColor.RED + "Derzeit ist kein Wahr oder Falsch Event.");
		}
	    } else if (args[0].equalsIgnoreCase("ask")) {
		if (args.length < 3)
		    throw new VerwendungsException("/wf ask w|f <Frage>");

		String frage = "";
		boolean wahr = args[1].equalsIgnoreCase("w");
		for (int i = 2; i < args.length; i++) {
		    if (i == 2)
			frage += args[i];
		    else
			frage += " " + args[i];
		}
		if (wahr) {
		    int count = 10;
		    Bukkit.getServer().broadcastMessage(
			    ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Wahr" + ChatColor.RED + "Falsch"
				    + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + ChatColor.ITALIC + frage);
		    while (count >= 0) {
			if (count == 0)
			    Bukkit.getServer().broadcastMessage(
				    ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Wahr" + ChatColor.RED + "Falsch"
					    + ChatColor.DARK_GRAY + "] " + ChatColor.GREEN + ChatColor.ITALIC + "Wahr!");
			else
			    Bukkit.getServer().broadcastMessage(
				    ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Wahr" + ChatColor.RED + "Falsch"
					    + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "Noch " + ChatColor.GOLD
					    + count + ChatColor.YELLOW + " Sekunden!");

			if (count > 0) {
			    try {
				Thread.sleep(1000);
			    } catch (InterruptedException e) {
				e.printStackTrace();
			    }
			}
			count--;
		    }
		} else {
		    int count = 10;
		    Bukkit.getServer().broadcastMessage(
			    ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Wahr" + ChatColor.RED + "Falsch"
				    + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + ChatColor.ITALIC + frage);
		    while (count >= 0) {
			if (count == 0)
			    Bukkit.getServer().broadcastMessage(
				    ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Wahr" + ChatColor.RED + "Falsch"
					    + ChatColor.DARK_GRAY + "] " + ChatColor.RED + ChatColor.ITALIC + "Falsch!");
			else
			    Bukkit.getServer().broadcastMessage(
				    ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Wahr" + ChatColor.RED + "Falsch"
					    + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "Noch " + ChatColor.GOLD
					    + count + ChatColor.YELLOW + " Sekunden!");

			if (count > 0) {
			    try {
				Thread.sleep(1000);
			    } catch (InterruptedException e) {
				e.printStackTrace();
			    }
			}
			count--;
		    }
		}
	    }
	} else {
	    if (args.length != 1)
		throw new VerwendungsException("/wf join");

	    if (!args[0].equalsIgnoreCase("join"))
		throw new VerwendungsException("/wf join");

	    if (args[0].equalsIgnoreCase("join")) {
		boolean ison;
		ison = config.getBoolean("WahrFalsch.IsEnabled");
		double x, y, z;
		float yaw, pitch;
		String world;
		Location loc;
		try {
		    x = Double.valueOf(config.getString("WahrFalsch.Warp.X"));
		    y = Double.valueOf(config.getString("WahrFalsch.Warp.Y"));
		    z = Double.valueOf(config.getString("WahrFalsch.Warp.Z"));
		    yaw = Float.valueOf(config.getString("WahrFalsch.Warp.Yaw"));
		    pitch = Float.valueOf(config.getString("WahrFalsch.Warp.Pitch"));
		    world = config.getString("WahrFalsch.Warp.World");
		    loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
		} catch (Exception e) {
		    System.out.println("MTS-Fehler:\n");
		    e.printStackTrace();
		    throw new Exception("Fehler beim Laden der Location");
		}
		if (ison) {
		    NotMove nm = new NotMove(user, loc, "dem " + ChatColor.YELLOW + "Wahr oder Falsch Event"
			    + ChatColor.RESET);
		    nm.go(false);
		} else {
		    p.sendMessage(ChatColor.RED + "Derzeit ist kein Wahr oder Falsch Event.");
		}
	    }
	}
    }

}
