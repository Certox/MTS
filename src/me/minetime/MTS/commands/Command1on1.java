package me.minetime.MTS.commands;

import me.minetime.MTS.api.IConfig;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.clazz.Config;
import me.minetime.MTS.clazz.User;
import me.minetime.MTS.exception.NoPermissionException;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Command1on1 extends CommandMT {

    public static boolean status = false;
    public static boolean join = true;

    public static String u1 = "";
    public static String u2 = "";

    public static ItemStack[] inv1 = null;
    public static ItemStack[] inv2 = null;

    public static ItemStack[] armor1 = null;
    public static ItemStack[] armor2 = null;

    public static int xp1 = 0;
    public static int xp2 = 0;

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {
	Player player = user.getPlayer();
	IConfig config = new Config();

	if (args.length == 0) {
	    if(player.isOp()){
	    	throw new VerwendungsException("/1on1 <join:leave:togglejoin:clear:setprepare:setstart:setrespawn>");
	    }
	    else
	    {
	    	throw new VerwendungsException("/1on1 <join:leave>");
	    }
	}
	
	if(args[0].equalsIgnoreCase("setprepare")){
		if(!player.isOp())
			throw new NoPermissionException();
		
		config.setString("1on1.prepare1.world", player.getWorld().getName());
		config.setString("1on1.prepare1.x", String.valueOf(player.getLocation().getX()));
		config.setString("1on1.prepare1.y", String.valueOf(player.getLocation().getY()));
		config.setString("1on1.prepare1.z", String.valueOf(player.getLocation().getZ()));
		
		player.sendMessage(ChatColor.GREEN + "Prepare wurde gesetzt.");
	}
	
	if(args[0].equalsIgnoreCase("setrespawn")){
		if(!player.isOp())
			throw new NoPermissionException();
		
		config.setString("1on1.respawn.world", player.getWorld().getName());
		config.setString("1on1.respawn.x", String.valueOf(player.getLocation().getX()));
		config.setString("1on1.respawn.y", String.valueOf(player.getLocation().getY()));
		config.setString("1on1.respawn.z", String.valueOf(player.getLocation().getZ()));
		
		player.sendMessage(ChatColor.GREEN + "Respawn wurde gesetzt.");
	}
	
	if(args[0].equalsIgnoreCase("setstart")){
		if(!player.isOp())
			throw new NoPermissionException();
		if(args.length != 2)
			throw new VerwendungsException("/1on1 setstart <1:2>");
		
		if(args[1].equalsIgnoreCase("1")){
			config.setString("1on1.start1.world", player.getWorld().getName());
			config.setString("1on1.start1.x", String.valueOf(player.getLocation().getX()));
			config.setString("1on1.start1.y", String.valueOf(player.getLocation().getY()));
			config.setString("1on1.start1.z", String.valueOf(player.getLocation().getZ()));
			
			player.sendMessage(ChatColor.GREEN + "Start-1 wurde gesetzt.");
		}
		else
		{
			if(args[1].equalsIgnoreCase("2")){
				config.setString("1on1.start2.world", player.getWorld().getName());
				config.setString("1on1.start2.x", String.valueOf(player.getLocation().getX()));
				config.setString("1on1.start2.y", String.valueOf(player.getLocation().getY()));
				config.setString("1on1.start2.z", String.valueOf(player.getLocation().getZ()));
				
				player.sendMessage(ChatColor.GREEN + "Start-2 wurde gesetzt.");
			}
			else
			{
				throw new VerwendungsException("/1on1 setstart <1:2>");
			}
		}
	}
	
	if(args[0].equalsIgnoreCase("clear")){
		if(!player.isOp())
			throw new NoPermissionException();
		
		if(u1.equals("") == false && u2.equals("") == false){
			if(new User(u1).isOnline()){
				Player p1 = new User(u1).getPlayer();
				
			    // give back
			    p1.getInventory().setContents(inv1);

			    p1.getInventory().setArmorContents(armor1);

			    p1.setExp(xp1);

			    p1.teleport(
				    new Location(Bukkit.getWorld(config.getString("1on1.respawn.world")), Double.valueOf(config
					    .getString("1on1.respawn.x")), Double.valueOf(config.getString("1on1.respawn.y")), Double
					    .valueOf(config.getString("1on1.respawn.z"))));

			    p1.sendMessage(ChatColor.RED + "Der Kampf wurde beendet.");
			}
			if(new User(u2).isOnline()){
				Player p2 = new User(u2).getPlayer();
				
			    // give back
			    p2.getInventory().setContents(inv2);

			    p2.getInventory().setArmorContents(armor2);

			    p2.setExp(xp2);

			    p2.teleport(
				    new Location(Bukkit.getWorld(config.getString("1on1.respawn.world")), Double.valueOf(config
					    .getString("1on1.respawn.x")), Double.valueOf(config.getString("1on1.respawn.y")), Double
					    .valueOf(config.getString("1on1.respawn.z"))));

			    p2.sendMessage(ChatColor.RED + "Der Kampf wurde beendet.");
			}
		}

	    // reset itemstacksn shit
	    inv1 = null;
	    inv2 = null;

	    armor1 = null;
	    armor2 = null;

	    xp1 = 0;
	    xp2 = 0;
	    
	    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "heal " + u1);
	    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "heal " + u2);

	    u1 = "";
	    u2 = "";

	    status = false;
	    
	    player.sendMessage(ChatColor.GREEN + "1on1 geleert!");
	}
	
	if (args[0].equalsIgnoreCase("togglejoin")) {
		if(!player.isOp())
			throw new NoPermissionException();
		
		if(join){
			join = false;
			player.sendMessage(ChatColor.GREEN + "Joinen deaktiviert.");
		}
		else
		{
			join = true;
			player.sendMessage(ChatColor.GREEN + "Joinen aktiviert.");
		}
	}
	
	if (args[0].equalsIgnoreCase("leave")) {
		if(status){
			if(u1.equalsIgnoreCase(player.getName())){
				Bukkit.broadcastMessage(ChatColor.GOLD + "[1on1] " + ChatColor.GREEN
						+ me.minetime.MTS.commands.Command1on1.u2 + ChatColor.AQUA + " hat gegen " + ChatColor.RED
						+ me.minetime.MTS.commands.Command1on1.u1 + ChatColor.AQUA + " gewonnen!");
				close1on1();
			}
			if(u2.equalsIgnoreCase(player.getName())){
				Bukkit.broadcastMessage(ChatColor.GOLD + "[1on1] " + ChatColor.GREEN
						+ me.minetime.MTS.commands.Command1on1.u1 + ChatColor.AQUA + " hat gegen " + ChatColor.RED
						+ me.minetime.MTS.commands.Command1on1.u2 + ChatColor.AQUA + " gewonnen!");
				close1on1();
			}
		}
		else
		{
			if(u1.equalsIgnoreCase(player.getName())){
				Player p1 = new User(u1).getPlayer();
				
			    // give back
			    p1.getInventory().setContents(inv1);

			    p1.getInventory().setArmorContents(armor1);

			    p1.setExp(xp1);

			    p1.teleport(
				    new Location(Bukkit.getWorld(config.getString("1on1.respawn.world")), Double.valueOf(config
					    .getString("1on1.respawn.x")), Double.valueOf(config.getString("1on1.respawn.y")), Double
					    .valueOf(config.getString("1on1.respawn.z"))));

			    p1.sendMessage(ChatColor.RED + "Der Kampf wurde beendet.");

			    // reset itemstacksn shit
			    inv1 = null;
			    inv2 = null;

			    armor1 = null;
			    armor2 = null;

			    xp1 = 0;
			    xp2 = 0;

			    u1 = "";
			    u2 = "";
			}
		}
			
	}
	
	if (args[0].equalsIgnoreCase("join")) {
		if(!player.isOp())
			if(!join)
				throw new Exception("Du kannst jetzt keinen Kampf betreten.");
		
		if(status) 
			throw new Exception("Es l\u00E4uft bereits ein Kampf.");
		if(u1.equalsIgnoreCase(player.getName()))
			throw new Exception("Du bist bereits im Kampf.");
		
		String name = "";
		if(player.isOp()){
			if(args.length == 2){
				if(new User(args[1]).isOnline()){
					name = args[1];
				}
				else
				{
					throw new Exception("Der Spieler ist nicht online");
				}
			}
			else
			{
				name = player.getName();
			}
		}
		else
		{
			name = player.getName();
		}
		
		// Join
		if(u1.equals("")){
			u1 = name;
			Player p1 = new User(name).getPlayer();
			inv1 = p1.getInventory().getContents();
			armor1 = p1.getInventory().getArmorContents();
			xp1 = (int) p1.getExp();
			// Alles leeren
			p1.getInventory().clear();
			p1.getInventory().setArmorContents(new ItemStack[4]);
			p1.setExp(0);
			// Kits geben
			p1.getInventory().addItem(new ItemStack(276, 1));
			p1.getInventory().addItem(new ItemStack(261, 1));
			p1.getInventory().addItem(new ItemStack(322, 10));
			p1.getInventory().addItem(new ItemStack(262, 32));
			p1.getInventory().setHelmet(new ItemStack(310, 1));
			p1.getInventory().setChestplate(new ItemStack(311, 1));
			p1.getInventory().setLeggings(new ItemStack(312, 1));
			p1.getInventory().setBoots(new ItemStack(313, 1));

			// Teleportieren
			p1.teleport(
				new Location(Bukkit.getWorld(config.getString("1on1.prepare1.world")), Double.valueOf(config
					.getString("1on1.prepare1.x")), Double.valueOf(config.getString("1on1.prepare1.y")), Double
					.valueOf(config.getString("1on1.prepare1.z"))));
			p1.sendMessage(
				ChatColor.GREEN + "Du wurdest in die Arena teleportiert, der Kampf beginnt, sobald jemand den Kampf betritt.");
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "heal " + u1);
		}
		else
		{
			u2 = name;
			Player p2 = new User(name).getPlayer();
			inv2 = p2.getInventory().getContents();
			armor2 = p2.getInventory().getArmorContents();
			xp2 = (int) p2.getExp();
			// Alles leeren
			p2.getInventory().clear();
			p2.getInventory().setArmorContents(new ItemStack[4]);
			p2.setExp(0);
			// Kits geben
			p2.getInventory().addItem(new ItemStack(276, 1));
			p2.getInventory().addItem(new ItemStack(261, 1));
			p2.getInventory().addItem(new ItemStack(322, 10));
			p2.getInventory().addItem(new ItemStack(262, 32));
			p2.getInventory().setHelmet(new ItemStack(310, 1));
			p2.getInventory().setChestplate(new ItemStack(311, 1));
			p2.getInventory().setLeggings(new ItemStack(312, 1));
			p2.getInventory().setBoots(new ItemStack(313, 1));

			Player p1 = new User(u1).getPlayer();
			
			status = true;
			
			p1.teleport(
				    new Location(Bukkit.getWorld(config.getString("1on1.start1.world")), Double.valueOf(config
					    .getString("1on1.start1.x")), Double.valueOf(config.getString("1on1.start1.y")), Double
					    .valueOf(config.getString("1on1.start1.z"))));
			    p1.sendMessage(ChatColor.GREEN + "Der Kampf beginnt!");

			    p2.getPlayer().teleport(
				    new Location(Bukkit.getWorld(config.getString("1on1.start2.world")), Double.valueOf(config
					    .getString("1on1.start2.x")), Double.valueOf(config.getString("1on1.start2.y")), Double
					    .valueOf(config.getString("1on1.start2.z"))));
			    p2.sendMessage(ChatColor.GREEN + "Der Kampf beginnt!");

			    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "heal " + u1);
			    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "heal " + u2);
			
		}
	}
    }

    public static void close1on1() {
	IConfig config = new Config();

	if (status == true) {
		Player p1 = new User(u1).getPlayer();
		Player p2 = new User(u2).getPlayer();
		
	    // give back
	    p1.getInventory().setContents(inv1);
	    p2.getInventory().setContents(inv2);

	    p1.getInventory().setArmorContents(armor1);
	    p2.getInventory().setArmorContents(armor2);

	    p1.setExp(xp1);
	    p2.setExp(xp2);

	    p1.teleport(
		    new Location(Bukkit.getWorld(config.getString("1on1.respawn.world")), Double.valueOf(config
			    .getString("1on1.respawn.x")), Double.valueOf(config.getString("1on1.respawn.y")), Double
			    .valueOf(config.getString("1on1.respawn.z"))));
	    p2.teleport(
		    new Location(Bukkit.getWorld(config.getString("1on1.respawn.world")), Double.valueOf(config
			    .getString("1on1.respawn.x")), Double.valueOf(config.getString("1on1.respawn.y")), Double
			    .valueOf(config.getString("1on1.respawn.z"))));

	    p1.sendMessage(ChatColor.RED + "Der Kampf wurde beendet.");
	    p2.sendMessage(ChatColor.RED + "Der Kampf wurde beendet.");

	    // reset itemstacksn shit
	    inv1 = null;
	    inv2 = null;

	    armor1 = null;
	    armor2 = null;

	    xp1 = 0;
	    xp2 = 0;
	    
	    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "heal " + u1);
	    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "heal " + u2);

	    u1 = "";
	    u2 = "";

	    status = false;
	    
	    p1.setHealth(20);
	    p1.setFoodLevel(20);
	    p1.setFireTicks(0);
	    
	    p2.setHealth(20);
	    p2.setFoodLevel(20);
	    p2.setFireTicks(0);
	}
    }

}
