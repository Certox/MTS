package me.minetime.MTS.commands;

import static me.minetime.MTS.MTSData.knifer;
import me.minetime.MTS.api.IConfig;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.clazz.Config;
import me.minetime.MTS.clazz.KnifeFighter;
import me.minetime.MTS.clazz.NotMove;
import me.minetime.MTS.clazz.User;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Commandkf extends CommandMT {

    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {
	final PermissionManager pex = PermissionsEx.getPermissionManager();
	PlayerInventory inv = user.getPlayer().getInventory();

	if (pex.has(user.getPlayer(), "MTS.kf.Admin")) {
	    if (args.length == 0 || args[0].equalsIgnoreCase("hilfe") || args[0].equalsIgnoreCase("help")) {
		cmd_help(user);
	    } else if (args.length == 1 && args[0].equalsIgnoreCase("join")) {
		cmd_join(user);
	    } else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
		cmd_leave(user);
	    } else if (args[0].equalsIgnoreCase("stats")) {
		cmd_stats(user, args);
	    } else if (args.length == 1 && args[0].equalsIgnoreCase("setwait")) {
		cmd_setwait(user.getPlayer().getLocation());
	    } else if (args.length == 2 && args[0].equalsIgnoreCase("setspawn")) {
		int spawn = 0;
		try {
		    spawn = Integer.parseInt(args[1]);
		} catch (Exception e) {
		    System.out.println("MTS-Fehler:\n");
		    e.printStackTrace();
		    throw new VerwendungsException("/kf setspawn 1|2|3|4");
		}
		cmd_setspawn(spawn, user.getPlayer().getLocation());
	    }
	} else {
	    if (args.length == 0 || args[0].equalsIgnoreCase("hilfe") || args[0].equalsIgnoreCase("help")) {
		cmd_help(user);
	    } else if (args.length == 1 && args[0].equalsIgnoreCase("join")) {
		cmd_join(user);
	    } else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
		cmd_leave(user);
	    } else if (args[0].equalsIgnoreCase("stats")) {
		cmd_stats(user, args);
	    }
	}
    }

    private void cmd_setspawn(int spawn, Location location) {
    try {
	IConfig config = new Config();
	config.setString("KnifeFight.Spawn." + spawn + ".X", "" + location.getBlockX());
	config.setString("KnifeFight.Spawn." + spawn + ".Y", "" + location.getBlockY());
	config.setString("KnifeFight.Spawn." + spawn + ".Z", "" + location.getBlockZ());
	config.setString("KnifeFight.Spawn." + spawn + ".World", "" + location.getWorld().getName());
    }
    catch(Exception e){
    	e.printStackTrace();
    }
    }

    private void cmd_join(IUser user) {
	boolean geportet;
	Location l = this.getWait();
	NotMove nm = new NotMove(user, l, "dem " + ChatColor.YELLOW + "KnifeFight" + ChatColor.RESET);
	nm.go(false);

	if (user.getPlayer().getLocation().equals(l))
	    geportet = true;
	else
	    geportet = false;

	if (geportet) {
	    knifer.add(user.getName());
	    if (knifer.size() >= 4) {
		startKnife();
	    }
	}
    }

    private void startKnife() {
    	for (int i = 0; i < 4; i++) {
    		IUser u = new User(knifer.get(i));
    		setfightInv(u);
    		u.getPlayer().teleport(this.getSpawn(i + 1));
    	}
    }

    private void setfightInv(IUser user) {
    PlayerInventory inv = user.getPlayer().getInventory();
	inv.clear();
	ItemStack is = new ItemStack(Material.SHEARS);
	is.addEnchantment(Enchantment.DAMAGE_ALL, 2);
	inv.addItem(is);
    }

    private void cmd_leave(IUser user) {
	Location l = Bukkit.getWorlds().get(0).getSpawnLocation();
	NotMove nm = new NotMove(user, l, "Spawn");
	nm.go(false);
    }

    private void cmd_help(IUser user) {
	final PermissionManager pex = PermissionsEx.getPermissionManager();
	String magicPoints = ChatColor.MAGIC + "" + ChatColor.BLUE + "..." + ChatColor.RESET;
	Player p = user.getPlayer();
	if (pex.has(p, "MTS.kf.Admin")) {
	    p.sendMessage(magicPoints + ChatColor.RED + " KnifeFight " + magicPoints);
	    p.sendMessage(ChatColor.BLUE + "/kf join " + ChatColor.RED + "Warteschleife betreten.");
	    p.sendMessage(ChatColor.BLUE + "/kf leave " + ChatColor.RED + "Warteschleife verlassen.");
	    p.sendMessage(ChatColor.BLUE + "/kf stats <Spielername> " + ChatColor.RED + "Knife-Stats ansehen");
	} else {
	    p.sendMessage(magicPoints + ChatColor.RED + " KnifeFight " + magicPoints);
	    p.sendMessage(ChatColor.BLUE + "/kf join " + ChatColor.RED + "Warteschleife betreten.");
	    p.sendMessage(ChatColor.BLUE + "/kf leave " + ChatColor.RED + "Warteschleife verlassen.");
	    p.sendMessage(ChatColor.BLUE + "/kf stats <Spielername> " + ChatColor.RED + "Knife-Stats ansehen");
	    p.sendMessage(ChatColor.BLUE + "/kf setwait " + ChatColor.RED + "Wartepunkt setzen");
	    p.sendMessage(ChatColor.BLUE + "/kf setspawn <1-4> " + ChatColor.RED + "Spawnpunkt <1-4> setzen");
	}
    }

    private void cmd_setwait(Location loc) {
	IConfig config = new Config();
	config.setString("KnifeFight.Wait.X", "" + loc.getBlockX());
	config.setString("KnifeFight.Wait.Y", "" + loc.getBlockY());
	config.setString("KnifeFight.Wait.Z", "" + loc.getBlockZ());
	config.setString("KnifeFight.Wait.World", "" + loc.getWorld().getName());
    }

    private void cmd_stats(IUser user, String[] args) {
	if (args.length == 1) {
	    KnifeFighter kf = new KnifeFighter(user.getName());
	    kf.printStats(user.getPlayer());
	} else if (args.length == 2) {
	    KnifeFighter kf = new KnifeFighter(args[1]);
	    kf.printStats(user.getPlayer());
	}
    }

    private Location getSpawn(int spawn) {
	IConfig config = new Config();
	Location l = null;
	int x, y, z;
	String world;
	try {
	    x = Integer.parseInt(config.getString("KnifeFight.Spawn." + spawn + ".X"));
	    y = Integer.parseInt(config.getString("KnifeFight.Spawn." + spawn + ".Y"));
	    z = Integer.parseInt(config.getString("KnifeFight.Spawn." + spawn + ".Z"));
	    world = config.getString("KnifeFight.Spawn." + spawn + ".World");
	    l = new Location(Bukkit.getWorld(world), x, y, z);
	} catch (Exception e) {
	    System.out.println("MTS-Fehler:\n");
	    e.printStackTrace();
	}
	return l;
    }

    private Location getWait() {
	IConfig config = new Config();
	Location l = null;
	Double x, y, z;
	String world;
	try {
	    x = Double.valueOf(config.getString("KnifeFight.Wait.X"));
	    y = Double.valueOf(config.getString("KnifeFight.Wait.Y"));
	    z = Double.valueOf(config.getString("KnifeFight.Wait.Z"));
	    world = config.getString("KnifeFight.Wait.World");
	    l = new Location(Bukkit.getWorld(world), x, y, z);
	} catch (Exception e) {
	    System.out.println("MTS-Fehler:\n");
	    e.printStackTrace();
	}
	return l;
    }

}
