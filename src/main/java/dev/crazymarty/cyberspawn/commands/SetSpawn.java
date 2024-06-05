package dev.crazymarty.cyberspawn.commands;

import dev.crazymarty.cyberspawn.CyberSpawn;
import dev.crazymarty.cyberspawn.config.MainConfig;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static dev.crazymarty.cyberspawn.utils.TextFormatter.sendMessage;

public class SetSpawn implements CommandExecutor {

    private final CyberSpawn instance;

    public SetSpawn(CyberSpawn cyberSpawn) {
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
        }
        String setSpawnPerm = MainConfig.permissions.setSpawn();
        if (!player.hasPermission(setSpawnPerm)) {
            message = MainConfig.messages.noPermission();
        }else {
            Location spawnLocation = instance.setSpawnLocation(player.getLocation());
            message = MainConfig.messages.spawnSet();
            if (message.contains("{loc}"))
                message = message.replace("{loc}",
                        spawnLocation.getX()  + ", " + spawnLocation.getY() + ", " + spawnLocation.getZ() + " in " + spawnLocation.getWorld().getName());
        }
        if (message.contains("{prefix}"))
            message = message.replace("{prefix}", MainConfig.messages.prefix());
        sendMessage(sender, message);
        return true;
    }

}
