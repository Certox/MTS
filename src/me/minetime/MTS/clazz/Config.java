package me.minetime.MTS.clazz;

import java.io.File;

import me.minetime.MTS.api.IConfig;

import org.bukkit.configuration.file.YamlConfiguration;

public class Config implements IConfig {

    public Config() {

    }

    @Override
    public String getString(String path) {
	YamlConfiguration config = new YamlConfiguration();
	File pluginFolder = new File("plugins", "MTS");
	File mtsConfig = new File(pluginFolder, "MTS_config.yml");

	if (!pluginFolder.exists()) {
	    try {
		pluginFolder.mkdir();
	    } catch (Exception e) {
		System.out.println("MTS Config Create Fehler:\n");
		e.printStackTrace();
	    }
	}

	if (!mtsConfig.exists()) {
	    try {
		mtsConfig.createNewFile();
	    } catch (Exception e) {
		System.out.println("MTS Config Create Fehler:\n");
		e.printStackTrace();
	    }

	    config.set("MySQL.MineTime.Host", "host");
	    config.set("MySQL.MineTime.DB", "db");
	    config.set("MySQL.MineTime.User", "user");
	    config.set("MySQL.MineTime.Pass", "pass");

	    config.set("MySQL.MineTimeSurf.Host", "host");
	    config.set("MySQL.MineTimeSurf.DB", "db");
	    config.set("MySQL.MineTimeSurf.User", "user");
	    config.set("MySQL.MineTimeSurf.Pass", "pass");
	    config.set("JoinMSG.Nachricht", "Hey Willkommen!");
	    config.set("Whitelist.Enabled", false);
	    config.set("Whitelist.Nachricht", "§Serverwartungen");

	    try {
		config.save(mtsConfig);
	    } catch (Exception e) {
		System.out.println("MTS Config Save Fehler:\n");
		e.printStackTrace();
	    }
	}

	try {
	    config.load(mtsConfig);
	} catch (Exception e) {
	    System.out.println("MTS Config Load Fehler:\n");
	    e.printStackTrace();
	}

	String data = config.getString(path);

	return data;
    }

    @Override
    public void setString(String path, String value) {

	YamlConfiguration config = new YamlConfiguration();
	File pluginFolder = new File("plugins", "MTS");
	File mtsConfig = new File(pluginFolder, "MTS_config.yml");

	if (!pluginFolder.exists()) {
	    try {
		pluginFolder.mkdir();
	    } catch (Exception e) {
		System.out.println("MTS Config Create Fehler:\n");
		e.printStackTrace();
	    }
	}

	if (!mtsConfig.exists()) {
	    try {
		mtsConfig.createNewFile();
	    } catch (Exception e) {
		System.out.println("MTS Config Create Fehler:\n");
		e.printStackTrace();
	    }

	    config.set("MySQL.MineTime.Host", "host");
	    config.set("MySQL.MineTime.DB", "db");
	    config.set("MySQL.MineTime.User", "user");
	    config.set("MySQL.MineTime.Pass", "pass");

	    config.set("MySQL.MineTimeSurf.Host", "host");
	    config.set("MySQL.MineTimeSurf.DB", "db");
	    config.set("MySQL.MineTimeSurf.User", "user");
	    config.set("MySQL.MineTimeSurf.Pass", "pass");
	    config.set("JoinMSG.Nachricht", "Hey Willkommen!");
	    config.set("Whitelist.Enabled", false);
	    config.set("Whitelist.Nachricht", "§Serverwartungen");

	    try {
		config.save(mtsConfig);
	    } catch (Exception e) {
		System.out.println("MTS Config Save Fehler:\n");
		e.printStackTrace();
	    }
	}

	try {
	    config.load(mtsConfig);
	} catch (Exception e) {
	    System.out.println("MTS Config Load Fehler:\n");
	    e.printStackTrace();
	}

	config.set(path, value);

	try {
	    config.save(mtsConfig);
	} catch (Exception e) {
	    System.out.println("MTS Config Save Fehler:\n");
	    e.printStackTrace();
	}

    }

