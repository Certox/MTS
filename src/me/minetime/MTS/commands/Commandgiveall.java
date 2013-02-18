package me.minetime.MTS.commands;

import static me.minetime.MTS.clazz.Message.msg;

import java.util.Locale;

import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Commandgiveall extends CommandMT {

    @Override
    public void run(final IUser user, final String CommandLabel, final String[] args) throws Exception {
	boolean hand = false;
	int anzahl = 0;
	int id = 0;
	ItemStack item = null;

	if (args.length < 1 || args.length > 2)
	    throw new VerwendungsException("/giveall [ID|hand] [Anzahl]");

	/* Verarbeitung */
	if (args[0].equalsIgnoreCase("hand"))
	    hand = true;
	else
	    hand = false;

	if (!hand) {
	    try {
		id = Integer.parseInt(args[0]);
	    } catch (NumberFormatException e) {
		throw new Exception("ID muss eine Zahl sein.");
	    }
	} else {
	    id = user.getPlayer().getItemInHand().getType().getId();
	}

	if (args.length == 1)
	    anzahl = 1;
	else {
	    try {
		anzahl = Integer.parseInt(args[1]);
	    } catch (NumberFormatException e) {
		throw new Exception("Anzahl muss eine Zahl sein.");
	    }
	}

	item = new ItemStack(id, anzahl);

	for (Player p : Bukkit.getOnlinePlayers()) {
	    p.getInventory().addItem(item);
	}

	Bukkit.broadcastMessage(msg("giveallmsg", anzahl,
		item.getType().toString().toLowerCase(Locale.ENGLISH).replace('_', ' ')));

    }
}
