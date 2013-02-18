package me.minetime.MTS.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import me.minetime.MTS.MTSData;
import me.minetime.MTS.api.IConfig;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.clazz.Config;
import me.minetime.MTS.clazz.User;
import me.minetime.MTS.exception.NoPermissionException;

public class Commandknifefight extends CommandMT {

	final PermissionManager pex = PermissionsEx.getPermissionManager();
	
	public boolean status = false;
	
	public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {
		Player p = user.getPlayer();
		if(args.length == 0){
			showHelp(user);
		} else {
			if(args[0].equalsIgnoreCase("join")){
				join(user);
			} else if(args[0].equalsIgnoreCase("stats")){
				stats(user);
			}  else if(args[0].equalsIgnoreCase("spectate")){
				spectate(user);
			} else if(args[0].equalsIgnoreCase("togglejoin")){
				if(pex.has(p, "MTS.knifefight.admin")){
					togglejoin(user);
				} else {
					throw new NoPermissionException();
				}
			} else if(args[0].equalsIgnoreCase("stop")){
				if(pex.has(p, "MTS.knifefight.admin")){
					stop(user);
				} else {
					throw new NoPermissionException();
				}
			} else if(args[0].equalsIgnoreCase("setspawn") && args.length > 1){
				if(pex.has(p, "MTS.knifefight.admin")){
					setspawn(user, args);
				} else {
					throw new NoPermissionException();
				}
			}
		}
	}
	
	public void showHelp(IUser user){
		Player p = user.getPlayer();
		p.sendMessage(ChatColor.AQUA + "---------------- KnifeFight ----------------");
		p.sendMessage(ChatColor.GOLD + "KnifeFight Arena betreten: " + ChatColor.DARK_AQUA + "/knifefight join");
		p.sendMessage(ChatColor.GOLD + "KnifeFight Stats ansehen: " + ChatColor.DARK_AQUA + "/knifefight stats");
		p.sendMessage(ChatColor.GOLD + "KnifeFight Kampf beobachten: " + ChatColor.DARK_AQUA + "/knifefight spectate");
		if(pex.has(p, "MTS.knifefight.admin")){
			p.sendMessage(ChatColor.GOLD + "KnifeFight (de)aktivieren: " + ChatColor.DARK_AQUA + "/knifefight togglejoin");
			p.sendMessage(ChatColor.GOLD + "Aktuellen Kampf beenden: " + ChatColor.DARK_AQUA + "/knifefight stop");
			p.sendMessage(ChatColor.GOLD + "KnifeFight Spawns setzen: " + ChatColor.DARK_AQUA + "/knifefight setspawn <1|2|3|4>");
		}
	}
	
	public void join(IUser user) throws Exception{
		Player p = user.getPlayer();
		if(!MTSData.kfenabled){
			throw new Exception("KnifeFight ist zur Zeit deaktiviert!");
		}
		if(MTSData.knife.size() < 4){
			MTSData.knife.put(p.getName(), p.getInventory());
			for(String s : MTSData.knife.keySet()){
				Bukkit.getServer().getPlayer(s).sendMessage(ChatColor.YELLOW + "" + MTSData.knife.size() + " / 4 Spielern " + ChatColor.AQUA + "sind dem Spiel beigetreten!");
			}
			if(MTSData.knife.size() == 4){
				start();
			}
		} else {
			p.sendMessage(ChatColor.AQUA + "Das Spiel hat bereits begonnen! Zuschauen? /knifefight spectate");
		}
	}
	
	public void stats(IUser user){
		Player p = user.getPlayer();
		p.sendMessage("Stats:");
	}
	
	public void togglejoin(IUser user){
		Player p = user.getPlayer();
		MTSData.kfenabled = !MTSData.kfenabled;
	}
	
	public void stop(IUser user){
		Player p = user.getPlayer();
		
	}
	
	public void setspawn(IUser user, String[] args) throws Exception{
		Player p = user.getPlayer();
		if(isNumeric(args[1])){
			int i = Integer.parseInt(args[1]);
			if(i > 4){
				throw new Exception("What? Es gibt folgende Spawns: 1, 2, 3 und 4! Volldepp!");
			}
			
			IConfig config = new Config();
			Location loc = p.getLocation();

			config.setString("KnifeFight.Spawn." + i + ".X", String.valueOf(loc.getX()));
			config.setString("KnifeFight.Spawn." + i + ".Y", String.valueOf(loc.getY()));
			config.setString("KnifeFight.Spawn." + i + ".Z", String.valueOf(loc.getZ()));
			config.setString("KnifeFight.Spawn." + i + ".World", loc.getWorld().getName());
			
			p.sendMessage(ChatColor.AQUA + "Spawn " + ChatColor.YELLOW + i + ChatColor.AQUA + " wurde gesetzt!");
		} else {
			throw new Exception("Bist du vollkommen behindert? Der Spawn muss 'ne Zahl sein, du Homo!");
		}
	}
	
	public void spectate(IUser user) throws Exception{
		if(!MTSData.kfenabled){
			throw new Exception("KnifeFight ist zur Zeit deaktiviert!");
		}
		
		//Spieler zum Beobachtungs-Punkt teleportieren!
	}
	
	public void start(){
		MTSData.kfrunning = true;
		int i = 1;
		for(String s : MTSData.knife.keySet()){
			Player p = Bukkit.getServer().getPlayer(s);
			p.sendMessage(ChatColor.AQUA + "Los geht's!");
			
			//Inventar-Shit
			p.getInventory().clear();
			ItemStack is = new ItemStack(Material.SHEARS);
			is.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
			p.getInventory().addItem(is);
			
			p.teleport(getSpawn(i));
			
			User user = new User(s);
			user.setInKnifeFight(true);
			
			i++;

		}
	}
	
    private Location getSpawn(int spawn) {
	IConfig config = new Config();
	Location l = null;
	double x, y, z;
	String world;
	try {
	    x = Double.valueOf(config.getString("KnifeFight.Spawn." + spawn + ".X"));
	    y = Double.valueOf(config.getString("KnifeFight.Spawn." + spawn + ".Y"));
	    z = Double.valueOf(config.getString("KnifeFight.Spawn." + spawn + ".Z"));
	    world = config.getString("KnifeFight.Spawn." + spawn + ".World");
	    l = new Location(Bukkit.getWorld(world), x, y, z);
	} catch (Exception e) {
	    System.out.println("MTS-Fehler:\n");
	    e.printStackTrace();
	}
	return l;
    }
    
    private boolean isNumeric(String s){
    	boolean isnumeric = false;
    	try {
    		Integer.parseInt(s);
    		isnumeric = true;
    	}
    	catch(NumberFormatException nFE){
    		isnumeric = false;
    	}
    	return isnumeric;
    }

}
