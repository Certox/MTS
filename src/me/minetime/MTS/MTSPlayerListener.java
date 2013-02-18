package me.minetime.MTS;

import static me.minetime.MTS.clazz.Message.msg;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TimeZone;

import me.minetime.MTS.api.IClan;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.Config;
import me.minetime.MTS.clazz.NewPlayerGuide;
import me.minetime.MTS.clazz.NotMove;
import me.minetime.MTS.clazz.Notify;
import me.minetime.MTS.clazz.User;
import me.minetime.MTS.exception.NoPermissionException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;

public class MTSPlayerListener implements Listener {


	
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onPlayerDrop(PlayerDropItemEvent event) {
	ItemStack dropper = event.getItemDrop().getItemStack();
	if (dropper.equals(Material.WRITTEN_BOOK)) {
	    if (!dropper.getEnchantments().isEmpty()) {
		Player p = event.getPlayer();
		final PermissionManager pex = PermissionsEx.getPermissionManager();
		if (!pex.has(p, "MTS.dropEnchantBook")) {
		    for (Player playa : Bukkit.getOnlinePlayers()) {
			if (playa.isOp() && pex.has(playa, "MTS.AntiBook")) {
			    playa.sendMessage(ChatColor.GOLD + "[AntiBook] " + ChatColor.RED + p.getName()
				    + " versucht ein enchantetes Buch zu droppen.");
			}
		    }
		    event.setCancelled(true);
		    p.kickPlayer(ChatColor.RED + "Wieso willst du denn ein enchantetes Buch droppen?");
		}
	    }
	}
    }
    
    

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onPlayerRespawn(PlayerRespawnEvent e) {
	User user = new User(e.getPlayer().getName());

	if (MTSData.customRespawn.containsKey(user.getName())) {
	    e.setRespawnLocation(MTSData.customRespawn.get(user.getName()));
	}
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) throws Exception {
	    Player player = event.getPlayer();

	    ItemStack item = player.getItemInHand();
	    if(item != null){
	    	if(item.getType() == Material.POTION){
	    		
	    		if(item.getDurability() == 32766 || item.getDurability() == 32702 ||
	    				item.getDurability() == 16318 || item.getDurability() == 16382){
	    			
	    			event.setCancelled(true);
	    			player.sendMessage(ChatColor.RED + "Unsichtbarkeitstränke sind deaktiviert!");
	    			item.setDurability((short) 0);
	    			return;
	    		}
	    	}
	    }
	    Block block = event.getClickedBlock();

	    if(block == null)
	    	return;
	    
	    if(MTSData.adventsSchilder.containsKey(block.getLocation())){
    		int tag = MTSData.adventsSchilder.get(block.getLocation());
    		
    		try{
    			ResultSet rs = MySQL.Query("SELECT `id` FROM `mt_advent` WHERE `day`='" + tag + "' AND `name`='" + player.getName() + "';");
    			
    			while(rs.next()){
    				player.sendMessage(ChatColor.RED + "Du hast diese Türchen bereits geöffnet!");
    				return;
    			}
    			
    			rs.close();
    		}catch(Exception ex){
    			ex.printStackTrace();
    		}
    		
    		Date myDate = new Date();
    	    SimpleDateFormat df2 = new SimpleDateFormat("dd");
    	    df2.setTimeZone(TimeZone.getDefault());
    	    df2.format(myDate);

    	    Calendar gc2 = new GregorianCalendar();
    	    gc2.setTime(myDate);
    	    Date now = gc2.getTime();
    	    
    	    int date = Integer.parseInt(df2.format(now));
    		
    	    if(date < 1 || date > 24){
    	    	player.sendMessage(ChatColor.RED + "Es ist noch nicht Advent!");
    	    	return;
    	    }
    	    
    	    if(tag > date){
    	    	player.sendMessage(ChatColor.RED + "Es ist noch nicht der " + tag + ". Dezember!");
    	    	return;
    	    }
    	    
    	    try{
    			ResultSet rs = MySQL.Query("SELECT `itemid`, `amount` FROM `mt_advent_items` WHERE `day`='" + tag + "';");
    			
    			while(rs.next()){
    				player.getInventory().addItem(new ItemStack(rs.getInt(1), rs.getInt(2)));
    			}
    			
    			rs.close();
    		}catch(Exception ex){
    			ex.printStackTrace();
    		}
    	    
    	    player.sendMessage(ChatColor.GREEN + "Du hast das " + tag + ". Türchen geöffnet und eine Überraschung bekommen!");
    	    
    	    if(tag == 23 || tag == 24){
    	    	player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Das MineTime-Team wünscht dir frohe Weihnachten! :)");
    	    }
    	    
    	    MySQL.Update("INSERT INTO `mt_advent` (`day`, `name`) VALUES ('" + tag + "', '" + player.getName() + "');");
    	    return;
    	}
	    

