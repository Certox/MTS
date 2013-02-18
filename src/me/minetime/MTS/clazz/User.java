package me.minetime.MTS.clazz;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import me.minetime.MTS.MTSData;
import me.minetime.MTS.MySQL;
import me.minetime.MTS.api.IClan;
import me.minetime.MTS.api.IUser;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class User implements IUser {

    private String userName;
    private boolean isIn1on1;
    private boolean isInKnifeFight;

    public User(String s) {

	this.userName = s;
    }

    @Override
    public Integer getKills() {
	int userKills = 0;
	try {
	    ResultSet rs = MySQL.Query("SELECT `kills` FROM `mt_stats` WHERE `name`='" + this.userName + "';");

	    while (rs.next()) {
		userKills = rs.getInt(1);
	    }

	    rs.close();
	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}
	return userKills;
    }

    @Override
    public Integer getDeaths() {
	int userDeaths = 0;
	try {

	    ResultSet rs = MySQL.Query("SELECT `deaths` FROM `mt_stats` WHERE `name`='" + this.userName + "';");

	    while (rs.next()) {
		userDeaths = rs.getInt(1);
	    }

	    rs.close();
	    ;

	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}
	return userDeaths;
    }

    @Override
    public IClan getClan() {
	Integer clanId = 0;
	try {

	    ResultSet rs = MySQL.Query("SELECT `id` FROM `mt_clans_members` WHERE `name`='" + this.userName + "';");

	    while (rs.next()) {
		clanId = rs.getInt(1);
	    }

	    rs.close();
	    ;

	} catch (Exception err) {
	    System.out.println("MTS: " + err);
	}
	if (!(clanId == 0))
	    return new Clan(clanId);
	else
	    return null;

    }

    @Override
    public Double getMoney() {
	Double userMoney = 0D;
	try {

	    ResultSet rs = MySQL.Query("SELECT `money` FROM `mt_money` WHERE `name`='" + this.userName.toLowerCase() + "';");

	    while (rs.next()) {
		userMoney = rs.getDouble(1);
	    }

	    rs.close();
	    ;

	} catch (Exception err) {
	    System.out.println("bClan00: " + err);
	}
	return userMoney;
    }

    @Override
    public Player getPlayer() {
	Player userPlayer = Bukkit.getServer().getPlayer(this.userName);

	return userPlayer;
    }

    @Override
    public boolean isOnline() {
	Player p = Bukkit.getServer().getPlayer(this.userName);
	return p == null ? false : true;
    }

    @Override
    public void addKill() {

	int newKills = getKills();
	newKills++;
	MySQL.Update("UPDATE `mt_stats` SET kills='" + newKills + "' WHERE name='" + this.userName + "';");

    }

    @Override
    public void addDeath() {

	int newDeaths = getDeaths();
	newDeaths++;
	MySQL.Update("UPDATE `mt_stats` SET deaths='" + newDeaths + "' WHERE name='" + this.userName + "';");

    }

    @Override
    public boolean hasClanRight(String cr) {
	Boolean b = false;

	try {

	    ResultSet rs = MySQL.Query("SELECT `id` FROM `mt_clans_members` WHERE `" + cr + "`='1' AND `name`='"
		    + this.userName + "';");

	    while (rs.next()) {
		b = true;
	    }

	    rs.close();
	    ;

	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	return b;
    }

    @Override
    public boolean isInClan() {
	boolean b = false;

	try {

	    ResultSet rs = MySQL.Query("SELECT `id` FROM `mt_clans_members` WHERE `name`='" + this.userName + "';");

	    while (rs.next()) {
		b = true;
	    }

	    rs.close();
	    ;

	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}
	return b;
    }

    @Override
    public void setClan(Integer id) {
	// User aus Clan loeschen, falls er in einem ist
	MySQL.Update("DELETE FROM `mt_clans_members` WHERE `name`='" + userName + "';");

	// User in neuen Clan einfuegen (ohne clanrechte)
	MySQL.Update("INSERT INTO `mt_clans_members` (`id`, `name`, `invite`, `kick`, `warp`, `banktake`, `bankgive`, `rechte`) VALUES ('"
		+ id + "', '" + this.userName + "', '0', '0', '0', '0', '0', '0');");
	MTSData.TBL_friede.remove(this.userName);
	MTSData.TBL_friede.put(this.userName, this.loadFriede());
    }

    @Override
    public void setMoney(Double d) {
	MySQL.Update("UPDATE `mt_money` SET `money`='" + d + "' WHERE `name`='" + this.userName.toLowerCase() + "';");

    }

    @Override
    public boolean hasAccount() {
	boolean AccountExist = false;

	try {

	    ResultSet rs = MySQL.Query("SELECT * FROM `mt_money` WHERE `name`='" + this.userName.toLowerCase() + "';");

	    while (rs.next()) {
		AccountExist = true;
	    }

	    rs.close();
	    ;

	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}
	return AccountExist;
    }

    @Override
    public void addMoney(Double d) {
	double balance = 0.00;

	try {

	    ResultSet rs = MySQL.Query("SELECT `money` FROM `mt_money` WHERE `name`='" + this.userName.toLowerCase() + "';");

	    while (rs.next())
		balance = rs.getDouble(1);

	    rs.close();
	    ;

	} catch (Exception err) {
	    System.out.println("bMoney-Fehler: " + err);
	}

	double new_balance = balance + d;
	MySQL.Update("UPDATE `mt_money` SET `money`='" + new_balance + "' WHERE `name`='" + this.userName.toLowerCase()
		+ "';");
    }

    @Override
    public void removeMoney(Double d) {
	double balance = 0.00;
	try {

	    ResultSet rs = MySQL.Query("SELECT `money` FROM `mt_money` WHERE `name`='" + this.userName.toLowerCase() + "';");

	    while (rs.next())
		balance = rs.getDouble(1);

	    rs.close();
	    ;

	} catch (Exception err) {
	    System.out.println("bMoney-Fehler: " + err);
	}

	double new_balance = balance - d;
	MySQL.Update("UPDATE `mt_money` SET `money`='" + new_balance + "' WHERE `name`='" + this.userName.toLowerCase()
		+ "';");

    }

    @Override
    public List<String> loadFriede() {
	// Friede, Clan, Allianz
	List<String> temp = new ArrayList<String>();

	// Alle Spieler, mit dem player friede habn
	try {

	    ResultSet rs = MySQL.Query("SELECT `to` FROM `mt_friede` WHERE `from`='" + this.userName + "';");

	    while (rs.next()) {
		temp.add(rs.getString(1));
	    }

	    rs.close();
	    ;

	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	if (this.isInClan()) {
	    for (IUser user : this.getClan().getClanMembers())
		if (!temp.contains(user.getName()))
		    temp.add(user.getName());

	    for (IClan clan : this.getClan().getClanAlliances())
		for (IUser user : clan.getClanMembers()) {
		    if (!temp.contains(user.getName()))
			temp.add(user.getName());
		}
	}

	return temp;
    }

    @Override
    public String getName() {

	return this.userName;
    }

    @Override
    public String getChatroom() {
	String s = "GLOBAL";

	if (MTSData.chatrooms.containsKey(this.userName.toLowerCase())) {
	    s = MTSData.chatrooms.get(this.userName.toLowerCase());
	}

	return s;
    }

    @Override
    public void setChatroom(String chatroom) {
	if (chatroom.equalsIgnoreCase("GLOBAL")) {
	    if (MTSData.chatrooms.containsKey(this.userName.toLowerCase())) {
		MTSData.chatrooms.remove(this.userName.toLowerCase());
	    }
	} else {
	    if (MTSData.chatrooms.containsKey(this.userName.toLowerCase())) {
		MTSData.chatrooms.remove(this.userName.toLowerCase());
		MTSData.chatrooms.put(this.userName.toLowerCase(), chatroom);
	    } else {
		MTSData.chatrooms.put(this.userName.toLowerCase(), chatroom);
	    }
	}
    }

    @Override
    public boolean isInChatroom(String chatroom) {
	boolean b = false;

	if (MTSData.chatrooms.containsKey(this.userName.toLowerCase())) {
	    if (MTSData.chatrooms.get(this.userName.toLowerCase()).equalsIgnoreCase(chatroom)) {
		b = true;
	    }
	}

	return b;
    }

    @Override
    public void createAccount() {

	Date myDate = new Date();
	SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	df2.setTimeZone(TimeZone.getDefault());
	df2.format(myDate);
	Calendar gc2 = new GregorianCalendar();
	gc2.setTime(myDate);
	gc2.add(Calendar.MINUTE, 20);
	Date now = gc2.getTime();
	MySQL.Update("INSERT INTO `mt_stats` (name, kills, deaths) VALUES ('" + this.userName + "', '0', '0');");
	MySQL.Update("INSERT INTO `mt_money` (name, money) VALUES ('" + this.userName + "', '30.00');");
	MySQL.Update("INSERT INTO `mt_newusers` (name, time) VALUES ('" + this.userName + "', '" + df2.format(now) + "');");
	MTSData.TBL_neuespieler.put(this.userName, df2.format(now));
    }

    @Override
    public boolean isNew() {
	if (!MTSData.TBL_neuespieler.containsKey(this.getName()))
	    return false;
	else {
	    try {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// replace with your start date string
		Date d = df.parse(MTSData.TBL_neuespieler.get(this.getName()));

		Date myDate = new Date();
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df2.setTimeZone(TimeZone.getDefault());
		df2.format(myDate);

		Calendar gc2 = new GregorianCalendar();
		gc2.setTime(myDate);
		Date now = gc2.getTime();
		if (now.after(d)) {

		    MTSData.TBL_neuespieler.remove(this.getName());
		    MySQL.Update("DELETE FROM `mt_newusers` WHERE `name`='" + this.getName() + "';");
		    return false;
		} else
		    return true;
	    } catch (Exception e) {

		e.printStackTrace();
		return false;
	    }

	}

    }

    public boolean isIn1on1() {
	return isIn1on1;
    }

    public void setIn1on1(boolean isIn1on1) {
	this.isIn1on1 = isIn1on1;
    }

    
    public boolean isInKnifeFight(){
    	return isInKnifeFight;
    }
    
    public void setInKnifeFight(boolean inKnifeFight){
    	this.isInKnifeFight = inKnifeFight;	
    }
    
    public void checkWarns() {
	try {
	    int warnungen = 0;

	    try {

		ResultSet rs = MySQL.Query("SELECT `id` FROM `mt_warns` WHERE name='" + this.userName + "';");

		while (rs.next()) {
		    warnungen++;
		}

		rs.close();
		;

	    } catch (Exception err) {
		System.out.println("MTS-Fehler: " + err);
	    }
	    if (warnungen == 4) {
		if (this.isOnline() == true) {
		    MTSData.authToLogout.add(this.userName);
		    Bukkit.getServer()
			    .getPlayer(this.userName)
			    .kickPlayer(
				    ChatColor.RED
					    + "Du hast gerade deine 4. Warnung erhalten. Bei 5 Warnungen wirst du f�r 2 Stunden gebannt!");
		}
	    }

	    if (warnungen == 5) {

		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "zeitsperre " + this.userName + " 2 5 Warnungen");
	    }

	    if (warnungen == 6) {
		if (this.isOnline() == true) {
		    MTSData.authToLogout.add(this.userName);
		    Bukkit.getServer()
			    .getPlayer(this.userName)
			    .kickPlayer(
				    ChatColor.RED
					    + "Du hast gerade deine 6. Warnung erhalten. Bei 7 Warnungen wirst du f�r 24 Stunden gebannt!");
		}
	    }

	    if (warnungen == 7)

		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "zeitsperre " + this.userName + " 24 7 Warnungen");

	    if (warnungen == 9) {
		if (this.isOnline() == true) {
		    MTSData.authToLogout.add(this.userName);
		    Bukkit.getServer()
			    .getPlayer(this.userName)
			    .kickPlayer(
				    ChatColor.RED
					    + "Du hast gerade deine 9. Warnung erhalten. Bei 10 Warnungen wirst du permanent gebannt!");
		}
	    }

	    if (warnungen >= 10)

		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sperre " + this.userName + " 10 Warnungen");

	} catch (Exception ex) {
	}
    }

    @Override
    public void setMuted(boolean b) {
	if (b == true) {
	    MySQL.Update("DELETE FROM `mt_mute` WHERE `name`='" + this.userName + "';");
	    MySQL.Update("INSERT INTO `mt_mute` (`name`) VALUES ('" + this.userName + "');");
	} else {
	    MySQL.Update("DELETE FROM `mt_mute` WHERE `name`='" + this.userName + "';");
	}
    }

    @Override
    public boolean isMuted() {
	boolean isMuted = false;
	try {
	    ResultSet rs = MySQL.Query("SELECT `id` FROM `mt_mute` WHERE name='" + this.userName + "';");

	    while (rs.next()) {
		isMuted = true;
	    }

	    rs.close();
	    ;

	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	return isMuted;
    }

    @Override
    public void mute() {
	MySQL.Update("INSERT INTO `mute` (name) VALUES ('" + this.userName + "');");
    }

    @Override
    public void unmute() {
	MySQL.Update("DELETE FROM `mute` WHERE name='" + this.userName + "');");
    }

    @Override
    public void addPlaytime(Integer i) {
	boolean b = false;
	String now = "";

	try {

	    ResultSet rs = MySQL.Query("SELECT `playtime` FROM `mt_stats` WHERE name='" + this.userName + "';");

	    while (rs.next()) {
		if (rs.getString(1).equals("")) {
		    b = true;
		    MySQL.Update("UPDATE `mt_stats` SET `playtime`='" + String.valueOf(i) + "' WHERE `name`='"
			    + this.userName + "';");
		} else {
		    now = rs.getString(1);
		}
	    }

	    rs.close();

	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	if (b == false || !now.equalsIgnoreCase("")) {
	    int newtime = Integer.valueOf(now) + i;
	    MySQL.Update("UPDATE `mt_stats` SET `playtime`='" + newtime + "' WHERE `name`='" + this.userName + "';");
	}
    }

    @Override
    public boolean isMarried() {
	boolean b = false;

	try {

	    ResultSet rs = MySQL.Query("SELECT * FROM `mt_partner` WHERE `from`='" + this.userName + "';");

	    while (rs.next()) {
		b = true;
	    }

	    rs.close();

	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	return b;
    }

    @Override
    public void setPartner(String s) {
	MySQL.Update("DELETE FROM `mt_partner` WHERE `from`='" + this.userName + "' OR `to`='" + this.userName + "';");

	// Jetzige Zeit definieren
	Date myDate = new Date();
	SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yyyy");
	df2.setTimeZone(TimeZone.getDefault());
	df2.format(myDate);
	Calendar gc2 = new GregorianCalendar();
	gc2.setTime(myDate);
	Date now = gc2.getTime();

	MySQL.Update("INSERT INTO `mt_partner` (`from`, `to`) VALUES ('" + this.userName + "', '" + s + "');");
	MySQL.Update("INSERT INTO `mt_partner` (`from`, `to`) VALUES ('" + s + "', '" + this.userName + "');");
	MySQL.Update("INSERT INTO `mt_partner_log` (`name`, `date`) VALUES ('" + this.userName + "', '" + df2.format(now)
		+ "');");
	MySQL.Update("INSERT INTO `mt_partner_log` (`name`, `date`) VALUES ('" + s + "', '" + df2.format(now) + "');");
    }

    @Override
    public List<String> getMarriageLog() {
	List<String> l = new ArrayList<String>();

	try {

	    ResultSet rs = MySQL.Query("SELECT `date` FROM `mt_partner_log` WHERE `name`='" + this.userName + "';");

	    while (rs.next()) {
		l.add(rs.getString(1));
	    }

	    rs.close();

	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	return l;
    }

    @Override
    public String getPartner() {
	String s = "";

	try {

	    ResultSet rs = MySQL.Query("SELECT `to` FROM `mt_partner` WHERE `from`='" + this.userName + "';");

	    while (rs.next()) {
		s = rs.getString(1);
	    }

	    rs.close();

	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	return s;
    }

    @Override
    public Integer getPlaytime() {
	Integer i = 0;

	try {

	    ResultSet rs = MySQL.Query("SELECT `playtime` FROM `mt_stats` WHERE `name`='" + this.userName + "';");

	    while (rs.next()) {
		i = rs.getInt(1);
	    }

	    rs.close();

	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	return i;
    }

    @Override
    public boolean isFriede(String s) {
	boolean b = false;

	try {

	    ResultSet rs = MySQL.Query("SELECT * FROM `mt_friede` WHERE `from`='" + this.userName + "' AND `to`='" + s
		    + "';");

	    while (rs.next()) {
		b = true;
	    }

	    rs.close();

	} catch (Exception err) {
	    System.out.println("MTS-Fehler: " + err);
	}

	return b;
    }
}
