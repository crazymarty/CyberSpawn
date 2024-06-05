package dev.crazymarty.cyberspawn.config;

import lombok.Getter;

import java.io.IOException;

@Getter
public class MainConfig extends ConfigManager {


    public MainConfig() throws IOException {
        super("config.yml",false,true);
        loadData();
    }

    public void reload() throws Exception {
        super.reload();
        loadData();
    }

    // Plugin settings config
    public record settings() {
        public static boolean enabled;
        public static boolean legacyColors;
        public static int spawnTpWarmup;
        public static boolean canPlayerMove;
        public static boolean teleportFirstJoin;
        public static boolean sendFirstJoinMessage;
    }

    // Messages config
    public record messages() {
        public static String prefix;
        public static String configReload;
        public static String pluginDisabled;
        public static String firstJoin;
        public static String spawnSet;
        public static String spawnTp;
        public static String spawnTpCooldown;
        public static String noPermission;
        public static String spawnNotSet;
    }

    // Permission config
    public record permissions() {
        public static String setSpawn;
        public static String teleportToSpawn;
        public static String teleportOthersToSpawn;
        public static String bypassWarmup;
        public static String reloadPlugin;
    }

    private void loadData() {
        settings.enabled = this.getBoolean("settings.enabled");
        settings.legacyColors = this.getBoolean("settings.legacyColors");
        settings.spawnTpWarmup = this.getInt("settings.spawnTpWarmup");
        settings.canPlayerMove = this.getBoolean("settings.canPlayerMove");
        settings.teleportFirstJoin = this.getBoolean("settings.teleportFirstJoin");
        settings.sendFirstJoinMessage = this.getBoolean("settings.sendFirstJoinMessage");

        messages.prefix = this.getString("messages.prefix");
        messages.configReload = this.getString("messages.configReload");
        messages.pluginDisabled = this.getString("messages.pluginDisabled");
        messages.firstJoin = this.getString("messages.firstJoin");
        messages.spawnSet = this.getString("messages.spawnSet");
        messages.spawnTp = this.getString("messages.spawnTp");
        messages.spawnTpCooldown = this.getString("messages.spawnTpCooldown");
        messages.noPermission = this.getString("messages.noPermission");
        messages.spawnNotSet = this.getString("messages.spawnNotSet");

        // Permission config

        permissions.setSpawn = this.getString("messages.setSpawn");
        permissions.teleportToSpawn = this.getString("messages.teleportToSpawn");
        permissions.teleportOthersToSpawn = this.getString("messages.teleportOthersToSpawn");
        permissions.bypassWarmup = this.getString("messages.bypassWarmup");
        permissions.reloadPlugin = this.getString("messages.reloadPlugin");
    }

}