	    if (block.getType() == Material.DIAMOND_ORE){
	    	Block rela = block.getRelative(event.getBlockFace());
		    int light = (rela.isEmpty() ? Math.round(((rela.getLightLevel()) & 0xFF) * 100) / 15 : 15);

		    if (player.getGameMode() != GameMode.CREATIVE && !MTSData.foundblocks.contains(block.getLocation())) {
			MTSData.foundblocks.add(block.getLocation());
			Thread notify = new Notify(player, block, light);
			notify.start();
		    }
	    }

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
	if (!event.isCancelled()) {

	    if (event.getCause() == TeleportCause.ENDER_PEARL) {
		event.getPlayer().sendMessage(msg("enderperle"));
		event.setTo(event.getFrom());
		event.setCancelled(true);
	    }

	}
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onPlayerJoin(PlayerJoinEvent e) {

	PermissionManager pex = PermissionsEx.getPermissionManager();
	if (!pex.has(e.getPlayer(), "MTS.gamemode"))
	    if (!e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
		e.getPlayer().setGameMode(GameMode.SURVIVAL);
	    }

	if (e.getPlayer().getName().length() < 15) {
	    if (pex.has(e.getPlayer(), "admin")) {
		e.getPlayer().setPlayerListName(ChatColor.RED + e.getPlayer().getName());
	    } else if (pex.has(e.getPlayer(), "mod"))
		e.getPlayer().setPlayerListName(ChatColor.DARK_PURPLE + e.getPlayer().getName());
	    else if (pex.has(e.getPlayer(), "supporter"))
		e.getPlayer().setPlayerListName(ChatColor.GREEN + e.getPlayer().getName());
	    else if (pex.has(e.getPlayer(), "cs"))
		e.getPlayer().setPlayerListName(ChatColor.DARK_GREEN + e.getPlayer().getName());
	}

	final IUser user = new User(e.getPlayer().getName());
	String msg = new Config().getString("JoinMSG.Nachricht");

	msg = msg.replace("#0", ChatColor.BLACK + "");
	msg = msg.replace("#1", ChatColor.DARK_BLUE + "");
	msg = msg.replace("#2", ChatColor.DARK_GREEN + "");
	msg = msg.replace("#3", ChatColor.DARK_AQUA + "");
	msg = msg.replace("#4", ChatColor.DARK_RED + "");
	msg = msg.replace("#5", ChatColor.DARK_PURPLE + "");
	msg = msg.replace("#6", ChatColor.GOLD + "");
	msg = msg.replace("#7", ChatColor.GRAY + "");
	msg = msg.replace("#8", ChatColor.DARK_GRAY + "");
	msg = msg.replace("#9", ChatColor.BLUE + "");
	msg = msg.replace("#a", ChatColor.GREEN + "");
	msg = msg.replace("#b", ChatColor.AQUA + "");
	msg = msg.replace("#c", ChatColor.RED + "");
	msg = msg.replace("#d", ChatColor.LIGHT_PURPLE + "");
	msg = msg.replace("#e", ChatColor.YELLOW + "");
	msg = msg.replace("#f", ChatColor.WHITE + "");
	msg = msg.replace("#l", ChatColor.BOLD + "");
	msg = msg.replace("#i", ChatColor.ITALIC + "");
	msg = msg.replace("#r", ChatColor.RESET + "");
	msg = msg.replace("#u", ChatColor.UNDERLINE + "");
	msg = msg.replace("#k", ChatColor.MAGIC + "");
	msg = msg.replace("-name-", e.getPlayer().getName());

	if (user.hasAccount()) {
	    e.getPlayer().sendMessage(msg);
	    MTSData.TBL_friede.put(e.getPlayer().getName(), user.loadFriede());
	    if (pex.has(e.getPlayer(), "MTS.chatcolor")) {
		try {
		    ResultSet rs = MySQL.Query("SELECT `head` FROM mt_chatheads WHERE name='" + e.getPlayer().getName()
			    + "';");

		    while (rs.next()) {
			if (rs.getString(1) != null)
			    MTSData.chatColors.put(e.getPlayer().getName(), rs.getString(1));
		    }
		    rs.close();
		} catch (Exception err) {
		    System.out.println("MTS-Fehler: " + err);
		}
	    }
	    try {
		ResultSet rs = MySQL.Query("SELECT `time` FROM `mt_newusers` WHERE name='" + e.getPlayer().getName() + "';");

		while (rs.next()) {
		    if (rs.getString(1) != null)
			MTSData.TBL_neuespieler.put(e.getPlayer().getName(), rs.getString(1));
		}
		rs.close();
	    } catch (Exception err) {
		System.out.println("MTS-Fehler: " + err);
	    }
	} else {
	    List<String> a = new ArrayList<String>();
	    MTSData.TBL_friede.put(e.getPlayer().getName(), a);
	    user.createAccount();
	    e.getPlayer().sendMessage(ChatColor.AQUA + "[]--- Willkommen auf MineTime ---[]");
	    /*
	    // MineTime 5.0 neuerungen für neue spieler
	    user.setChatroom("neuespieler");
	    
	    /*
	    NewPlayerGuide npg = new NewPlayerGuide(user);
	    npg.go();
	    */
	}

	if (user.isInClan()) {
	    for (IUser u : user.getClan().getClanMembers()) {
		if (u.isOnline() && !u.getName().equals(user.getName())) {
		    u.getPlayer().sendMessage(
			    ChatColor.GOLD + "" + ChatColor.BOLD + "[CLAN] " + ChatColor.DARK_GRAY + e.getPlayer().getName()
				    + ChatColor.GRAY + " ist nun online!");
		}
	    }
	}
	e.setJoinMessage(null);

	Date myDate = new Date();
	SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
	df.setTimeZone(TimeZone.getDefault());
	df.format(myDate);
	Calendar gc = new GregorianCalendar();
	gc.setTime(myDate);
	Date now = gc.getTime();

	MySQL.Update("INSERT INTO `mt_onlineusers` (name, since) VALUES('" + e.getPlayer().getName() + "', '"
		+ df.format(now) + "');");

	// Login eintragen
	MTSData.lastLogin.put(e.getPlayer().getName(), (int) (System.currentTimeMillis() / 60000L)); // In
												     // Minuten

	// Login-Zeit in List eintragen
	if (user.isMarried())
	    MTSData.marriedChatTags.add(user.getName());

	// Username auf cases korrigieren
	try {
	    ResultSet rs = MySQL.Query("SELECT `id` FROM `mt_stats` WHERE `cases`='0' AND `name`='"
		    + user.getName().toLowerCase() + "';");

	    while (rs.next()) {
		MySQL.Update("UPDATE `mt_stats` SET `cases`='1', `name`='" + user.getName() + "' WHERE `name`='"
			+ user.getName().toLowerCase() + "';");
		MySQL.Update("UPDATE `mt_money` SET `name`='" + user.getName() + "' WHERE `name`='"
			+ user.getName().toLowerCase() + "';");
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}
    }

    @EventHandler
    public void onNameTag(PlayerReceiveNameTagEvent event) {
	PermissionManager pex = PermissionsEx.getPermissionManager();

	ChatColor c = ChatColor.WHITE;

	for (PermissionGroup pg : pex.getUser(event.getNamedPlayer()).getGroups()) {
	    if (pg.getName().equalsIgnoreCase("Owner"))
		c = ChatColor.DARK_RED;
	    if (pg.getName().equalsIgnoreCase("Sponsor"))
		c = ChatColor.DARK_RED;
	    if (pg.getName().equalsIgnoreCase("Payment"))
		c = ChatColor.DARK_PURPLE;
	    if (pg.getName().equalsIgnoreCase("Dev"))
		c = ChatColor.DARK_AQUA;
	    if (pg.getName().equalsIgnoreCase("Admin"))
		c = ChatColor.RED;
	    if (pg.getName().equalsIgnoreCase("Mod"))
		c = ChatColor.DARK_PURPLE;
	    if (pg.getName().equalsIgnoreCase("Supporter"))
		c = ChatColor.GREEN;
	    if (pg.getName().equalsIgnoreCase("CS"))
		c = ChatColor.DARK_GREEN;

	}

	if (c != ChatColor.WHITE) {
	    event.setTag(c + event.getNamedPlayer().getName());
	}
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onPlayerQuit(PlayerQuitEvent e) throws Exception {

	if (MTSData.support.containsKey(e.getPlayer().getName())) {
	    Bukkit.getServer().getPlayer(MTSData.support.get(e.getPlayer().getName()))
		    .sendMessage(ChatColor.RED + "Dein Supporter ist offline gegangen, du bist wieder im normalem Chat!");
	    MTSData.support.remove(e.getPlayer().getName());
	} else if (MTSData.support.containsValue(e.getPlayer().getName())) {
	    String name = "";
	    for (Entry<String, String> entry : MTSData.support.entrySet()) {
		if (e.getPlayer().getName().equals(entry.getValue())) {
		    name = entry.getKey();
		}
	    }
	    Bukkit.getServer().getPlayer(name)
		    .sendMessage(ChatColor.RED + "Der User ist offline gegangen, du bist wieder im normalem Chat!");
	    MTSData.support.remove(name);
	}

	if (me.minetime.MTS.commands.Commandevent.players.contains(e.getPlayer().getName())) {
	    me.minetime.MTS.commands.Commandevent.players.remove(e.getPlayer().getName());

	    Bukkit.broadcastMessage(ChatColor.GOLD + "[Event]" + ChatColor.AQUA + e.getPlayer().getName()
		    + " hat das Event verlassen.");
	}

	// if(e.getPlayer() == me.minetime.MTS.arena.Commands.p1 ||
	// e.getPlayer() == me.minetime.MTS.arena.Commands.p2){
	// me.minetime.MTS.arena.Commands.p1.teleport(new
	// Location(e.getPlayer().getWorld(), 499829, 74.5, -500137.5));
	// me.minetime.MTS.arena.Commands.p2.teleport(new
	// Location(e.getPlayer().getWorld(), 499829, 74.5, -500137.5));
	//
	// me.minetime.MTS.arena.Commands.p1.getInventory().setContents(me.minetime.MTS.arena.Commands.inv1);
	// me.minetime.MTS.arena.Commands.p2.getInventory().setContents(me.minetime.MTS.arena.Commands.inv2);
	//
	// me.minetime.MTS.arena.Commands.p1.getInventory().setArmorContents(me.minetime.MTS.arena.Commands.armor1);
	// me.minetime.MTS.arena.Commands.p2.getInventory().setArmorContents(me.minetime.MTS.arena.Commands.armor2);
	//
	// me.minetime.MTS.arena.Commands.stat = false;
	// me.minetime.MTS.arena.Commands.p1 = null;
	// me.minetime.MTS.arena.Commands.p2 = null;
	//
	// me.minetime.MTS.arena.Commands.inv1 = null;
	// me.minetime.MTS.arena.Commands.inv2 = null;
	//
	// me.minetime.MTS.arena.Commands.armor1 = null;
	// me.minetime.MTS.arena.Commands.armor2 = null;
	//
	// }

	Player player = e.getPlayer();
	WorldGuardPlugin worldGuard = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
	if (worldGuard == null)
	    throw new Exception("Worldguard not found!");

	Location pt = player.getLocation();
	LocalPlayer localPlayer = worldGuard.wrapPlayer(player);
	World world = player.getWorld();
	RegionManager regionManager = worldGuard.getRegionManager(world);
	ApplicableRegionSet set = regionManager.getApplicableRegions(pt);
	if (!set.allows(DefaultFlag.PVP, localPlayer))
	    MTSData.authToLogout.add(e.getPlayer().getName());

	if (MTSData.authToLogout.contains(e.getPlayer().getName()))
	    MTSData.authToLogout.remove(e.getPlayer().getName());
	else {
	    ItemStack[] armorContents = player.getInventory().getArmorContents();
	    for (ItemStack content : armorContents) {
		if (content.getAmount() != 0) {
		    player.getWorld().dropItemNaturally(player.getLocation(), content);
		    System.out.println(content.getTypeId() + " " + content.getEnchantments().toString() + " wurde von "
			    + player.getName() + " gedroppert!");
		}

	    }

	    player.getInventory().setArmorContents(new ItemStack[4]);
	}

	e.getPlayer().setPlayerListName(null);
	e.setQuitMessage(null);
	User user = new User(e.getPlayer().getName());
	if (user.isInClan())
	    user.getClan().sendClanMessage(
		    ChatColor.GOLD + "" + ChatColor.BOLD + "[CLAN] " + ChatColor.DARK_GRAY + e.getPlayer().getName()
			    + ChatColor.GRAY + " ist nun offline!");

	MySQL.Update("DELETE FROM `mt_onlineusers` WHERE `name`='" + e.getPlayer().getName() + "';");
	if (MTSData.TBL_friede.containsKey(e.getPlayer().getName()))
	    MTSData.TBL_friede.remove(e.getPlayer().getName());
	if (MTSData.TBL_neuespieler.containsKey(e.getPlayer().getName()))
	    MTSData.TBL_neuespieler.remove(e.getPlayer().getName());

	// Rechne differenz aus
	int timeToAdd = (int) (System.currentTimeMillis() / 60000L)
		- Integer.valueOf(MTSData.lastLogin.get(player.getName()));

	// Zeit in DB eintragen
	new User(player.getName()).addPlaytime(timeToAdd);
	MTSData.lastLogin.remove(player.getName());

	if (MTSData.marriedChatTags.contains(user.getName())) {
	    MTSData.marriedChatTags.remove(user.getName());
	}

	User o = new User(e.getPlayer().getName());

	if (me.minetime.MTS.commands.Command1on1.status == true) {
	    if (me.minetime.MTS.commands.Command1on1.u1.equalsIgnoreCase(o.getName())) {
		Bukkit.broadcastMessage(ChatColor.GOLD + "[1on1] " + ChatColor.GREEN
			+ me.minetime.MTS.commands.Command1on1.u2 + ChatColor.AQUA + " hat gegen " + ChatColor.RED
			+ o.getName() + ChatColor.AQUA + " gewonnen!");
		me.minetime.MTS.commands.Command1on1.close1on1();
	    } else {
		if (me.minetime.MTS.commands.Command1on1.u2.equalsIgnoreCase(o.getName())) {
		    Bukkit.broadcastMessage(ChatColor.GOLD + "[1on1] " + ChatColor.GREEN
			    + me.minetime.MTS.commands.Command1on1.u1 + ChatColor.AQUA + " hat gegen "
			    + ChatColor.RED + o.getName() + ChatColor.AQUA + " gewonnen!");
		    me.minetime.MTS.commands.Command1on1.close1on1();
		}
	    }

	}
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onPlayerKick(PlayerKickEvent e) {

	if (MTSData.support.containsKey(e.getPlayer().getName())) {
	    Bukkit.getServer().getPlayer(MTSData.support.get(e.getPlayer().getName()))
		    .sendMessage(ChatColor.RED + "Dein Supporter wurde gekickt gegangen, du bist wieder im normalem Chat!");
	    MTSData.support.remove(e.getPlayer().getName());
	} else if (MTSData.support.containsValue(e.getPlayer().getName())) {
	    String name = "";
	    for (Entry<String, String> entry : MTSData.support.entrySet()) {
		if (e.getPlayer().getName().equals(entry.getValue())) {
		    name = entry.getKey();
		}
	    }
	    Bukkit.getServer().getPlayer(name)
		    .sendMessage(ChatColor.RED + "Der User wurde gekickt, du bist wieder im normalem Chat!");
	    MTSData.support.remove(name);
	}
	e.getPlayer().setPlayerListName(null);
	e.setLeaveMessage(null);
	MySQL.Update("DELETE FROM `mt_onlineusers` WHERE `name`='" + e.getPlayer().getName() + "';");
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
	// if(e.getPlayer() == me.minetime.MTS.arena.Commands.p1 ||
	// e.getPlayer() == me.minetime.MTS.arena.Commands.p2){
	// if(!e.getPlayer().isOp()){
	// e.setCancelled(true);
	// e.getPlayer().sendMessage(ChatColor.RED + "Jetzt nicht!");
	// }
	// }

	for (String entry : MTS.spy) {
	    Player p = Bukkit.getServer().getPlayer(entry);
	    if (p != null)
		p.sendMessage(ChatColor.GRAY + e.getPlayer().getName() + ": " + e.getMessage());

	}
	if (MTSData.commandmute) {
	    PermissionManager pex = PermissionsEx.getPermissionManager();
	    if (pex.has(e.getPlayer(), "MTS.commandmute") == false) {
		e.setCancelled(true);
		e.getPlayer().sendMessage(
			ChatColor.RED + "Du kannst keine Befehle ausfï¿½hren, wenn Commandmute aktiviert ist!");
	    }
	}

	if (e.getMessage().equalsIgnoreCase("/stop")) {
	    if (e.getPlayer().isOp()) {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "say Serverneustart/stop!");
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "save-all");
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "logoutall Serverneustart/Stopp!");
	    }
	}
	e.setMessage(e.getMessage().replace("'", ""));

	/*
	 * if(e.getMessage().length() >= 3){ if(e.getMessage().substring(0,
	 * 3).equalsIgnoreCase("/m ")){ if(new
	 * User(e.getPlayer().getName()).isMuted()){ e.setCancelled(true);
	 * e.getPlayer().sendMessage(msg("muteMessage")); } } }
	 * 
	 * if(e.getMessage().length() >= 5){ if(e.getMessage().substring(0,
	 * 5).equalsIgnoreCase("/msg ")){ if(new
	 * User(e.getPlayer().getName()).isMuted()){ e.setCancelled(true);
	 * e.getPlayer().sendMessage(msg("muteMessage")); } } }
	 * 
	 * if(e.getMessage().length() >= 6){ if(e.getMessage().substring(0,
	 * 6).equalsIgnoreCase("/tell ")){ if(new
	 * User(e.getPlayer().getName()).isMuted()){ e.setCancelled(true);
	 * e.getPlayer().sendMessage(msg("muteMessage")); } } }
	 */

    }

