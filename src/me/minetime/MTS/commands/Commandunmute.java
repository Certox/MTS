package me.minetime.MTS.commands;

import static me.minetime.MTS.clazz.Message.msg;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.clazz.User;
import me.minetime.MTS.exception.PlayerNotOnlineException;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.Bukkit;

public class Commandunmute extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {
	if (args.length < 1) {
	    throw new VerwendungsException("/unmute [player]");
	}

	if (Bukkit.getPlayer(args[0]) == null) {
	    throw new PlayerNotOnlineException(args[0]);
	}

	User u = new User(args[0]);

	if (u.isMuted()) {
	    throw new Exception(msg("muteIsNotMuted", u.getName()));
	}

	u.unmute();
    }

}