    @Override
    public boolean getBoolean(String path) {
	boolean data = false;

	YamlConfiguration config = new YamlConfiguration();
	File pluginFolder = new File("plugins", "MTS");
	File mtsConfig = new File(pluginFolder, "MTS_config.yml");

	if (!pluginFolder.exists()) {
	    try {
		pluginFolder.mkdir();
	    } catch (Exception e) {
		System.out.println("MTS Config Create Fehler:\n");
		e.printStackTrace();
	    }
	}

	if (!mtsConfig.exists()) {
	    try {
		mtsConfig.createNewFile();
	    } catch (Exception e) {
		System.out.println("MTS Config Create Fehler:\n");
		e.printStackTrace();
	    }

	    config.set("MySQL.MineTime.Host", "host");
	    config.set("MySQL.MineTime.DB", "db");
	    config.set("MySQL.MineTime.User", "user");
	    config.set("MySQL.MineTime.Pass", "pass");

	    config.set("MySQL.MineTimeSurf.Host", "host");
	    config.set("MySQL.MineTimeSurf.DB", "db");
	    config.set("MySQL.MineTimeSurf.User", "user");
	    config.set("MySQL.MineTimeSurf.Pass", "pass");
	    config.set("JoinMSG.Nachricht", "Hey Willkommen!");
	    config.set("Whitelist.Enabled", false);
	    config.set("Whitelist.Nachricht", "§Serverwartungen");

	    try {
		config.save(mtsConfig);
	    } catch (Exception e) {
		System.out.println("MTS Config Save Fehler:\n");
		e.printStackTrace();
	    }
	}

	try {
	    config.load(mtsConfig);
	} catch (Exception e) {
	    System.out.println("MTS Config Load Fehler:\n");
	    e.printStackTrace();
	}

	data = config.getBoolean(path);

	return data;
    }

    @Override
    public void setBoolean(String path, boolean value) {

	YamlConfiguration config = new YamlConfiguration();
	File pluginFolder = new File("plugins", "MTS");
	File mtsConfig = new File(pluginFolder, "MTS_config.yml");

	if (!pluginFolder.exists()) {
	    try {
		pluginFolder.mkdir();
	    } catch (Exception e) {
		System.out.println("MTS Config Create Fehler:\n");
		e.printStackTrace();
	    }
	}

	if (!mtsConfig.exists()) {
	    try {
		mtsConfig.createNewFile();
	    } catch (Exception e) {
		System.out.println("MTS Config Create Fehler:\n");
		e.printStackTrace();
	    }

	    config.set("MySQL.MineTime.Host", "host");
	    config.set("MySQL.MineTime.DB", "db");
	    config.set("MySQL.MineTime.User", "user");
	    config.set("MySQL.MineTime.Pass", "pass");

	    config.set("MySQL.MineTimeSurf.Host", "host");
	    config.set("MySQL.MineTimeSurf.DB", "db");
	    config.set("MySQL.MineTimeSurf.User", "user");
	    config.set("MySQL.MineTimeSurf.Pass", "pass");
	    config.set("JoinMSG.Nachricht", "Hey Willkommen!");
	    config.set("Whitelist.Enabled", false);
	    config.set("Whitelist.Nachricht", "§Serverwartungen");

	    try {
		config.save(mtsConfig);
	    } catch (Exception e) {
		System.out.println("MTS Config Save Fehler:\n");
		e.printStackTrace();
	    }
	}

	try {
	    config.load(mtsConfig);
	} catch (Exception e) {
	    System.out.println("MTS Config Load Fehler:\n");
	    e.printStackTrace();
	}

	config.set(path, value);

	try {
	    config.save(mtsConfig);
	} catch (Exception e) {
	    System.out.println("MTS Config Save Fehler:\n");
	    e.printStackTrace();
	}

    }

}
