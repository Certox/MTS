package me.minetime.MTS.api;

import java.util.List;

import org.bukkit.Location;

public interface IClan {

    Integer getId();

    String getClanName();

    IUser getClanOwner();

    IUser getClanFounder();

    Integer getClanDeaths();

    Integer getClanKills();

    String getuncolouredClanTag();

    String getcolouredClanTag();

    Integer getClanLevel();

    List<IUser> getClanMembers();

    List<IClan> getClanAlliances();

    Location getClanWarp();

    Double getClanMoney();

    void setClanWarp(Location l);

    void delClanMoney(Double d);

    void addClanMoney(Double d);

    void delClanKills(Integer i);

    void addClanKills(Integer i);

    void setClanLevel(Integer level);

    void sendClanMessage(String msg);

    void addClanDeaths(Integer i);

    Integer getClanRank();

}
