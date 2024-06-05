package dev.crazymarty.cyberspawn.commands;

import dev.crazymarty.cyberspawn.CyberSpawn;
import dev.crazymarty.cyberspawn.config.MainConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;

import static dev.crazymarty.cyberspawn.utils.TextFormatter.sendMessage;

public class Main implements CommandExecutor {

    private final CyberSpawn instance;
//    private final Configuration config;

    public Main(CyberSpawn cyberSpawn) {
        this.instance = cyberSpawn.getInstance();
//        this.config = cyberSpawn.getConfig();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Help message");
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            String reloadPerms = MainConfig.permissions.reloadPlugin; // .getString("permission.reloadPlugin");
            if (!sender.hasPermission(reloadPerms)) {
                String prefix = MainConfig.messages.prefix; // config.getString("messages.prefix");
                String noPerm = MainConfig.messages.noPermission; // config.getString("messages.noPermission");
                noPerm = noPerm.replace("{prefix}", prefix);
                sendMessage(sender, noPerm);
                return true;
            }
            instance.saveDefaultConfig();
            String configReload = MainConfig.messages.configReload; //config.getString("messages.configReload");
            String prefix = MainConfig.messages.prefix; // config.getString("messages.prefix");
            configReload = configReload.replace("{prefix}", prefix);
            sendMessage(sender, configReload);
            return true;
        }
        return true;
    }
}
