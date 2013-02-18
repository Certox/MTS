package me.minetime.MTS.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.minetime.MTS.MTSData;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.exception.VerwendungsException;

public class Commandcnick extends CommandMT {

    @Override
    public void run(final IUser user, final String CommandLabel, final String[] args) throws Exception {
    	Player player = user.getPlayer();
    	
    	if (args.length < 1) {
    	    throw new VerwendungsException("/cnick [name]");
    	}
    	
    	StringBuilder sb = new StringBuilder();
    	int i = 0;
	    for (String s : args) {
		if (i != 0){
		    sb.append(' ');
		}
		sb.append(args[i]);
		i++;
	    }
	    
    	player.setDisplayName(ChatColor.GRAY + sb.toString());
    	
    	MTSData.changePlayerName(player, sb.toString());
    }
}
