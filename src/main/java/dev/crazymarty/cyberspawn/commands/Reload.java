package dev.crazymarty.cyberspawn.commands;

import dev.crazymarty.cyberspawn.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.jetbrains.annotations.NotNull;


import static dev.crazymarty.cyberspawn.utils.TextFormatter.sendMessage;

public class Reload implements CommandExecutor {

    private final Core instance;
    private final Configuration config;

    public Reload(Core core) {
        this.instance = core.getInstance();
        this.config = core.getConfig();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        String reloadPerms = config.getString("permission.reloadPlugin");
        if (!sender.hasPermission(reloadPerms)) {
            String prefix = config.getString("messages.prefix");
            String noPerm = config.getString("messages.noPermission");
            noPerm = noPerm.replace("{prefix}", prefix);
            sendMessage(sender, noPerm);
            return true;
        }
        instance.saveDefaultConfig();
        String configReload = config.getString("messages.configReload");
        String prefix = config.getString("messages.prefix");
        configReload = configReload.replace("{prefix}", prefix);
        sendMessage(sender, configReload);
        return true;
    }
}
