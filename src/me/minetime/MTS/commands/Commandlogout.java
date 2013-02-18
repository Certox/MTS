package me.minetime.MTS.commands;

import static me.minetime.MTS.clazz.Message.msg;
import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;
import me.minetime.MTS.clazz.NotMove;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;

public class Commandlogout extends CommandMT {

    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) {

	Player player = user.getPlayer();

	boolean isInRegion = false;
	WorldGuardPlugin worldGuard = getWorldGuard();

	Location pt = player.getLocation();
	LocalPlayer localPlayer = worldGuard.wrapPlayer(player);
	World world = player.getWorld();
	RegionManager regionManager = worldGuard.getRegionManager(world);
	ApplicableRegionSet set = regionManager.getApplicableRegions(pt);
	if (!set.allows(DefaultFlag.PVP, localPlayer))
	    isInRegion = true;

	// Auswertung
	if (isInRegion == true) {
	    player.sendMessage(msg("insideNoPvP"));
	} else {
	    NotMove nm = new NotMove(user);
	    nm.go(true);
	}

    }

    protected WorldGuardPlugin getWorldGuard() {
	Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

	// WorldGuard may not be loaded
	if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	    return null; // Maybe you want throw an exception instead
	}

	return (WorldGuardPlugin) plugin;
    }

}
