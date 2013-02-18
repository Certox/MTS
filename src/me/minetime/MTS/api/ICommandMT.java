package me.minetime.MTS.api;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ICommandMT {

    void run(IUser user, Command cmd, String commandLabel, String[] args) throws Exception;

    void run(CommandSender sender, Command cmd, String commandLabel, String[] args) throws Exception;

}
