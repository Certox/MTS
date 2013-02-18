package me.minetime.MTS.clazz;

import static me.minetime.MTS.clazz.Message.msg;
import me.minetime.MTS.MTSData;
import me.minetime.MTS.api.IUser;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class NotMove implements Runnable {

    private IUser user;
    private int tpID = -1;
    private long started;
    private long delay;
    private long initX;
    private long initY;
    private long initZ;
    private int health;
    private boolean logout;
    private Location loc;
    private String ortname;

    public NotMove(IUser user, Location location, String ortname) {
	this.ortname = ortname;
	this.loc = location;
	this.user = user;
    }

    public NotMove(IUser user) {
	this.user = user;
    }

    private void initTimer(long delay) {
	this.started = System.currentTimeMillis();
	this.delay = delay;
	this.health = user.getPlayer().getHealth();
	this.initX = Math.round(user.getPlayer().getLocation().getX() * 0.3);
	this.initY = Math.round(user.getPlayer().getLocation().getY() * 0.3);
	this.initZ = Math.round(user.getPlayer().getLocation().getZ() * 0.3);
    }

    @Override
    public void run() {

	if (user == null || !user.isOnline() || user.getPlayer().getLocation() == null || (loc == null && logout == false)) {
	    cancel(false);
	    return;
	}
	if (Math.round(user.getPlayer().getLocation().getX() * 0.3) != initX
		|| Math.round(user.getPlayer().getLocation().getY() * 0.3) != initY
		|| Math.round(user.getPlayer().getLocation().getZ() * 0.3) != initZ || user.getPlayer().getHealth() < health) {
	    cancel(true);
	    return;
	}

	health = user.getPlayer().getHealth();

	long now = System.currentTimeMillis();
	if (now > started + delay) {
	    if (logout) {
		MTSData.authToLogout.add(user.getName());
		user.getPlayer().kickPlayer(msg("logout"));
	    } else {
		user.getPlayer().teleport(this.loc);
	    }
	    cancel(false);
	}

    }

    public void go(boolean logout) {
	this.logout = logout;
	cancel(false);
	if (logout)
	    user.getPlayer().sendMessage(msg("notMoveLogout"));
	else
	    user.getPlayer().sendMessage(msg("notMoveTeleport", this.ortname));
	initTimer(2000L);

	tpID = Bukkit.getServer().getScheduler()
		.scheduleSyncRepeatingTask(Bukkit.getServer().getPluginManager().getPlugin("MTS"), this, 10, 10);
    }

    public void cancel(boolean notifyUser) {
	if (tpID == -1) {
	    return;
	}
	try {
	    Bukkit.getServer().getScheduler().cancelTask(tpID);
	    if (notifyUser) {
		if (logout)
		    user.getPlayer().sendMessage(msg("notMoveLogoutCancelled"));
		else
		    user.getPlayer().sendMessage(msg("notMoveTeleportCancelled"));
	    }
	} finally {
	    tpID = -1;
	}
    }

}
