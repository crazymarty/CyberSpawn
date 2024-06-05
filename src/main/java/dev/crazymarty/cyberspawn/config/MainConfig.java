package dev.crazymarty.cyberspawn.config;


import lombok.Getter;

import java.io.IOException;
import java.util.Set;

@Getter
public class MainConfig extends ConfigManager {

    public static Settings settings;
    public static Messages messages;
    public static Permissions permissions;


    public MainConfig() throws IOException {
        super("config.yml",false,true);
        loadData();
    }

    public void reload() throws Exception {
        super.reload();
        loadData();
    }

    // Plugin settings config
    public record Settings(boolean enabled,
                           boolean legacyColors, 
                           int spawnTpWarmup, 
                           boolean canPlayerMove, 
                           boolean teleportFirstJoin, 
                           boolean sendFirstJoinMessage) {
    }

    // Messages config
    public record Messages(String prefix,
                           String configReload, 
                           String pluginDisabled, 
                           String firstJoin, 
                           String spawnSet, 
                           String spawnTP, 
                           String spawnTpCoolDown, 
                           String noPermission, 
                           String spawnNotSet) {
    }

    // Permission config
    public record Permissions(String setSpawn,
                              String teleportToSpawn, 
                              String teleportOthersToSpawn, 
                              String bypassWarmup,
                              String reloadPlugin) {
    }

    private void loadData() {
        boolean enabled = this.getBoolean("settings.enabled");
        boolean legacyColors = this.getBoolean("settings.legacyColors");
        int spawnTpWarmup = this.getInt("settings.spawnTpWarmup");
        boolean canPlayerMove = this.getBoolean("settings.canPlayerMove");
        boolean teleportFirstJoin = this.getBoolean("settings.teleportFirstJoin");
        boolean sendFirstJoinMessage = this.getBoolean("settings.sendFirstJoinMessage");
        settings = new Settings(enabled,legacyColors,spawnTpWarmup,canPlayerMove,teleportFirstJoin,sendFirstJoinMessage);

        String prefix = this.getString("messages.prefix");
        String configReload = this.getString("messages.configReload");
        String pluginDisabled = this.getString("messages.pluginDisabled");
        String firstJoin = this.getString("messages.firstJoin");
        String spawnSet = this.getString("messages.spawnSet");
        String spawnTp = this.getString("messages.spawnTp");
        String spawnTpCoolDown = this.getString("messages.spawnTpCooldown");
        String noPermission = this.getString("messages.noPermission");
        String spawnNotSet = this.getString("messages.spawnNotSet");
        messages = new Messages(prefix,configReload,pluginDisabled,firstJoin,spawnSet,spawnTp,spawnTpCoolDown,noPermission,spawnNotSet);

        // Permission config

        String setSpawn = this.getString("messages.setSpawn");
        String teleportToSpawn = this.getString("messages.teleportToSpawn");
        String teleportOthersToSpawn = this.getString("messages.teleportOthersToSpawn");
        String bypassWarmup = this.getString("messages.bypassWarmup");
        String reloadPlugin = this.getString("messages.reloadPlugin");
        permissions = new Permissions(setSpawn,teleportToSpawn,teleportOthersToSpawn,bypassWarmup,reloadPlugin);
    }

}
