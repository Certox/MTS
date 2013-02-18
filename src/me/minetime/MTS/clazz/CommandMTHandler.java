package me.minetime.MTS.clazz;

import static me.minetime.MTS.clazz.Message.msg;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import me.minetime.MTS.api.ICommandMT;
import me.minetime.MTS.api.ICommandMTHandler;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.exception.VerwendungsException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class CommandMTHandler implements ICommandMTHandler {

    private final transient ClassLoader classLoader;
    private final transient String commandPath;
    private final transient Map<String, ICommandMT> commands = new HashMap<String, ICommandMT>();

    public CommandMTHandler(ClassLoader classLoader, String commandPath) {
	this.classLoader = classLoader;
	this.commandPath = commandPath;

    }

    public boolean handleCommand(final CommandSender sender, final Command command, final String commandLabel,
	    final String[] args) {

	// ////////////////////////
	// /////alias system///////
	// ////////////////////////

	final String commandName = command.getName().toLowerCase(Locale.ENGLISH);
	ICommandMT cmd = commands.get(commandName);
	if (cmd == null) {

	    try {
		cmd = (CommandMT) classLoader.loadClass(commandPath + commandName).newInstance();
		commands.put(commandName, cmd);
	    } catch (Exception ex) {
		sender.sendMessage(msg("error", 001));
		ex.printStackTrace();

		return true;
	    }

	}

	// PermissionSystem//

	IUser user = null;
	if (sender instanceof Player) {
	    user = new User(sender.getName());
	    if (!hasPerm(commandLabel, user))
	    // log.info(msg("commandLog", sender.getName(), "/" + commandLabel +
	    // argsToString(args)));

	    {
		user.getPlayer().sendMessage(msg("noPermissions"));
		return true;
	    }

	}

	try {
	    if (user == null)
		cmd.run(sender, command, commandLabel, args);
	    else
		cmd.run(user, command, commandLabel, args);
	    return true;
	} catch (VerwendungsException e) {
	    sender.sendMessage(msg("verwendung", e.getMessage()));
	    return true;
	} catch (Throwable ex) {
	    sender.sendMessage(msg("exceptionMsg", ex.getMessage()));
	    return true;
	}

    }

    private boolean hasPerm(String cmd, IUser user) {

	if (cmd.equalsIgnoreCase("c") || cmd.equalsIgnoreCase("p") || cmd.equalsIgnoreCase("logout")
		|| cmd.equalsIgnoreCase("vote") || cmd.equalsIgnoreCase("infos") || cmd.equalsIgnoreCase("annehmen")
		|| cmd.equalsIgnoreCase("ranking") || cmd.equalsIgnoreCase("activate") || cmd.equalsIgnoreCase("gutschein")
		|| cmd.equalsIgnoreCase("shop") || cmd.equalsIgnoreCase("team") || cmd.equalsIgnoreCase("money")
		|| cmd.equalsIgnoreCase("list") || cmd.equalsIgnoreCase("bewerbung") || cmd.equalsIgnoreCase("cw")
		|| cmd.equalsIgnoreCase("event") || cmd.equalsIgnoreCase("support") || cmd.equalsIgnoreCase("1on1") || cmd.equalsIgnoreCase("starter"))
	    return true;
	String perm = "MTS." + cmd;
	final PermissionManager pex = PermissionsEx.getPermissionManager();
	return pex.has(user.getPlayer(), perm);
    }
}
