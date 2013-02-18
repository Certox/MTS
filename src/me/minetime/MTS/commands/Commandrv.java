package me.minetime.MTS.commands;

import java.util.ArrayList;
import java.util.Collections;

import me.minetime.MTS.api.IUser;
import me.minetime.MTS.clazz.CommandMT;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.NPC;
import org.bukkit.event.entity.EntityDeathEvent;

public class Commandrv extends CommandMT {

    @SuppressWarnings("unchecked")
    @Override
    public void run(final IUser user, final String commandLabel, final String[] args) throws Exception {

	ArrayList<Location> villSpawnPoints = new ArrayList<Location>();

	/* Kill NPCs */
	int npcspawn = 0;

	for (Chunk chunk : Bukkit.getServer().getWorlds().get(0).getLoadedChunks()) {

	    for (Entity entity : chunk.getEntities()) {

		if (entity instanceof NPC) {
		    EntityDeathEvent event = new EntityDeathEvent((LivingEntity) entity, Collections.EMPTY_LIST);
		    villSpawnPoints.add(entity.getLocation());
		    Bukkit.getServer().getPluginManager().callEvent(event);
		    entity.remove();

		}

	    }

	}

	/* Respawn NPCs */
	for (int i = 0; i < villSpawnPoints.size(); i++) {
	    Bukkit.getServer().getWorld(villSpawnPoints.get(i).getWorld().getName())
		    .spawnEntity(villSpawnPoints.get(i), EntityType.VILLAGER);
	    npcspawn++;
	}

	/* Ausgabe der Infos an Spieler */
	user.getPlayer().sendMessage(
		ChatColor.BLUE + "" + ChatColor.MAGIC + "... " + ChatColor.GOLD + "Es wurden " + ChatColor.AQUA + ""
			+ npcspawn + ChatColor.GREEN + " NPCs respawnt." + ChatColor.BLUE + "" + ChatColor.MAGIC + " ...");

    }

}
