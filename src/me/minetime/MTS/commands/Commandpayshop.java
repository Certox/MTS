package me.minetime.MTS.commands;

import static me.minetime.MTS.clazz.Message.msg;

import java.util.HashMap;

import me.minetime.MTS.clazz.CommandMT;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Commandpayshop extends CommandMT {

    @Override
    public void run(final CommandSender sender, final String commandLabel, final String[] args) throws Exception {

	if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
	    sender.sendMessage(ChatColor.AQUA + "R\u00e4nge:" + ChatColor.GOLD + " Vip | Pro | Elite | Executive");
	    sender.sendMessage(ChatColor.AQUA + "Items: " + ChatColor.GOLD
		    + " fullbow | fullarmor | fullsword | fullpickaxe");

	} else if (args.length != 2) {
	    sender.sendMessage(ChatColor.RED + "Verwendung:" + ChatColor.AQUA + " /payshop <name> [item/rang]");

	} else if (Bukkit.getServer().getPlayer(args[0]) == null)
	    throw new Exception("Der Spieler ist nicht online!");

	Player vic = Bukkit.getServer().getPlayer(args[0]);
	if (args[1].equalsIgnoreCase("Vip")) {
	    HashMap<Integer, ItemStack> hm = new HashMap<Integer, ItemStack>();
	    ItemStack[] is = new ItemStack[3];
	    is[0] = new ItemStack(384, 64);
	    is[1] = new ItemStack(7, 32);
	    is[2] = new ItemStack(276, 64);

	    hm.putAll(vic.getInventory().addItem(is[0]));
	    if (hm.size() > 0) {

		vic.getInventory().removeItem(new ItemStack(384, 64 - is[0].getAmount()));

		throw new Exception("Der Spieler hat nicht genug Platz im Inventar!");
	    }

	    hm.putAll(vic.getInventory().addItem(is[1]));
	    if (hm.size() > 0) {
		vic.getInventory().removeItem(is[0]);
		vic.getInventory().removeItem(new ItemStack(7, 32 - is[1].getAmount()));
		throw new Exception("Der Spieler hat nciht genug Platz im Inventar!");

	    }
	    hm.putAll(vic.getInventory().addItem(is[2]));
	    if (hm.size() > 0) {
		vic.getInventory().removeItem(is[0]);
		vic.getInventory().removeItem(is[1]);
		vic.getInventory().removeItem(new ItemStack(276, 64 - is[2].getAmount()));
		throw new Exception("Der Spieler hat nciht genug Platz im Inventar!");

	    }

	    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
		    "pex user " + args[0] + " group set vip");
	    sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + args[0] + " ist nun VIP");
	    vic.sendMessage(msg("payShopRang", 1, "VIP"));

	} else if (args[1].equalsIgnoreCase("Pro")) {
	    HashMap<Integer, ItemStack> hm = new HashMap<Integer, ItemStack>();
	    ItemStack[] is = new ItemStack[3];
	    is[0] = new ItemStack(384, 128);
	    is[1] = new ItemStack(7, 64);
	    is[2] = new ItemStack(276, 128);

	    hm.putAll(vic.getInventory().addItem(is[0]));
	    if (hm.size() > 0) {

		vic.getInventory().removeItem(new ItemStack(384, 128 - is[0].getAmount()));
		throw new Exception("Der Spieler hat nicht genug Platz im Inventar!");

	    }

	    hm.putAll(vic.getInventory().addItem(is[1]));
	    if (hm.size() > 0) {
		vic.getInventory().removeItem(is[0]);
		vic.getInventory().removeItem(new ItemStack(7, 64 - is[1].getAmount()));
		throw new Exception("Der Spieler hat nicht genug Platz im Inventar!");

	    }
	    hm.putAll(vic.getInventory().addItem(is[2]));
	    if (hm.size() > 0) {
		vic.getInventory().removeItem(is[0]);
		vic.getInventory().removeItem(is[1]);
		vic.getInventory().removeItem(new ItemStack(276, 128 - is[2].getAmount()));
		throw new Exception("Der Spieler hat nicht genug Platz im Inventar!");

	    }
	    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
		    "pex user " + args[0] + " group set pro");
	    sender.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + args[0] + " ist nun PRO");
	    vic.sendMessage(msg("payShopRang", 7, "PRO"));

	} else if (args[1].equalsIgnoreCase("elite")) {
	    HashMap<Integer, ItemStack> hm = new HashMap<Integer, ItemStack>();
	    ItemStack[] is = new ItemStack[3];
	    is[0] = new ItemStack(384, 192);
	    is[1] = new ItemStack(7, 128);
	    is[2] = new ItemStack(276, 192);

	    hm.putAll(vic.getInventory().addItem(is[0]));
	    if (hm.size() > 0) {

		vic.getInventory().removeItem(new ItemStack(384, 192 - is[0].getAmount()));
		throw new Exception("Der Spieler hat nicht genug Platz im Inventar!");
	    }

	    hm.putAll(vic.getInventory().addItem(is[1]));
	    if (hm.size() > 0) {
		vic.getInventory().removeItem(is[0]);
		vic.getInventory().removeItem(new ItemStack(7, 128 - is[1].getAmount()));
		throw new Exception("Der Spieler hat nicht genug Platz im Inventar!");
	    }
	    hm.putAll(vic.getInventory().addItem(is[2]));
	    if (hm.size() > 0) {
		vic.getInventory().removeItem(is[0]);
		vic.getInventory().removeItem(is[1]);
		vic.getInventory().removeItem(new ItemStack(276, 256 - is[2].getAmount()));
		throw new Exception("Der Spieler hat nicht genug Platz im Inventar!");
	    }

	    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
		    "pex user " + args[0] + " group set elite");
	    sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + args[0] + " ist nun ELITE");
	    vic.sendMessage(msg("payShopRang", "e", "ELITE"));

	}

	else if (args[1].equalsIgnoreCase("executive")) {
	    HashMap<Integer, ItemStack> hm = new HashMap<Integer, ItemStack>();
	    ItemStack[] is = new ItemStack[3];
	    is[0] = new ItemStack(384, 192);
	    is[1] = new ItemStack(7, 256);
	    is[2] = new ItemStack(276, 512);

	    hm.putAll(vic.getInventory().addItem(is[0]));
	    if (hm.size() > 0) {

		vic.getInventory().removeItem(new ItemStack(384, 192 - is[0].getAmount()));
		throw new Exception("Der Spieler hat nicht genug Platz im Inventar!");
	    }

	    hm.putAll(vic.getInventory().addItem(is[1]));
	    if (hm.size() > 0) {
		vic.getInventory().removeItem(is[0]);
		vic.getInventory().removeItem(new ItemStack(7, 256 - is[1].getAmount()));
		throw new Exception("Der Spieler hat nicht genug Platz im Inventar!");
	    }
	    hm.putAll(vic.getInventory().addItem(is[2]));
	    if (hm.size() > 0) {
		vic.getInventory().removeItem(is[0]);
		vic.getInventory().removeItem(is[1]);
		vic.getInventory().removeItem(new ItemStack(276, 512 - is[2].getAmount()));
		throw new Exception("Der Spieler hat nicht genug Platz im Inventar!");
	    }
	    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
		    "pex user " + args[0] + " group set executive");
	    sender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + args[0] + " ist nun EXECUTIVE");
	    vic.sendMessage(msg("payShopRang", 6, "EXECUTIVE"));

	} else if (args[1].equalsIgnoreCase("fullarmor")) {
	    HashMap<Integer, ItemStack> hm = new HashMap<Integer, ItemStack>();
	    ItemStack[] is = new ItemStack[4];
	    is[0] = new ItemStack(310, 1);
	    is[1] = new ItemStack(311, 1);
	    is[2] = new ItemStack(312, 1);
	    is[3] = new ItemStack(313, 1);

	    for (int i = 0; i < 4; i++) {
		is[i].addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
		is[i].addEnchantment(Enchantment.PROTECTION_FIRE, 4);
		is[i].addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4);
		is[i].addEnchantment(Enchantment.PROTECTION_PROJECTILE, 4);

	    }

	    is[3].addEnchantment(Enchantment.PROTECTION_FALL, 4);
	    is[0].addEnchantment(Enchantment.WATER_WORKER, 1);
	    is[0].addEnchantment(Enchantment.OXYGEN, 3);

	    hm.putAll(vic.getInventory().addItem(is[0]));
	    if (hm.size() > 0)
		throw new Exception("Der Spieler hat nicht genug Platz im Inventar!");

	    hm.putAll(vic.getInventory().addItem(is[1]));
	    if (hm.size() > 0) {
		vic.getInventory().removeItem(is[0]);

		throw new Exception("Der Spieler hat nicht genug Platz im Inventar!");
	    }
	    hm.putAll(vic.getInventory().addItem(is[2]));
	    if (hm.size() > 0) {
		vic.getInventory().removeItem(is[0]);
		vic.getInventory().removeItem(is[1]);

		throw new Exception("Der Spieler hat nicht genug Platz im Inventar!");
	    }
	    hm.putAll(vic.getInventory().addItem(is[3]));
	    if (hm.size() > 0) {
		vic.getInventory().removeItem(is[0]);
		vic.getInventory().removeItem(is[1]);
		vic.getInventory().removeItem(is[2]);

		throw new Exception("Der Spieler hat nicht genug Platz im Inventar!");
	    }

	    sender.sendMessage(ChatColor.GOLD + args[0] + " hat eine fullenchantete R\u00fcstung erhalten!");
	    vic.sendMessage(msg("payShopItemfullamor"));

	} else if (args[1].equalsIgnoreCase("fullsword")) {
	    HashMap<Integer, ItemStack> hm = new HashMap<Integer, ItemStack>();

	    ItemStack is = new ItemStack(276, 1);

	    is.addEnchantment(Enchantment.DAMAGE_ALL, 5);
	    is.addEnchantment(Enchantment.DAMAGE_ARTHROPODS, 5);
	    is.addEnchantment(Enchantment.DAMAGE_UNDEAD, 5);
	    is.addEnchantment(Enchantment.FIRE_ASPECT, 2);
	    is.addEnchantment(Enchantment.KNOCKBACK, 2);
	    is.addEnchantment(Enchantment.LOOT_BONUS_MOBS, 3);

	    hm.putAll(vic.getInventory().addItem(is));
	    if (hm.size() > 0)
		throw new Exception("Der Spieler hat nicht genug Platz im Inventar!");

	    sender.sendMessage(ChatColor.GOLD + args[0] + " hat eine fullenchantetes Schwert erhalten!");
	    vic.sendMessage(msg("payShopItemfullsword"));
	} else if (args[1].equalsIgnoreCase("fullpickaxe")) {
	    HashMap<Integer, ItemStack> hm = new HashMap<Integer, ItemStack>();

	    ItemStack is = new ItemStack(278, 1);

	    is.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
	    is.addEnchantment(Enchantment.SILK_TOUCH, 1);
	    is.addEnchantment(Enchantment.DIG_SPEED, 5);
	    is.addEnchantment(Enchantment.DURABILITY, 3);

	    hm.putAll(vic.getInventory().addItem(is));
	    if (hm.size() > 0)
		throw new Exception("Der Spieler hat nicht genug Platz im Inventar!");

	    sender.sendMessage(ChatColor.GOLD + args[0] + " hat eine fullenchantete Spitzhacke erhalten!");
	    vic.sendMessage(msg("payShopItemfullpickaxe"));
	} else if (args[1].equalsIgnoreCase("fullbow")) {
	    HashMap<Integer, ItemStack> hm = new HashMap<Integer, ItemStack>();

	    ItemStack is = new ItemStack(261, 1);

	    is.addEnchantment(Enchantment.ARROW_DAMAGE, 5);
	    is.addEnchantment(Enchantment.ARROW_FIRE, 1);
	    is.addEnchantment(Enchantment.ARROW_INFINITE, 1);
	    is.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);

	    hm.putAll(vic.getInventory().addItem(is));
	    if (hm.size() > 0)
		throw new Exception("Der Spieler hat nicht genug Platz im Inventar!");

	    sender.sendMessage(ChatColor.GOLD + args[0] + " hat einen fullenchanteten Bogen erhalten!");
	    vic.sendMessage(msg("payShopItemfullbow"));
	} else
	    sender.sendMessage(ChatColor.RED + "Verwendung:" + ChatColor.AQUA + " /payshop <name> [item/rang]");

    }

}
