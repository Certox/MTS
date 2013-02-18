package me.minetime.MTS.commands;

import static me.minetime.MTS.clazz.Message.msg;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import me.minetime.MTS.MySQL;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.MaterialData;

public class Commandshop extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {

	Player player = user.getPlayer();

	if (args.length == 0)
	    show(player);
	else {
	    int command = 0;
	    if (args[0].equalsIgnoreCase("buy") || args[0].equalsIgnoreCase("b")) {
		command = 1;

		if (args.length == 3) {

		    int itm_count;
		    try {
			itm_count = Integer.parseInt(args[2]);
		    } catch (NumberFormatException e) {
			throw new Exception(msg("numberFormatAnzahl"));
		    }
		    if ((Integer.parseInt(args[2]) < 1) || (Integer.parseInt(args[2]) > 2304))
			/* 89 */throw new Exception(msg("shopAnzahl"));
		    double itm_price = 0.0D;
		    /* 97 */
		    boolean isNumber = false;
		    /* 98 */int itm_id = 0;
		    int exists = 0;
		    byte itm_data = 0;
		    String itm_name = args[1];
		    int available = 0;
		    /* 93 */
		    if (args[1].split(":").length == 2) {
			if (args[1].split(":")[0].matches("\\d+") && args[1].split(":")[1].matches("\\d+")) {
			    isNumber = true;
			    itm_id = Integer.parseInt(args[1].split(":")[0]);
			    itm_data = Byte.parseByte(args[1].split(":")[1]);
			}
		    }

		    /* 94 */

		    if (args[1].matches("\\d+")) {
			itm_id = Integer.parseInt(args[1]);
			isNumber = true;
		    }

		    if (isNumber) {

			try {
			    ResultSet rs = MySQL.Query("SELECT `buy`,`name`,`available` FROM `mt_shop` WHERE id='" + itm_id
				    + "' AND value='" + itm_data + "';");

			    while (rs.next()) {
				exists = 1;
				itm_price = rs.getDouble(1);
				itm_name = rs.getString(2);
				available = rs.getInt(3);
			    }
			    rs.close();
			} catch (Exception ex) {
			    System.out.println("Shop-Fehler:" + ex.getMessage());
			    throw new Exception(msg("shopFail"));
			}
		    }

		    else {
			try {
			    ResultSet rs = MySQL.Query("SELECT `buy`,`id`,`value`,`available` FROM `mt_shop` WHERE name='"
				    + itm_name + "' AND value='" + itm_data + "';");

			    while (rs.next()) {
				exists = 1;
				itm_price = rs.getDouble(1);
				itm_id = rs.getInt(2);
				itm_data = rs.getByte(3);
				available = rs.getInt(4);
			    }
			    rs.close();
			} catch (Exception ex) {
			    System.out.println("Shop-Fehler:" + ex.getMessage());
			    throw new Exception(msg("shopFail"));
			}
		    }

		    /* 125 */if (exists == 0)
			/* 126 */throw new Exception(msg("shopExist"));
		    else if (available == 0 || available == 2) {
			throw new Exception("Dieses Shopitem ist nicht für den Kauf verfügbar");
		    }
		    /*     */else
		    /*     */{
			/* 136 */double price = itm_count * itm_price;

			/*     */
			/* 139 */if (user.getMoney() >= price) {

			    PlayerInventory inv = player.getInventory();
			    ItemStack is = new ItemStack(itm_id, itm_count, itm_data);

			    HashMap<Integer, ItemStack> hm = inv.addItem(is);
			    int amount = itm_count - is.getAmount();
			    ItemStack is2 = new ItemStack(itm_id, amount, itm_data);
			    if (hm.size() == 0) {
				user.removeMoney(price);
				player.sendMessage(msg("shopSuccess", itm_count, itm_name, "gekauft"));
			    } else {
				inv.removeItem(is2);
				throw new Exception(msg("shopInvVoll"));
			    }

			    /*     */}
			/*     */else

			    /* 146 */throw new Exception(msg("shopMoney", price));

			/*     */}
		    /*     */
		    /*     */
		    /*     */
		    /*     */}
		/*     */else
		    /*     */throw new VerwendungsException("/shop buy [name] [anzahl] oder /shop buy [id:value] [anzahl]");
		/* 155 */
		/*     */
		/*     */
		/*     */}
	    /*     */
	    /* 160 */else if (args[0].equalsIgnoreCase("sell") || args[0].equalsIgnoreCase("s")) {
		/* 161 */command = 1;

		/* 162 */if (args.length == 3)
		/*     */{
		    /* 164 */int isNumber = 1;
		    /*     */int itm_count;
		    try {
			itm_count = Integer.parseInt(args[2]);
		    } catch (NumberFormatException e) {
			throw new Exception(msg("numberFormatAnzahl"));
		    }
		    /*     */
		    /* 176 */if (isNumber == 1) {
			/* 177 */if ((Integer.parseInt(args[2]) < 1) || (Integer.parseInt(args[2]) > 2304)) {
			    /* 178 */throw new Exception(msg("shopAnzahl"));
			    /*     */} else
			/*     */{
			    double itm_price = 0.0D;
			    /* 97 */int itm_available = 0;
			    /* 98 */int itm_id = 0;
			    int exists = 0;
			    byte itm_data = 0;
			    boolean isDigit = false;

			    String itm_name = args[1];

			    if (args[1].split(":").length == 2) {
				if (args[1].split(":")[0].matches("\\d+") && args[1].split(":")[1].matches("\\d+")) {
				    isDigit = true;
				    itm_id = Integer.parseInt(args[1].split(":")[0]);
				    itm_data = Byte.parseByte(args[1].split(":")[1]);
				}

			    }

			    /* 94 */

			    if (args[1].matches("\\d+")) {
				itm_id = Integer.parseInt(args[1]);
				isDigit = true;
			    }

			    if (isDigit) {

				try {
				    ResultSet rs = MySQL.Query("SELECT `sell`,`name`,`available` FROM `mt_shop` WHERE id='"
					    + itm_id + "' AND value='" + itm_data + "';");

				    while (rs.next()) {
					exists = 1;
					itm_price = rs.getDouble(1);
					itm_name = rs.getString(2);
					itm_available = rs.getInt(3);
				    }
				    rs.close();
				} catch (Exception ex) {
				    System.out.println("Shop-Fehler:" + ex.getMessage());
				    throw new Exception(msg("shopFail"));
				}
			    } else {
				try {
				    ResultSet rs = MySQL.Query("SELECT `sell`,`id`,`available` FROM `mt_shop` WHERE name='"
					    + itm_name + "' AND value='" + itm_data + "';");

				    while (rs.next()) {
					exists = 1;
					itm_price = rs.getDouble(1);
					itm_id = rs.getInt(2);
					itm_available = rs.getInt(3);
				    }
				    rs.close();
				} catch (Exception ex) {
				    System.out.println("Shop-Fehler:" + ex.getMessage());
				    throw new Exception(msg("shopFail"));
				}
			    }
			    /*     */
			    /* 214 */if (exists == 0) {
				/* 215 */throw new Exception(msg("shopExist"));
				/*     */}
			    /* 219 */else if (itm_available == 0 || itm_available == 3) {
				/* 220 */throw new Exception("Dieses Shopitem ist nicht für den Verkauf verfügbar!");
				/*     */}
			    /*     */else {
				/*     */
				/* 225 */
				/* 226 */PlayerInventory inv = player.getInventory();
				/* 227 */double price = itm_price * itm_count;

				ItemStack stack = new ItemStack(itm_id, itm_count, itm_data);
				ItemStack checker = new ItemStack(itm_id, itm_data);
				String mName = checker.getType().name().toUpperCase();
				Material mat = Material.getMaterial(mName);
				short max = mat.getMaxDurability();
				boolean damaged = false;
				boolean enchant = false;

				int invcount = 0;
				boolean sellbar = false;
				ItemStack[] inventory = inv.getContents();
				for (int a = 0; a < 36; a++) {
				    if (inventory[a] != null && inventory[a].getType() == checker.getType()) {
					Map<Enchantment, Integer> ench = inventory[a].getEnchantments();
					if (ench.size() >= 1) {
					    enchant = true;
					    continue;
					}

					if (((inventory[a].getType() == checker.getType())
						&& (inventory[a].getDurability() == itm_data) && max == 0)
						|| ((inventory[a].getType() == checker.getType()) && max >= 1 && inventory[a]
							.getDurability() == 0)) {
					    invcount = inventory[a].getAmount() + invcount;

					} else if (inventory[a].getType() == checker.getType() && max >= 1
						&& inventory[a].getDurability() >= 1) {
					    // player.sendMessage("Das Item ist beschädigt!");
					    damaged = true;
					    continue;

					}
				    } else
					continue;
				    if (invcount >= itm_count)
					sellbar = true;

				}

				/* 229 */if (inv.contains(itm_id)) {
				    /* 234 */if (sellbar)
				    /*     */{

					user.addMoney(price);
					inv.removeItem(stack);
					player.sendMessage(ChatColor.GREEN + "Du hast erfolgreich " + itm_count + " Mal " + itm_name + " für " + price + " verkauft.");

				    } else if (!sellbar && damaged) {
					throw new Exception("Du kannst keine beschädigten Items verkaufen!");
				    } else if (!sellbar && enchant) {
					throw new Exception("Du kannst keine verzauberten Items verkaufen!");
				    } else
					throw new Exception("Du hast nicht genug " + itm_name + " im Inventar.");

				} else {
				    throw new Exception(ChatColor.RED + "Du hast kein " + itm_name + " im Inventar.");
				}

				/*     */}
			    /*     */}
			/*     */
			/*     */}
		} else if (args.length == 2) {
		    int itm_count = 0;
		    if (args[1].equalsIgnoreCase("hand")) {
			ItemStack item;

			item = player.getItemInHand();
			if (item == null || item.getType() == Material.AIR) {
			    throw new Exception("Du hast nichts in der Hand!");

			}
			int itm_id = item.getTypeId();
			itm_count = item.getAmount();
			Map<Enchantment, Integer> ench = item.getEnchantments();

			if (ench.size() >= 1) {
			    throw new Exception("Du kannst keine verzauberten Items verkaufen!");

			}
			String itm_name = "";
			MaterialData data = item.getData();
			byte itm_data = 0;
			int exists = 0;
			double itm_price = 0;
			int itm_available = 0;
			String mName = item.getType().name().toUpperCase();
			Material mat = Material.getMaterial(mName);
			short max = mat.getMaxDurability();
			if (max == 0)
			    itm_data = data.getData();
			else if (max > 0 && data.getData() > 0)
			    throw new Exception("Du kannst keine beschädigten Items verkaufen!");

			try {
			    ResultSet rs = MySQL.Query("SELECT `sell`,`name`,`available` FROM `mt_shop` WHERE id='" + itm_id
				    + "' AND value='" + itm_data + "';");

			    while (rs.next()) {
				exists = 1;
				itm_price = rs.getDouble(1);
				itm_available = rs.getInt(3);

				itm_name = rs.getString(2);
			    }
			    rs.close();
			} catch (Exception ex) {
			    player.sendMessage(ChatColor.RED + "Fehler!");
			    System.out.println("Shop:" + ex);
			}

			/*     */
			/* 214 */if (exists == 0) {
			    /* 215 */throw new Exception(msg("shopExist"));
			    /*     */}
			/* 219 */else if (itm_available == 0 || itm_available == 3) {
			    /* 220 */throw new Exception("Dieses Shopitem ist nicht für den Verkauf verfügbar!");
			    /*     */} else {
			    double price = itm_price * itm_count;
			    user.addMoney(price);
			    /* 277 */player.getInventory().removeItem(item);

			    player.sendMessage(msg("shopSuccess", itm_count, itm_name, "verkauft"));

			}
		    } else if (args[1].equalsIgnoreCase("all")) {

			/* 226 */PlayerInventory inv = player.getInventory();

			double itm_price = 0.0D;
			int exists = 0;
			int itm_available = 0;
			int notselled = 0;
			double price = 0.0D;
			ItemStack[] inventory = inv.getContents();
			for (int a = 0; a < 36; a++) {

			    itm_price = 0.0D;
			    exists = 0;
			    itm_available = 0;

			    if (inventory[a] == null || inventory[a].getType() == Material.AIR) {
				// player.sendMessage("leer");
				continue;
			    } else {
				Map<Enchantment, Integer> ench = inventory[a].getEnchantments();
				if (ench.size() >= 1) {
				    // enchant = true;
				    notselled++;
				    continue;
				}

				String mName = inventory[a].getType().name().toUpperCase();
				Material mat = Material.getMaterial(mName);
				short max = mat.getMaxDurability();
				if (max >= 1 && inventory[a].getDurability() >= 1) {
				    // damaged = true;
				    notselled++;
				    continue;
				}
				int itm_id = inventory[a].getTypeId();
				int itm_data = inventory[a].getDurability();
				itm_count = inventory[a].getAmount();
				try {
				    ResultSet rs = MySQL.Query("SELECT `sell`,`available` FROM `mt_shop` WHERE id='"
					    + itm_id + "' AND value='" + itm_data + "';");

				    while (rs.next()) {
					exists = 1;
					itm_price = rs.getDouble(1);
					itm_available = rs.getInt(2);

				    }

				} catch (Exception ex) {
				    System.out.println("Shop-Fehler:" + ex.getMessage());
				    throw new Exception(msg("shopFail"));
				}

				if (exists == 0) {
				    notselled++;
				    continue;
				} else if (itm_available == 0 || itm_available == 3) {
				    notselled++;
				    continue;
				} else {
				    price = price + (itm_price * itm_count);

				    inv.clear(a);

				    // player.sendMessage(ChatColor.GREEN +
				    // "Du hast erfolgreich " + itm_count +
				    // " Mal " + itm_name + " verkauft.");

				}

			    }

			}
			user.addMoney(price);

			if (notselled >= 1)

			    player.sendMessage(ChatColor.RED + "Es konnten " + notselled + " Slots nicht geleert werden!");

			else

			    player.sendMessage(ChatColor.GREEN + "Alles wurde erfolgreich verkauft!");
		    } else
			throw new VerwendungsException("/shop sell/s hand/all");

		}
		/*     */else
		    /* 307 */throw new VerwendungsException("/shop sell [name] [anzahl]");
		/*     */
	    }
	    if (args[0].equalsIgnoreCase("price") || args[0].equalsIgnoreCase("p")) {
		command = 1;
		if (args.length == 2) {
		    String itm_name = "";
		    double itm_sellPrice = 0.0D;
		    double itm_buyPrice = 0.0D;
		    int itm_available = 0;
		    int exists = 0;
		    int itm_id = 0;
		    byte itm_data = 0;

		    if (args[1].equalsIgnoreCase("hand")) {
			ItemStack item;

			item = player.getItemInHand();
			if (item == null || item.getType() == Material.AIR)
			    throw new Exception("Du hast nichts in der Hand!");
			MaterialData data = item.getData();
			String mName = item.getType().name().toUpperCase();
			Material mat = Material.getMaterial(mName);
			short max = mat.getMaxDurability();
			itm_id = item.getTypeId();
			if (max == 0)
			    itm_data = data.getData();
			try {
			    ResultSet rs = MySQL.Query("SELECT * FROM `mt_shop` WHERE id='" + itm_id + "' AND value='"
				    + itm_data + "';");

			    while (rs.next()) {
				exists = 1;
				itm_buyPrice = rs.getDouble(3);
				itm_sellPrice = rs.getDouble(4);
				itm_available = rs.getInt(5);
				itm_data = rs.getByte(7);
				itm_name = rs.getString(2);

			    }
			    rs.close();
			} catch (Exception ex) {
			    System.out.println("Shop-Fehler:" + ex.getMessage());
			    throw new Exception(msg("shopFail"));
			}

		    } else {
			itm_name = args[1];
			boolean isNumber = false;
			if (args[1].split(":").length == 2) {
			    if (args[1].split(":")[0].matches("\\d+") && args[1].split(":")[1].matches("\\d+")) {
				isNumber = true;
				itm_id = Integer.parseInt(args[1].split(":")[0]);
				itm_data = Byte.parseByte(args[1].split(":")[1]);
			    }
			}
			if (itm_name.matches("\\d+") || isNumber) {
			    if (!isNumber)
				itm_id = Integer.parseInt(itm_name);
			    try {
				ResultSet rs = MySQL.Query("SELECT * FROM `mt_shop` WHERE id='" + itm_id + "' AND value='"
					+ itm_data + "';");

				while (rs.next()) {
				    exists = 1;
				    itm_buyPrice = rs.getDouble(3);
				    itm_sellPrice = rs.getDouble(4);
				    itm_available = rs.getInt(5);
				    itm_name = rs.getString(2);
				}
				rs.close();
			    } catch (Exception ex) {
				System.out.println("Shop-Fehler:" + ex.getMessage());
				throw new Exception(msg("shopFail"));
			    }
			} else {

			    try {
				ResultSet rs = MySQL.Query("SELECT * FROM `mt_shop` WHERE name='" + itm_name.toLowerCase()
					+ "';");

				while (rs.next()) {
				    exists = 1;
				    itm_buyPrice = rs.getDouble(3);
				    itm_sellPrice = rs.getDouble(4);
				    itm_available = rs.getInt(5);
				    itm_data = rs.getByte(7);
				    itm_id = rs.getInt(1);
				}
				rs.close();
			    } catch (Exception ex) {
				System.out.println("Shop-Fehler:" + ex.getMessage());
				throw new Exception(msg("shopFail"));
			    }
			}
		    }
		    String durability = (itm_data != 0 ? ChatColor.GREEN + ":" + itm_data : "");
		    if (exists == 0)
			throw new Exception(msg("shopExist"));

		    else if (exists == 1 && itm_available == 1) {
			player.sendMessage(ChatColor.GREEN + itm_name + "(" + itm_id + durability + ")");
			player.sendMessage(ChatColor.YELLOW + "Kaufpreis: " + itm_buyPrice + " MineTaler");
			player.sendMessage(ChatColor.YELLOW + "Verkaufspreis: " + itm_sellPrice + " MineTaler");
		    }

		    else if (exists == 1 && itm_available == 0) {
			player.sendMessage(ChatColor.GREEN + itm_name + "(" + itm_id + durability + ") ");
			player.sendMessage(ChatColor.RED + "Weder zum Kauf noch zum Verkauf verfügbar!");
		    } else if (exists == 1 && itm_available == 2) {
			player.sendMessage(ChatColor.GREEN + itm_name + "(" + itm_id + durability + ") ");
			player.sendMessage(ChatColor.YELLOW + "Kaufpreis: " + ChatColor.RED + "Nicht verfügbar!");
			player.sendMessage(ChatColor.YELLOW + "Verkaufspreis: " + itm_sellPrice + " MineTaler");
		    } else if (exists == 1 && itm_available == 3) {
			player.sendMessage(ChatColor.GREEN + itm_name + "(" + itm_id + durability + ") ");
			player.sendMessage(ChatColor.YELLOW + "Kaufpreis: " + itm_buyPrice + " MineTaler");
			player.sendMessage(ChatColor.YELLOW + "Verkaufspreis: " + ChatColor.RED + "Nicht verfügbar!");
		    }

		} else {
		    throw new VerwendungsException("/shop price/p [name] ");
		}
	    }
	    if (command == 0)
		show(player);
	}
	/*     */}

    protected void show(Player player) {

	player.sendMessage(ChatColor.AQUA + "--------- MineTime Shop v1.4 ----------");
	player.sendMessage(ChatColor.GOLD + "Item kaufen: " + ChatColor.DARK_AQUA + "/shop buy/b [name/ID] [anzahl]");
	player.sendMessage(ChatColor.GOLD + "Item verkaufen: " + ChatColor.DARK_AQUA + "/shop sell/s [name/ID] [anzahl]");
	player.sendMessage(ChatColor.GOLD + "Alle Items verkaufen: " + ChatColor.DARK_AQUA + "/shop sell/s all");
	player.sendMessage(ChatColor.GOLD + "Item in der Hand verkaufen: " + ChatColor.DARK_AQUA + "/shop sell/s hand");
	player.sendMessage(ChatColor.GOLD + "Preis anzeigen: " + ChatColor.DARK_AQUA + "/shop price/p [name/ID]");
	player.sendMessage(ChatColor.GOLD + "Preis des Items in der Hand anzeigen: " + ChatColor.DARK_AQUA
		+ "/shop price hand");

    }

}
