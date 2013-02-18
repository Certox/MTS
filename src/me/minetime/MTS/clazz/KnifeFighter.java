package me.minetime.MTS.clazz;

import java.sql.ResultSet;

import me.minetime.MTS.MySQL;
import me.minetime.MTS.api.IKnifeFighter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class KnifeFighter implements IKnifeFighter {

    private String userName = "";

    public KnifeFighter(String userName) {
	this.userName = userName;
    }

    public Integer getRounds() {
	String roundquery = "SELECT `rounds` FROM `mt_knifefight` WHERE `name`='" + this.userName.toLowerCase() + "';";
	Integer rounds = 0;
	try {
	    ResultSet rs = MySQL.Query(roundquery);
	    while (rs.next()) {
		rounds = rs.getInt(1);
	    }
	} catch (Exception e) {
	    System.out.println("MTS-Fehler (MySQL KnifeFight):\n");
	    e.printStackTrace();
	}
	return rounds;
    }

    public Integer getWins() {
	String winquery = "SELECT `wins` FROM `mt_knifefight` WHERE `name`='" + this.userName.toLowerCase() + "';";
	Integer wins = 0;
	try {
	    ResultSet rs = MySQL.Query(winquery);
	    while (rs.next()) {
		wins = rs.getInt(1);
	    }
	} catch (Exception e) {
	    System.out.println("MTS-Fehler (MySQL KnifeFight):\n");
	    e.printStackTrace();
	}
	return wins;
    }

    public Integer getLooses() {
	String loosequery = "SELECT `looses` FROM `mt_knifefight` WHERE `name`='" + this.userName.toLowerCase() + "';";
	Integer looses = 0;
	try {
	    ResultSet rs = MySQL.Query(loosequery);
	    while (rs.next()) {
		looses = rs.getInt(1);
	    }
	} catch (Exception e) {
	    System.out.println("MTS-Fehler (MySQL KnifeFight):\n");
	    e.printStackTrace();
	}
	return looses;
    }

    public Integer getDeaths() {
	String deathquery = "SELECT `deaths` FROM `mt_knifefight` WHERE `name`='" + this.userName.toLowerCase() + "';";
	Integer deaths = 0;
	try {
	    ResultSet rs = MySQL.Query(deathquery);
	    while (rs.next()) {
		deaths = rs.getInt(1);
	    }
	} catch (Exception e) {
	    System.out.println("MTS-Fehler (MySQL KnifeFight):\n");
	    e.printStackTrace();
	}
	return deaths;
    }

    public Integer getKills() {
	String killquery = "SELECT `kills` FROM `mt_knifefight` WHERE `name`='" + this.userName.toLowerCase() + "';";
	Integer kills = 0;
	try {
	    ResultSet rs = MySQL.Query(killquery);
	    while (rs.next()) {
		kills = rs.getInt(1);
	    }
	} catch (Exception e) {
	    System.out.println("MTS-Fehler (MySQL KnifeFight):\n");
	    e.printStackTrace();
	}
	return kills;
    }

    public Integer getRank() {
	String rankquery = "SELECT `name` FROM `mt_stats` ORDER BY `wins` DESC;";
	Integer rank = 0;
	boolean fertig = false;
	try {
	    ResultSet rs = MySQL.Query(rankquery);
	    while (rs.next() && !fertig) {
		rank++;
		if (rs.getString(1).equalsIgnoreCase(this.userName))
		    fertig = true;
		else
		    fertig = true;
	    }
	} catch (Exception e) {
	    System.out.println("MTS-Fehler (MySQL KnifeFight):\n");
	    e.printStackTrace();
	}
	return rank;
    }

    public void addKill() {
	Integer newkills = this.getKills() + 1;
	String addKQuery = "UPDATE `mt_knifefight` SET kills='" + newkills + "' WHERE name='" + this.userName + "';";
	MySQL.Update(addKQuery);
    }

    public void addDeath() {
	Integer newdeaths = this.getDeaths() + 1;
	String addDQuery = "UPDATE `mt_knifefight` SET deaths='" + newdeaths + "' WHERE name='" + this.userName + "';";
	MySQL.Update(addDQuery);
    }

    public void addRound() {
	Integer newrounds = this.getRounds() + 1;
	String addRQuery = "UPDATE `mt_knifefight` SET rounds='" + newrounds + "' WHERE name='" + this.userName + "';";
	MySQL.Update(addRQuery);
    }

    public void addWin() {
	Integer newwins = this.getWins() + 1;
	String addWQuery = "UPDATE `mt_knifefight` SET wins='" + newwins + "' WHERE name='" + this.userName + "';";
	MySQL.Update(addWQuery);
    }

    public void addLoose() {
	Integer newlooses = this.getLooses() + 1;
	String addWQuery = "UPDATE `mt_knifefight` SET looses='" + newlooses + "' WHERE name='" + this.userName + "';";
	MySQL.Update(addWQuery);
    }

    public String getName() {
	return this.userName;
    }

    public Player getPlayer() {
	Player p = Bukkit.getPlayer(this.userName);
	return p;
    }

    public void printStats(Player rec) {
	String magicPoints = ChatColor.MAGIC + "" + ChatColor.BLUE + "...";
	double kd = this.getKills();
	if (this.getDeaths() != 0) {
	    kd = (double) this.getKills() / this.getDeaths(); // KD ausrechnen
	    kd = Math.round(kd * 100) / 100.0; // KD auf 2 nachkommastellen
					       // runden
	}

	rec.sendMessage(magicPoints + ChatColor.RESET + ChatColor.RED + "Knife-Stats von " + this.userName + " "
		+ magicPoints);
	rec.sendMessage(ChatColor.BLUE + "Ranking: " + ChatColor.RED + "#" + this.getRank());
	rec.sendMessage(ChatColor.BLUE + "Runden: " + ChatColor.RED + this.getRounds());
	rec.sendMessage(ChatColor.BLUE + "Gewonnen: " + ChatColor.RED + this.getWins() + ChatColor.BLUE
		+ " \u007C Verloren: " + ChatColor.RED + this.getLooses());
	rec.sendMessage(ChatColor.BLUE + "Kills: " + ChatColor.RED + this.getKills() + ChatColor.BLUE + " \u007C Deaths: "
		+ ChatColor.RED + this.getDeaths());
    }

}
