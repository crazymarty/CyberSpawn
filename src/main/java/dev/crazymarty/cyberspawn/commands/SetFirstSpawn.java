package dev.crazymarty.cyberspawn.commands;

import dev.crazymarty.cyberspawn.CyberSpawn;
import dev.crazymarty.cyberspawn.config.MainConfig;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static dev.crazymarty.cyberspawn.utils.TextFormatter.sendMessage;

public class SetFirstSpawn implements CommandExecutor {

    private final CyberSpawn instance;

    public SetFirstSpawn(CyberSpawn cyberSpawn) {
        this.instance = cyberSpawn;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("[SPAWN] This command is can only be used by players.");
            return true;
        }
        String message;
        if (!MainConfig.settings.enabled()) {
            message = MainConfig.messages.pluginDisabled();
            sendMessage(player, message);
            return true;
        }
        String setSpawnPerm = MainConfig.permissions.setSpawn();
        if (!player.hasPermission(setSpawnPerm)) {
            message = MainConfig.messages.noPermission();
        }else {
            Location spawnLocation = instance.setSpawnLocation(player.getLocation());
            message = MainConfig.messages.spawnSet();
            if (message.contains("{loc}"))
                message = message.replace("{loc}",
                        Math.round(spawnLocation.getX())  + ", " + Math.round(spawnLocation.getY()) + ", " + Math.round(spawnLocation.getZ()) + ", " + spawnLocation.getWorld().getName());
        }
        if (message.contains("{prefix}"))
            message = message.replace("{prefix}", MainConfig.messages.prefix());
        sendMessage(sender, message);
        return true;
    }

}