    //
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onPlayerChat(AsyncPlayerChatEvent e) {

	PermissionManager pex = PermissionsEx.getPermissionManager();

	User user = new User(e.getPlayer().getName());

	e.setMessage(e.getMessage().replace("'", ""));

	e.setCancelled(true);
	// interner Ausfuehrungsboolean
	boolean isCancelled = false;

	/*
	 * if (isCancelled == false) {
	 * 
	 * if(new User(e.getPlayer().getName()).isMuted()){
	 * e.setCancelled(true); e.getPlayer().sendMessage(msg("muteMessage"));
	 * isCancelled = true; } }
	 */

	if (isCancelled == false) {
	    if (e.getMessage().substring(0, 1).equals("%")) {
		isCancelled = true;
		if (user.isInClan())
		    user.getClan().sendClanMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[CLAN] " + ChatColor.DARK_GRAY + user.getName() + ": " + ChatColor.GRAY + e.getMessage().substring(1));
	    }
	}

	if (isCancelled == false) {

	    if (e.getMessage().substring(0, 1).equals("&")) {
		isCancelled = true;
		if (user.isInClan()) {
		    if (user.getClan().getClanAlliances().size() != 0) {
			user.getClan().sendClanMessage(
				ChatColor.GOLD + "" + ChatColor.BOLD + "[ALLIANZ] " + ChatColor.GRAY
					+ user.getClan().getuncolouredClanTag() + ChatColor.DARK_GRAY + "*"
					+ user.getName() + ": " + ChatColor.GRAY + e.getMessage().substring(1));

			for (IClan c : user.getClan().getClanAlliances()) {
			    c.sendClanMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[ALLIANZ] " + ChatColor.GRAY
				    + user.getClan().getuncolouredClanTag() + ChatColor.DARK_GRAY + "*"
				    + ChatColor.DARK_GRAY + user.getName() + ": " + ChatColor.GRAY
				    + e.getMessage().substring(1));
			}
		    } else {
			e.getPlayer().sendMessage(ChatColor.RED + "Fehler: Dein Clan besitzt keine Allianzen.");
		    }
		} else {
		    e.getPlayer().sendMessage(ChatColor.RED + "Fehler: Du bist in keinem Clan.");
		}
	    }

	}

