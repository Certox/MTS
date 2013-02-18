package me.minetime.MTS.api;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ICommandMTHandler {

    boolean handleCommand(CommandSender sender, Command command, String commandLabel, String[] args);

}
