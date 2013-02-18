package me.minetime.MTS.clazz;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import me.minetime.MTS.MySQL;
import me.minetime.MTS.api.IClan;
import me.minetime.MTS.api.IUser;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class Clan implements IClan {

    private Integer clanId;

    public Clan(Integer clanId) {
	this.clanId = clanId;
    }

    @Override
    public Integer getId() {
	return this.clanId;
    }

    @Override
    public String getClanName() {

	String clanName = "";
	try {
	    ResultSet rs = MySQL.Query("SELECT `name` FROM `mt_clans` WHERE `id`='" + this.clanId + "';");

	    while (rs.next()) {
		clanName = rs.getString(1);
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}
	return clanName;
    }

    @Override
    public IUser getClanOwner() {

	String clanOwner = "";
	try {
	    ResultSet rs = MySQL.Query("SELECT `owner` FROM `mt_clans` WHERE `id`='" + this.clanId + "';");

	    while (rs.next()) {
		clanOwner = rs.getString(1);
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}
	return new User(clanOwner);
    }

    @Override
    public Integer getClanDeaths() {
	Integer clanDeaths = 0;
	try {
	    ResultSet rs = MySQL.Query("SELECT `deaths` FROM `mt_clans` WHERE `id`='" + this.clanId + "';");

	    while (rs.next()) {
		clanDeaths = rs.getInt(1);
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}
	return clanDeaths;
    }

    @Override
    public Integer getClanKills() {
	int clanKills = 0;
	try {
	    ResultSet rs = MySQL.Query("SELECT `kills` FROM `mt_clans` WHERE `id`='" + this.clanId + "';");

	    while (rs.next()) {
		clanKills = rs.getInt(1);
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}
	return clanKills;
    }

    @Override
    public Integer getClanRank() {
	Integer c = 0;
	Integer counter = 0;

	try {
	    ResultSet rs = MySQL.Query("SELECT `id` FROM `mt_clans` ORDER BY `kills` DESC;");

	    while (rs.next()) {
		if (rs.getInt(1) == this.clanId) {
		    counter = counter + 1;
		    c = counter;
		} else {
		    counter = counter + 1;
		}
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}

	return c;
    }

    @Override
    public Integer getClanLevel() {
	Integer clanLevel = 0;

	try {
	    ResultSet rs = MySQL.Query("SELECT `level` FROM `mt_clans` WHERE `id`='" + this.clanId + "';");

	    while (rs.next()) {
		clanLevel = rs.getInt(1);
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}
	return clanLevel;
    }

    @Override
    public List<IUser> getClanMembers() {

	List<IUser> member = new ArrayList<IUser>();
	try {
	    ResultSet rs = MySQL.Query("SELECT `name` FROM `mt_clans_members` WHERE `id`='" + this.clanId + "';");

	    while (rs.next()) {
		member.add(new User(rs.getString(1)));
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS123: " + err);
	}

	return member;
    }

    @Override
    public String getcolouredClanTag() {
	String tag = "", cTag = "";
	try {
	    ResultSet rs = MySQL.Query("SELECT `short` FROM `mt_clans` WHERE `id`='" + this.clanId + "';");

	    while (rs.next()) {
		tag = rs.getString(1);
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}
	
	if (tag.equals("MTT"))
	    cTag = ChatColor.DARK_RED + "" + ChatColor.BOLD + tag;
	else {
	    switch (this.getClanLevel()) {
	    case 1:
		cTag = ChatColor.AQUA + tag;
		break;
	    case 2:
		cTag = ChatColor.AQUA + tag;
		break;
	    case 3:
		cTag = ChatColor.GREEN + tag;
		break;
	    case 4:
		cTag = ChatColor.GREEN + tag;
		break;
	    case 5:
		cTag = ChatColor.GOLD + tag;
		break;
	    case 6:
		cTag = ChatColor.DARK_PURPLE + tag;
		break;
	    case 7:
		cTag = ChatColor.GRAY + "" + ChatColor.BOLD + tag;
		break;
	    case 8:
		cTag = ChatColor.DARK_BLUE + "" + ChatColor.BOLD + tag;
		break;
	    case 9:
		cTag = ChatColor.AQUA + "" + ChatColor.BOLD + tag;
		break;
	    case 10:
		cTag = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + tag;
		break;
	    default:
		cTag = ChatColor.AQUA + tag;
		break;
	    }
	    
	    if (tag.equals("Kartoffl"))
		    cTag = ChatColor.YELLOW + tag + ChatColor.RESET;
			    
	}
	return cTag;
    }

    @Override
    public String getuncolouredClanTag() {
	String tag = "";
	try {
	    ResultSet rs = MySQL.Query("SELECT `short` FROM `mt_clans` WHERE `id`='" + this.clanId + "';");

	    while (rs.next()) {
		tag = rs.getString(1);
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}
	return tag;
    }

    @Override
    public List<IClan> getClanAlliances() {
	List<IClan> clan = new ArrayList<IClan>();

	try {
	    ResultSet rs = MySQL.Query("SELECT `from` FROM `mt_clans_allianzen` WHERE `to`='" + this.clanId + "';");

	    while (rs.next()) {
		clan.add(new Clan(rs.getInt(1)));
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}

	return clan;
    }

    @Override
    public IUser getClanFounder() {
	String clanFounder = "";
	try {
	    ResultSet rs = MySQL.Query("SELECT `founder` FROM `mt_clans` WHERE `id`='" + this.clanId + "';");

	    while (rs.next()) {
		clanFounder = rs.getString(1);
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}
	return new User(clanFounder);
    }

    @Override
    public Location getClanWarp() {
	Location clanWarp = null;
	try {
	    ResultSet rs = MySQL.Query("SELECT `world`, `x`, `y`, `z` FROM `mt_clans` WHERE `id`='" + this.clanId + "';");

	    while (rs.next()) {
		clanWarp = new Location(Bukkit.getWorld(rs.getString(1)), rs.getDouble(2), rs.getDouble(3), rs.getDouble(4));
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}
	return clanWarp;
    }

    @Override
    public Double getClanMoney() {
	Double clanMoney = 0D;
	try {
	    ResultSet rs = MySQL.Query("SELECT `money` FROM `mt_clans` WHERE `id`='" + this.clanId + "';");

	    while (rs.next()) {
		clanMoney = rs.getDouble(1);
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}
	return clanMoney;
    }

    @Override
    public void setClanWarp(Location l) {
	MySQL.Update("UPDATE mt_clans SET world='" + l.getWorld().getName() + "', x='" + l.getX() + "', y='" + l.getY()
		+ "', z='" + l.getZ() + "' WHERE id='" + this.clanId + "';");
    }

    @Override
    public void delClanMoney(Double d) {
	Double n = 0.0;

	try {
	    ResultSet rs = MySQL.Query("SELECT `money` FROM `mt_clans` WHERE `id`='" + this.clanId + "';");

	    while (rs.next()) {
		n = rs.getDouble(1);
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	n = n - d;

	MySQL.Update("UPDATE `mt_clans` SET `money`='" + n + "' WHERE `id`='" + this.clanId + "';");
    }

    @Override
    public void addClanMoney(Double d) {
	Double n = 0.0;

	try {
	    ResultSet rs = MySQL.Query("SELECT `money` FROM `mt_clans` WHERE `id`='" + this.clanId + "';");

	    while (rs.next()) {
		n = rs.getDouble(1);
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	n = n + d;

	MySQL.Update("UPDATE `mt_clans` SET `money`='" + n + "' WHERE `id`='" + this.clanId + "';");
    }

    @Override
    public void delClanKills(Integer i) {
	Integer n = 0;

	try {
	    ResultSet rs = MySQL.Query("SELECT `kills` FROM `mt_clans` WHERE `id`='" + this.clanId + "';");

	    while (rs.next()) {
		n = rs.getInt(1);
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	n = n - i;

	MySQL.Update("UPDATE `mt_clans` SET `kills`='" + n + "' WHERE `id`='" + this.clanId + "';");
    }

    @Override
    public void addClanKills(Integer i) {
	Integer n = 0;

	try {
	    ResultSet rs = MySQL.Query("SELECT `kills` FROM `mt_clans` WHERE `id`='" + this.clanId + "';");

	    while (rs.next()) {
		n = rs.getInt(1);
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	n = n + i;

	MySQL.Update("UPDATE `mt_clans` SET `kills`='" + n + "' WHERE `id`='" + this.clanId + "';");
    }

    @Override
    public void addClanDeaths(Integer i) {
	Integer n = 0;

	try {
	    ResultSet rs = MySQL.Query("SELECT `deaths` FROM `mt_clans` WHERE `id`='" + this.clanId + "';");

	    while (rs.next()) {
		n = rs.getInt(1);
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	n = n + i;

	MySQL.Update("UPDATE `mt_clans` SET `deaths`='" + n + "' WHERE `id`='" + this.clanId + "';");
    }

    @Override
    public void setClanLevel(Integer level) {
	MySQL.Update("UPDATE `mt_clans` SET `level`='" + level + "' WHERE `id`='" + this.clanId + "';");
    }

    @Override
    public void sendClanMessage(String msg) {
	for (IUser u : this.getClanMembers()) {
	    if (u.isOnline()) {
		u.getPlayer().sendMessage(msg);
	    }
	}
    }

}
