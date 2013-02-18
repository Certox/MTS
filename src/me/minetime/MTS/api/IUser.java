package me.minetime.MTS.api;

import java.util.List;

import org.bukkit.entity.Player;

public interface IUser {

    Integer getKills();

    Integer getDeaths();

    IClan getClan();

    Double getMoney();

    Player getPlayer();

    boolean isOnline();

    void addKill();

    void addDeath();

    boolean isInClan();

    boolean hasClanRight(String cr);

    void setClan(Integer id);

    void setMoney(Double d);

    boolean hasAccount();

    void addMoney(Double d);

    void removeMoney(Double d);

    List<String> loadFriede();

    String getName();

    void createAccount();

    boolean isNew();

    boolean isIn1on1();

    void setIn1on1(boolean in1on1);
    
    boolean isInKnifeFight();
    
    void setInKnifeFight(boolean inKnifeFight);

    void checkWarns();

    boolean isMuted();

    void mute();

    void unmute();

    void setMuted(boolean b);

    boolean isInChatroom(String chatroom);

    void setChatroom(String chatroom);

    String getChatroom();

    void addPlaytime(Integer i);

    boolean isMarried();

    void setPartner(String s);

    List<String> getMarriageLog();

    String getPartner();

    Integer getPlaytime();

    boolean isFriede(String s);

}