	if (isCancelled == false) {
	    if (!pex.has(e.getPlayer(), "MTS.caps")) {
		int spacecount = 0;
		boolean msgbool = false;
		String[] msg = e.getMessage().split(" ");
		for (int i = 0; i < msg.length; i++) {
		    if ((msg[i].length() == 1) && (isMostUpper2(msg[i]))) {
			spacecount++;
		    }
		    if ((!isMostUpper(msg[i])) && (spacecount <= 2))
			continue;
		    msg[i] = msg[i].toLowerCase();
		    if (msgbool)
			continue;
		    e.getPlayer().sendMessage(ChatColor.DARK_AQUA + "Caps ist b\u00F6se!");
		    msgbool = true;
		}

		String newmsg = "";
		for (int i = 0; i < msg.length; i++) {
		    newmsg = newmsg + msg[i];
		    newmsg = newmsg + " ";
		}
		e.setMessage(newmsg);
		msgbool = false;
	    }

	}

	if (isCancelled == false) {
	    if (MTSData.support.containsKey(e.getPlayer().getName())) {
		Player pl1 = e.getPlayer();
		Player pl2 = Bukkit.getServer().getPlayer(MTSData.support.get(e.getPlayer().getName()));
		pl1.sendMessage(ChatColor.GOLD + e.getPlayer().getName() + ": " + ChatColor.RED + e.getMessage());
		pl2.sendMessage(ChatColor.GOLD + e.getPlayer().getName() + ": " + ChatColor.RED + e.getMessage());
		isCancelled = true;
	    } else if (MTSData.support.containsValue(e.getPlayer().getName())) {
		String name = "";
		for (Entry<String, String> entry : MTSData.support.entrySet()) {
		    if (e.getPlayer().getName().equals(entry.getValue())) {
			name = entry.getKey();
		    }
		}
		Player pl1 = e.getPlayer();
		Player pl2 = Bukkit.getServer().getPlayer(name);
		pl1.sendMessage(ChatColor.GRAY + e.getPlayer().getName() + ": " + ChatColor.WHITE + e.getMessage());
		pl2.sendMessage(ChatColor.GRAY + e.getPlayer().getName() + ": " + ChatColor.WHITE + e.getMessage());
		isCancelled = true;
	    }
	}
	if (isCancelled == false) {
	    if (MTSData.globalmute) {
		if (pex.has(e.getPlayer(), "MTS.globalmute") == false) {
		    isCancelled = true;
		    e.getPlayer().sendMessage(msg("globalmute"));
		}
	    }

	}

