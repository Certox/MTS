package me.minetime.MTS;

import static me.minetime.MTS.clazz.Message.msg;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.User;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;

public class MTSEntityListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    // EVENTHANDLER
    public void onEntityDamagebyEntity(EntityDamageByEntityEvent event) {

	boolean isCancelled = false;

	if (MTSData.pvp == false) {
	    isCancelled = true;
	    event.setCancelled(true);
	}

	if (event.getDamager() instanceof Player) {
	    Player p = (Player) event.getDamager();
	    if (p.getItemInHand().getType().equals(Material.WRITTEN_BOOK)) {
		if (!p.getItemInHand().getEnchantments().isEmpty()) {
		    final PermissionManager pex = PermissionsEx.getPermissionManager();
		    if (!pex.has(p, "MTS.damageBook")) {
			for (Player playa : Bukkit.getOnlinePlayers()) {
			    if (playa.isOp() && pex.has(playa, "MTS.AntiBook")) {
				playa.sendMessage(ChatColor.GOLD + "[AntiBook] " + ChatColor.RED + p.getName()
					+ " versucht mit einem Buch zu schlagen.");
			    }
			}
			event.setCancelled(true);
			isCancelled = true;
			p.kickPlayer(ChatColor.RED + "Mit einem Buch k\u00E4mpft man doch nicht!");
		    }
		}
	    }
	}

	//
	if (event.getEntity() instanceof Villager) {
	    Villager o = (Villager) event.getEntity(); // OPfer definieren
	    if (event.getDamager() instanceof Player) {
		((Player) event.getDamager()).sendMessage(msg("damageVillager"));
		isCancelled = true;

	    }
	    event.setCancelled(true);
	    o.setHealth(20);

	}
	if (!isCancelled)
	    if (event.getDamager() instanceof EnderPearl) {
		event.setCancelled(true);
		isCancelled = true;
	    }
	if (!isCancelled)
	    if (event.getEntity() instanceof Player) {
		Player o = (Player) event.getEntity();

		if (event.getDamager() instanceof Player) {
		    Player a = (Player) event.getDamager();

		    if (isCancelled == false) { // Region prüfung
			WorldGuardPlugin worldGuard = (WorldGuardPlugin) Bukkit.getServer().getPluginManager()
				.getPlugin("WorldGuard");
			Location pt = o.getLocation();
			LocalPlayer localPlayer = worldGuard.wrapPlayer(o);
			World world = o.getWorld();
			RegionManager regionManager = worldGuard.getRegionManager(world);
			ApplicableRegionSet set = regionManager.getApplicableRegions(pt);
			if (!set.allows(DefaultFlag.SLEEP, localPlayer)) {
			    isCancelled = true;
			    event.setCancelled(false);
			}
		    }

		    if (isCancelled == false) { // Friede prüfen
			if (MTSData.TBL_friede.get(a.getName()) != null)
			    if (MTSData.TBL_friede.get(a.getName()).contains(o.getName())) {
				isCancelled = true;
				event.setCancelled(true);
			    }
		    }

		    /*
		    IUser Usera = new User(a.getName());
		    IUser Usero = new User(o.getName());
		    if (isCancelled == false) { // Prüfen, ob Opfer neu ist
			if (Usero.isNew()) {
			    isCancelled = true;
			    a.sendMessage(ChatColor.RED
				    + "Dieser Spieler ist neu, du kannst ihn seine ersten 20 Spielminuten nicht angreifen.");
			    event.setCancelled(true);
			}
		    }
		    */

		    /*
		    if (isCancelled == false) { // Prüfen, ob ANgreifer neu ist
			if (Usera.isNew()) {
			    isCancelled = true;
			    a.sendMessage(ChatColor.RED
				    + "Du kannst deine ersten 20 Spielminuten nicht kämpfen, gehe zuerst farmen oder ein Lager aufbauen :)");
			    event.setCancelled(true);
			}
		    }
		    */

		}
		//

		else if (event.getDamager() instanceof Arrow || event.getDamager() instanceof Snowball
			|| event.getDamager() instanceof Egg) {

		    Projectile ar = (Projectile) event.getDamager();
		    if (ar.getShooter() instanceof Player) {
			Player a = (Player) ar.getShooter(); // Schiesser des
							     // Pfeils
							     // definieren

			IUser Usera = new User(a.getName());
			IUser Usero = new User(o.getName());

			if (isCancelled == false) { // Region prüfung
			    WorldGuardPlugin worldGuard = (WorldGuardPlugin) Bukkit.getServer().getPluginManager()
				    .getPlugin("WorldGuard");
			    Location pt = o.getLocation();
			    LocalPlayer localPlayer = worldGuard.wrapPlayer(o);
			    World world = o.getWorld();
			    RegionManager regionManager = worldGuard.getRegionManager(world);
			    ApplicableRegionSet set = regionManager.getApplicableRegions(pt);
			    if (!set.allows(DefaultFlag.SLEEP, localPlayer)) {
				isCancelled = true;
				event.setCancelled(false);
			    }
			}

			if (isCancelled == false) { // Friede prüfen

			    if (MTSData.TBL_friede.get(a.getName()) != null)
				if (MTSData.TBL_friede.get(a.getName()).contains(o.getName())) {
				    isCancelled = true;
				    event.setCancelled(true);
				}
			}

			if (isCancelled == false) { // Prüfen, ob Opfer neu ist
			    if (Usero.isNew()) {
				isCancelled = true;
				a.sendMessage(ChatColor.RED
					+ "Dieser Spieler ist neu, du kannst ihn seine ersten 20 Spielminuten nicht angreifen.");
				event.setCancelled(true);
			    }
			}

			if (isCancelled == false) { // Prüfen, ob ANgreifer neu
						    // ist
			    if (Usera.isNew()) {
				isCancelled = true;
				a.sendMessage(ChatColor.RED
					+ "Du kannst deine ersten 20 Spielminuten nicht kämpfen, gehe zuerst farmen oder ein Lager aufbauen :)");
				event.setCancelled(true);
			    }
			}
			//
		    }
		}
	    }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    // EVENTHANDLER
    public void onEntityDamage(EntityDamageEvent event) {
    	if (event.getEntity() instanceof Player){
    		Player player = (Player) event.getEntity();
    		
    		//Fall-Damage
    		if (event.getCause() == EntityDamageEvent.DamageCause.FALL)
    			event.setCancelled(true);
    		
    		//1on1
    		if (me.minetime.MTS.commands.Command1on1.status == true) {
    			int health = player.getHealth() * 2;
    			if(health <= event.getDamage()){
    				if (me.minetime.MTS.commands.Command1on1.u1.equalsIgnoreCase(player.getName())) {
    					event.setCancelled(true);
    					Bukkit.broadcastMessage(ChatColor.GOLD + "[1on1] " + ChatColor.GREEN
    							+ me.minetime.MTS.commands.Command1on1.u2 + ChatColor.AQUA + " hat gegen " + ChatColor.RED
    							+ me.minetime.MTS.commands.Command1on1.u1 + ChatColor.AQUA + " gewonnen!");
    					me.minetime.MTS.commands.Command1on1.close1on1();
    				}
    				else
    				{
    					if (me.minetime.MTS.commands.Command1on1.u2.equalsIgnoreCase(player.getName())) {
    						event.setCancelled(true);
    						Bukkit.broadcastMessage(ChatColor.GOLD + "[1on1] " + ChatColor.GREEN
        							+ me.minetime.MTS.commands.Command1on1.u1 + ChatColor.AQUA + " hat gegen " + ChatColor.RED
        							+ me.minetime.MTS.commands.Command1on1.u2 + ChatColor.AQUA + " gewonnen!");
    						me.minetime.MTS.commands.Command1on1.close1on1();
    					}
    				}
    			}
			}
    		
    	    if(MTSData.kfrunning){
    	    	
    	    	if(new User(player.getName()).isInKnifeFight()){
    	    	
    	    		int health = player.getHealth() * 2;
    	    		if(health <= event.getDamage()){
    	    			event.setCancelled(true);
        	    		for(String s : MTSData.knife.keySet()){
        	    			//Message an die Spieler schicken
        	    			Bukkit.getServer().getPlayer(s).sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.AQUA + " ist gestorben und somit augeschieden!");
        	    			Bukkit.getServer().getPlayer(s).sendMessage(ChatColor.AQUA + "Es sind noch " + ChatColor.YELLOW + "" + MTSData.knife.size() + " Spieler " + ChatColor.AQUA + "am Leben!");
        	    		}

            		
        	    		//Zum Spawn porten
        	    		player.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
            		
        	    		//Inventar clearen und altes zurueckgeben
        	    		player.getInventory().clear();
        	    		PlayerInventory pi = MTSData.knife.get(player.getName());
            		
        	    		for(ItemStack is : pi){
        	    			player.getInventory().addItem(is);
        	    		}
            		
        	    		player.getInventory().setArmorContents(pi.getArmorContents());
        	    		
        	    		new User(player.getName()).setInKnifeFight(false);
    	    		}
    	    	}
    	    }
    	}
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    // EVENTHANDLER
    public void onEntityDeath(PlayerDeathEvent event) {

	event.setDeathMessage(null);

	boolean isCancelled = false;
	if (event.getEntity() instanceof Player) {
	    boolean enchBookDrop = false;
	    
	    Player o = (Player) event.getEntity(); // OPfer definieren

	    for (int i = 0; i < event.getDrops().size(); i++) {
		if (event.getDrops().get(i).getType().equals(Material.WRITTEN_BOOK)
			&& !event.getDrops().get(i).getEnchantments().isEmpty()) {
		    event.getDrops().remove(i);
		    enchBookDrop = true;
		}
	    }

	    final PermissionManager pex = PermissionsEx.getPermissionManager();
	    Player p = o;
	    if (enchBookDrop && !pex.has(p, "MTS.dropEnchantBook")) {
		for (Player playa : Bukkit.getOnlinePlayers()) {
		    if (playa.isOp() && pex.has(playa, "MTS.AntiBook")) {
			playa.sendMessage(ChatColor.GOLD + "[AntiBook] " + ChatColor.RED + p.getName()
				+ " hatte ein enchantetes Buch im Inventar und ist gestorben.");
		    }
		}
		p.getInventory().setContents(null);
		p.getInventory().setArmorContents(null);
		p.kickPlayer(ChatColor.RED + "Wieso hattest du denn ein enchantetes Buch im Inventar?");
	    }

	    Player a = o.getKiller();

	    if (isCancelled == false) { // KIlls/Tode eintragen
		if (a != null) {
		    IUser Usera = new User(a.getName());
		    Usera.addKill();
		    if (Usera.isInClan())
			Usera.getClan().addClanKills(1);
		    IUser Usero = new User(o.getName());

		    Usero.addDeath();
		    if (Usero.isInClan())
			Usero.getClan().addClanDeaths(1);
		    // Usera.getPlayer().sendMessage(msg("killMessage",
		    // Usero.getName()));
		    //
		    // //Kill-Message an den Killer senden
		    // double KDa = Double.valueOf(Usera.getKills());
		    // if(Usera.getDeaths() != 0){
		    // KDa = (double)Usera.getKills()/Usera.getDeaths(); // KD
		    // ausrechnen
		    // KDa = Math.round(KDa*100)/100.0; // KD auf 2
		    // nachkommastellen runden
		    // }
		    // Usera.getPlayer().sendMessage(msg("statsMessage",
		    // Usera.getKills(), Usera.getDeaths(), KDa));
		    //
		    // //Death-Message an das Opfer senden
		    // Usero.getPlayer().sendMessage(msg("deathMessage",
		    // Usera.getName()));
		    // double KDo = Double.valueOf(Usero.getKills());
		    // if(Usero.getDeaths() != 0){
		    // KDo = (double)Usero.getKills()/Usero.getDeaths(); // KD
		    // ausrechnen
		    // KDo = Math.round(KDo*100)/100.0; // KD auf 2
		    // nachkommastellen runden
		    // }
		    // Usero.getPlayer().sendMessage(msg("statsMessage",
		    // Usero.getKills(), Usero.getDeaths(), KDo));

		}

	    }
	}
    }
}
