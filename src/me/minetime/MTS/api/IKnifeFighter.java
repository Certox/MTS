package me.minetime.MTS.api;

import org.bukkit.entity.Player;

public interface IKnifeFighter {

    Integer getRounds();

    Integer getWins();

    Integer getLooses();

    Integer getDeaths();

    Integer getKills();

    Integer getRank();

    String getName();

    Player getPlayer();

    void addKill();

    void addDeath();

    void addRound();

    void addWin();

    void addLoose();

    void printStats(Player rec);
}
