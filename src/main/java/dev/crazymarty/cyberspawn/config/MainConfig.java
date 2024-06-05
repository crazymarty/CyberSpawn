package dev.crazymarty.cyberspawn.config;


import lombok.Getter;

import java.io.IOException;

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
                           boolean teleportToFirstSpawn,
                           boolean sendJoinLeaveMessage,
                           boolean sendFirstJoinMessage,
                           boolean teleportToSpawnOnDeath,
                           boolean teleportToBedIfAvailable) {
    }

    // Messages config
    public record Messages(String prefix,
                           String configReload, 
                           String pluginDisabled,
                           String playerJoin,
                           String playerLeave,
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
        boolean teleportToFirstSpawn = this.getBoolean("settings.teleportToFirstSpawn");
        boolean sendJoinLeaveMessage = this.getBoolean("settings.sendJoinLeaveMessage");
        boolean sendFirstJoinMessage = this.getBoolean("settings.sendFirstJoinMessage");
        boolean teleportToSpawnOnDeath = this.getBoolean("settings.teleportToSpawnOnDeath");
        boolean teleportToBedIfAvailable = this.getBoolean("settings.teleportToBedIfAvailable");
        settings = new Settings(enabled,legacyColors,spawnTpWarmup,canPlayerMove,teleportFirstJoin,teleportToFirstSpawn, sendJoinLeaveMessage, sendFirstJoinMessage, teleportToSpawnOnDeath, teleportToBedIfAvailable);

        String prefix = this.getString("messages.prefix");
        String configReload = this.getString("messages.configReload");
        String pluginDisabled = this.getString("messages.pluginDisabled");
        String playerJoin = this.getString("messages.playerJoin");
        String playerLeave = this.getString("messages.playerLeave");
        String firstJoin = this.getString("messages.firstJoin");
        String spawnSet = this.getString("messages.spawnSet");
        String spawnTp = this.getString("messages.spawnTp");
        String spawnTpCoolDown = this.getString("messages.spawnTpCooldown");
        String noPermission = this.getString("messages.noPermission");
        String spawnNotSet = this.getString("messages.spawnNotSet");
        messages = new Messages(prefix,configReload,pluginDisabled,playerJoin, playerLeave, firstJoin,spawnSet,spawnTp,spawnTpCoolDown,noPermission,spawnNotSet);

        // Permission config

        String setSpawn = this.getString("permissions.setSpawn");
        String teleportToSpawn = this.getString("permissions.teleportToSpawn");
        String teleportOthersToSpawn = this.getString("permissions.teleportOthersToSpawn");
        String bypassWarmup = this.getString("permissions.bypassWarmup");
        String reloadPlugin = this.getString("permissions.reloadPlugin");
        permissions = new Permissions(setSpawn,teleportToSpawn,teleportOthersToSpawn,bypassWarmup,reloadPlugin);
    }

}
