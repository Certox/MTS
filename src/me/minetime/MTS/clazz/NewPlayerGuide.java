package me.minetime.MTS.clazz;

import me.minetime.MTS.api.IUser;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class NewPlayerGuide implements Runnable{
    private int taskID = -1;
    private IUser user;
    private int time = 45;
    
    public NewPlayerGuide(IUser user2){
    	this.user = user2;
    }
    
    @Override
    public void run(){
    	if(!user.isOnline())
    		this.cancel();
    
       if(time != 0){
    	   int temp = time;
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   
    	   user.getPlayer().sendMessage(ChatColor.RED + "|-- „Willkommen auf MineTime.me!“ --|");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage(ChatColor.AQUA + "MineTime ist ein PvP-Server, wo es ums Kämpfen geht!");
    	   user.getPlayer().sendMessage(ChatColor.AQUA + "Laufe weit vom Spawn, suche nach Items und baue eine Base alleine oder mit Freunden! Clans kannst du mit " + ChatColor.GOLD + "/c");
    	   user.getPlayer().sendMessage(ChatColor.AQUA + "gründen! Informationen über dich: " + ChatColor.GOLD + "/p");
    	   user.getPlayer().sendMessage(ChatColor.AQUA + "Wenn du Fragen hast, wende sie an [ASK] mit " + ChatColor.GOLD + "/ask");
    	   user.getPlayer().sendMessage(ChatColor.AQUA + "Werde der Beste und besitze den mächtigsten Clan!");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage(ChatColor.AQUA + "Noch " + temp + " Sekunden wird die Nachricht angezeigt.");
       }
       else
       {
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage("");
    	   user.getPlayer().sendMessage(ChatColor.RED + "Viel Spaß auf MineTime!");
    	   
    	   //Stop task
    	   this.cancel();
       }
       
       time--;
    }
   
    public void go(){
    	taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Bukkit.getServer().getPluginManager().getPlugin("MTS"), this, 0, 20);
    }
    
    public void cancel(){
    	if (taskID == -1)
    	    return;
    	
    	try {
    	    Bukkit.getServer().getScheduler().cancelTask(taskID);
    	    user.setChatroom("GLOBAL");
    	} finally {
    	    taskID = -1;
    	}
    }
}
