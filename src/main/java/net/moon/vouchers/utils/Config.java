package net.moon.vouchers.utils;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Config extends YamlConfiguration {

    private final File file;

    public Config(JavaPlugin plugin, String name) {
        this.file = new File(plugin.getDataFolder().getAbsolutePath(), name + ".yml");

        if (!this.file.exists()) {
            plugin.saveResource(name + ".yml", false);
        }

        this.load();
    }

    public void load() {
        try {
            this.load(this.file);
        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
    }
}
