package dev.crazymarty.cyberspawn.commands;

import dev.crazymarty.cyberspawn.CyberSpawn;
import dev.crazymarty.cyberspawn.config.ConfigManager;
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
    private final MainConfig mainConfig;

    public Main(CyberSpawn cyberSpawn) {
        this.instance = cyberSpawn;
        this.mainConfig = cyberSpawn.getMainConfig();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("Help message");
            return true;
        }
        if (!args[0].equalsIgnoreCase("reload")) return false;
        String message;
        if (!sender.hasPermission(MainConfig.permissions.reloadPlugin())) {
            message = MainConfig.messages.noPermission();
        } else message = MainConfig.messages.configReload();
        if (message.contains("{prefix}"))
            message = message.replace("{prefix}",MainConfig.messages.prefix());
        sendMessage(sender, message);
        try {
            mainConfig.reload();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
