package dev.crazymarty.cyberspawn;

import dev.crazymarty.cyberspawn.commands.Reload;
import dev.crazymarty.cyberspawn.commands.SetSpawn;
import dev.crazymarty.cyberspawn.commands.Spawn;
import dev.crazymarty.cyberspawn.database.SQLiteData;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Core extends JavaPlugin {

    private static Core instance;
    private static boolean legacyColor;
    private SQLiteData pluginData;

    // Plugin startup logic
    @Override
    public void onEnable() {
        instance = this;

        loadConfig();
        connectPluginData();
        registerCommands();
    }

    // Plugin shutdown logic
    @Override
    public void onDisable() {
        disconnectPluginData();
    }

    // Register commands
    private void registerCommands() {
        Objects.requireNonNull(getCommand("setspawn")).setExecutor(new SetSpawn(this));
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new Spawn(this));
        Objects.requireNonNull(getCommand("reload")).setExecutor(new Reload(this));
    }

    private void connectPluginData() {
        pluginData = new SQLiteData(getDataFolder().getAbsolutePath() + "/cyberspawn.db");
    }

    public void disconnectPluginData() {
        pluginData.closeConnection();
    }

    public Location setSpawnLocation(Location location) {
        return pluginData.setSpawnLocation(location);
    }

    public Location getSpawnLocation() {
        return pluginData.getSpawnLocation();
    }

    private void loadConfig() {
        saveDefaultConfig();
        legacyColor = getConfig().getBoolean("settings.legacyColors");
    }

    public Core getInstance() {return instance;}
    public static boolean getLegacyColor() {return legacyColor;}
}