	if (isCancelled == false) {
	    if (e.getMessage().substring(0, 1).equals("7")) {
		e.getPlayer().sendMessage(msg("chat7"));
		isCancelled = true;
	    }
	}
	// if (e.getMessage().contains("lag") ||
	// e.getMessage().contains("laag")) {
	// isCancelled = true;
	// e.getPlayer().sendMessage(msg("lag"));
	// }

	if (!isCancelled) {
	    if (!pex.has(e.getPlayer(), "MTS.Spam")) {
		if (!MTSData.antispam.containsKey(e.getPlayer().getName())) {
		    MTSData.antispam.put(e.getPlayer().getName(), e.getMessage());
		} else {
		    if (MTSData.antispam.get(e.getPlayer().getName()).equalsIgnoreCase(e.getMessage())) {
			e.getPlayer().sendMessage(msg("spam"));
			isCancelled = true;
		    } else {
			MTSData.antispam.remove(e.getPlayer().getName());
			MTSData.antispam.put(e.getPlayer().getName(), e.getMessage());
		    }
		}
	    }
	}

	if (isCancelled == false) {

	    Player player = e.getPlayer();
	    String msg = e.getMessage();
	    if (MTSData.chatColors.containsKey(e.getPlayer().getName())) {
		msg = MTSData.chatColors.get(e.getPlayer().getName()) + msg;
	    }

	    if (player.isOp()) {
		if (msg.length() >= 2){
			if(msg.substring(0, 2).equalsIgnoreCase("#z")) {
				String allowedChars = "23456789abcdef";

				char[] c = msg.substring(1).toCharArray();

				msg = "";

				for (char t : c) {
					msg = msg + "#" + generateRandomString(allowedChars, new Random(), 1) + t;
		    	}
			}
		}
	    }

	    if (pex.has(player, "MTS.Chatcolor")) {
	    	if (msg.length() >= 2){
	    	if(msg.substring(0, 2).equalsIgnoreCase("#0")) msg = ChatColor.BLACK + msg.substring(2);
	    	if(msg.substring(0, 2).equalsIgnoreCase("#1")) msg = ChatColor.DARK_BLUE + msg.substring(2);
	    	if(msg.substring(0, 2).equalsIgnoreCase("#2")) msg = ChatColor.DARK_GREEN + msg.substring(2);
	    	if(msg.substring(0, 2).equalsIgnoreCase("#3")) msg = ChatColor.DARK_AQUA + msg.substring(2);
	    	if(msg.substring(0, 2).equalsIgnoreCase("#5")) msg = ChatColor.DARK_PURPLE + msg.substring(2);
	    	if(msg.substring(0, 2).equalsIgnoreCase("#6")) msg = ChatColor.GOLD + msg.substring(2);
	    	if(msg.substring(0, 2).equalsIgnoreCase("#7")) msg = ChatColor.GRAY + msg.substring(2);
	    	if(msg.substring(0, 2).equalsIgnoreCase("#8")) msg = ChatColor.DARK_GRAY + msg.substring(2);
	    	if(msg.substring(0, 2).equalsIgnoreCase("#9")) msg = ChatColor.BLUE + msg.substring(2);
	    	if(msg.substring(0, 2).equalsIgnoreCase("#a")) msg = ChatColor.GREEN + msg.substring(2);
	    	if(msg.substring(0, 2).equalsIgnoreCase("#b")) msg = ChatColor.AQUA + msg.substring(2);
	    	if(msg.substring(0, 2).equalsIgnoreCase("#e")) msg = ChatColor.YELLOW + msg.substring(2);
	    	if(msg.substring(0, 2).equalsIgnoreCase("#f")) msg = ChatColor.WHITE+ msg.substring(2);
	    	}
	    }

	    if (pex.has(player, "MTS.ChatcolorTeam")) {
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
	    }

	    // prefix
	    String prefix = pex.getUser(player.getName()).getPrefix();
	    if (prefix != null) {
		prefix = prefix.replace("&0", ChatColor.BLACK + "");
		prefix = prefix.replace("&1", ChatColor.DARK_BLUE + "");
		prefix = prefix.replace("&2", ChatColor.DARK_GREEN + "");
		prefix = prefix.replace("&3", ChatColor.DARK_AQUA + "");
		prefix = prefix.replace("&4", ChatColor.DARK_RED + "");
		prefix = prefix.replace("&5", ChatColor.DARK_PURPLE + "");
		prefix = prefix.replace("&6", ChatColor.GOLD + "");
		prefix = prefix.replace("&7", ChatColor.GRAY + "");
		prefix = prefix.replace("&8", ChatColor.DARK_GRAY + "");
		prefix = prefix.replace("&9", ChatColor.BLUE + "");
		prefix = prefix.replace("&a", ChatColor.GREEN + "");
		prefix = prefix.replace("&b", ChatColor.AQUA + "");
		prefix = prefix.replace("&c", ChatColor.RED + "");
		prefix = prefix.replace("&e", ChatColor.YELLOW + "");
		prefix = prefix.replace("&f", ChatColor.WHITE + "");

	    } else
		prefix = "";
	    String clantag = "";
	    if (user.isInClan())
		clantag = user.getClan().getcolouredClanTag() + ChatColor.GRAY + "*";

	    String marriedTag = "";
	    if (MTSData.marriedChatTags.contains(user.getName())) {
		marriedTag = ChatColor.LIGHT_PURPLE + "\u2764";
	    }

	    String mesg = marriedTag + prefix + " " + clantag + ChatColor.WHITE + user.getName() + ChatColor.GRAY + ": " + ChatColor.WHITE + msg;
	    
	    if(user.getChatroom().equalsIgnoreCase("GLOBAL")){
	    	MTSData.sendChatroomMessage("GLOBAL", mesg);
	    }
	    else
	    {
	    	//Do nothing
	    }
	}

    }

    private String generateRandomString(String allowedChars, Random random, Integer length) {
	int max = allowedChars.length();
	StringBuffer buffer = new StringBuffer();

	for (int i = 0; i < length; i++) {
	    int value = random.nextInt(max);
	    buffer.append(allowedChars.charAt(value));
	}

	return buffer.toString();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerLogin(PlayerLoginEvent e) {
	int bantype = 0;
	String reason = "";
	String time = "";
	try {
	    ResultSet rs = MySQL.Query("SELECT `reason`,`time` FROM `mt_timebans` WHERE `name`='" + e.getPlayer().getName()
		    + "';");

	    while (rs.next()) {
		// Zeit aus Datenbank in Zeitstempel umwandeln
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = df.parse(rs.getString(2));
		Calendar gc = new GregorianCalendar();
		gc.setTime(d);
		Date d2 = gc.getTime();

		// Jetzige Zeit definieren
		Date myDate = new Date();
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df2.setTimeZone(TimeZone.getDefault());
		df2.format(myDate);
		Calendar gc2 = new GregorianCalendar();
		gc2.setTime(myDate);
		Date now = gc2.getTime();

		// Falls Jetzt hinter dem Datum aus der DB liegt
		if (!now.after(d2)) {
		    bantype = 2;
		    reason = rs.getString(1);
		    time = rs.getString(2);
		} else
		    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
			    "delsperre " + e.getPlayer().getName());
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}

	try {
	    ResultSet rs = MySQL.Query("SELECT `reason` FROM `mt_bans` WHERE `name`='" + e.getPlayer().getName() + "';");

	    while (rs.next()) {
		bantype = 1;
		reason = rs.getString(1);
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}

	try {
	    ResultSet rs = MySQL.Query("SELECT `reason` FROM `mt_globalbans` WHERE `name`='" + e.getPlayer().getName()
		    + "';");

	    while (rs.next()) {
		bantype = 3;
		reason = rs.getString(1);
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}

	if (bantype == 1) {
	    e.setKickMessage(ChatColor.RED + "Du hast eine permanent-Sperre. Grund:" + ChatColor.BLUE + reason);
	    e.disallow(PlayerLoginEvent.Result.KICK_BANNED, e.getKickMessage());
	} else if (bantype == 2) {
	    e.setKickMessage(ChatColor.RED + "Du hast eine Zeitsperre bis " + ChatColor.GOLD + time + ChatColor.RED
		    + " Grund:" + ChatColor.BLUE + reason);
	    e.disallow(PlayerLoginEvent.Result.KICK_BANNED, e.getKickMessage());

	} else if (bantype == 3) {
	    e.setKickMessage(ChatColor.RED + "Du wurdest " + ChatColor.DARK_RED + "global vom MineTime-Netzwerk "
		    + ChatColor.RED + "gebannt. Grund:" + ChatColor.BLUE + reason);
	    e.disallow(PlayerLoginEvent.Result.KICK_BANNED, e.getKickMessage());
	}
	Config mts_config = new Config();
	boolean whitelisted = false;
	String nachricht = ChatColor.RED + "Du bist nicht auf der Whitelist.";

	if (mts_config.getBoolean("Whitelist.Enabled")) {
	    nachricht = mts_config.getString("Whitelist.Nachricht");
	    if (Bukkit.getWhitelistedPlayers().contains(Bukkit.getOfflinePlayer(e.getPlayer().getDisplayName())))
		whitelisted = true;
	    else
		whitelisted = false;

	    if (!whitelisted) {
		e.setKickMessage(nachricht);
		e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, e.getKickMessage());
	    }
	}
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) throws NoPermissionException { 
    	Player p = Bukkit.getPlayer(event.getWhoClicked().getName());
    	
        if (!p.isOp())
        {
          if (event.getInventory().getName().equalsIgnoreCase("Repair"))
          {
        	  if((event.getCurrentItem().getAmount() <= 1)){
        		  
        	  } else {
        		  event.setCancelled(true);
        		  return;
        	  }
          }
        }
      }
    
    /*
     * private static String generateRandomString(String allowedChars, Random
     * random, Integer length) { int max = allowedChars.length(); StringBuffer
     * buffer = new StringBuffer();
     * 
     * for (int i=0; i<length; i++) { int value = random.nextInt(max);
     * buffer.append(allowedChars.charAt(value)); }
     * 
     * return buffer.toString(); }
     */

    private static boolean isMostUpper(String s) {
	int capcount = 0;
	for (char c : s.toCharArray()) {
	    if ((!Character.isLetter(c)) || (!Character.isUpperCase(c)))
		continue;
	    capcount++;
	}

	return (capcount > s.length() / 2) && (s.length() != 1);
    }

    private static boolean isMostUpper2(String s) {
	for (char c : s.toCharArray()) {
	    if ((Character.isLetter(c)) && (Character.isUpperCase(c))) {
		return true;
	    }
	}
	return false;
    }

}
