package me.minetime.MTS.commands;

import java.sql.ResultSet;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.minetime.MTS.MySQL;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;


public class Commandstarter extends CommandMT {


    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {
    	try{
    		ResultSet rs = MySQL.Query("SELECT `id` FROM `mt_starterkits` WHERE `name`='" + user.getName() + "';");
    		
    		while(rs.next()){
    			user.getPlayer().sendMessage(ChatColor.RED + "Man bekommt nur einmal ein Starterkit!");
    			return;
    		}
    		
    		MySQL.Update("INSERT INTO `mt_starterkits` (`name`) VALUES ('" + user.getName() + "');");
    		
    		user.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_SWORD, 1));
    		
    		user.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_HELMET, 1));
    		user.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_CHESTPLATE, 1));
    		user.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_LEGGINGS, 1));
    		user.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_BOOTS, 1));
    		
    		user.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_PICKAXE, 1));
    		user.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_AXE, 1));
    		user.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_SPADE, 1));
    		
    		user.getPlayer().getInventory().addItem(new ItemStack(322, 10));
    		user.getPlayer().getInventory().addItem(new ItemStack(Material.LOG, 16));
    		
    		user.getPlayer().sendMessage(ChatColor.GREEN + "Du hast dein StarterKit erhalten!");
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
}