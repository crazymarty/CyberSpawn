package dev.crazymarty.cyberspawn;

import dev.crazymarty.cyberspawn.commands.Main;
import dev.crazymarty.cyberspawn.commands.SetSpawn;
import dev.crazymarty.cyberspawn.commands.Spawn;
import dev.crazymarty.cyberspawn.config.MainConfig;
import dev.crazymarty.cyberspawn.database.SQLiteData;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Objects;

public final class CyberSpawn extends JavaPlugin {

    @Getter
    private static CyberSpawn instance;
    private static boolean legacyColor;
    @Getter
    private MainConfig mainConfig;
    private SQLiteData pluginData;


    @Override
    public void onEnable() {
        instance = this;

        try {
            loadConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        connectPluginData();
        registerCommands();
    }

    @Override
    public void onDisable() {
        disconnectPluginData();
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("setspawn")).setExecutor(new SetSpawn(this));
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new Spawn(this));
        Objects.requireNonNull(getCommand("cyberspawn")).setExecutor(new Main(this));
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

    private void loadConfig() throws IOException {
        mainConfig = new MainConfig();
        legacyColor = getConfig().getBoolean("settings.legacyColors");
    }


    public static boolean getLegacyColor() {return legacyColor;}
}
