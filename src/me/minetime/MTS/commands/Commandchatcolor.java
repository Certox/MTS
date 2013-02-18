package me.minetime.MTS.commands;

import static me.minetime.MTS.clazz.Message.msg;
import me.minetime.MTS.MTSData;
import me.minetime.MTS.MySQL;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Commandchatcolor extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {
	Player player = user.getPlayer();

	if (MTSData.chatColors.containsKey(player.getName())) {
	    MTSData.chatColors.remove(player.getName());

	    player.sendMessage(msg("chatheadWeg"));
	    MySQL.Update("DELETE FROM `mt_chatheads` WHERE `name`='" + player.getName() + "';");
	} else {
	    if (args.length >= 1) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
		    sb.append(' ');
		    sb.append(args[i]);
		}
		String head = sb.toString().substring(1);

		if (head.length() > 2) {
		    PermissionManager pex = PermissionsEx.getPermissionManager();
		    if (pex.has(player, "MTS.ChatcolorTeam")){
			MTSData.chatColors.put(player.getName(), head);
			MySQL.Update("INSERT INTO `mt_chatheads` (`name`, `head`) VALUES ('" + player.getName() + "', '"
				+ head + "')");

			player.sendMessage(msg("chatheadGesetzt"));
		    } else {
			throw new Exception(msg("chatheadException"));
		    }
		} else {
		    MTSData.chatColors.put(player.getName(), head);
		    MySQL.Update("INSERT INTO `mt_chatheads` (`name`, `head`) VALUES ('" + player.getName() + "', '" + head
			    + "')");

		    player.sendMessage(msg("chatheadGesetzt"));
		}
	    } else
		throw new VerwendungsException("/chatcolor [FarbCode/ChatHead]");

	}

    }

}
