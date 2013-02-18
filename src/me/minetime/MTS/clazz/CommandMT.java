package me.minetime.MTS.clazz;

import me.minetime.MTS.api.ICommandMT;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.exception.OnlyPlayerException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMT implements ICommandMT {

    @Override
    public final void run(final IUser user, final Command cmd, final String commandLabel, final String[] args)
	    throws Exception {
	run(user, commandLabel, args);

    }

    protected void run(final IUser user, final String commandLabel, final String[] args) throws Exception {
	Player userPlayer = user.getPlayer();
	run((CommandSender) userPlayer, commandLabel, args);
    }

    @Override
    public final void run(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args)
	    throws Exception {
	run(sender, commandLabel, args);
    }

    protected void run(final CommandSender sender, final String commandLabel, final String[] args) throws Exception {
	throw new OnlyPlayerException();
    }

}
