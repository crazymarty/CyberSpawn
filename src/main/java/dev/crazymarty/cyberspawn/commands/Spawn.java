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

public class Spawn implements CommandExecutor {

    private final CyberSpawn instance;

    public Spawn(CyberSpawn cyberSpawn) {
        this.instance = cyberSpawn;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("[SPAWN] This command can only be executed by players.");
            return true;
        }
        Location spawnLocation = instance.getSpawnLocation();
        String message;
        if (!player.hasPermission(MainConfig.permissions.teleportToSpawn())) {
            message = MainConfig.messages.noPermission();
        }
        if (spawnLocation == null) {
            message = MainConfig.messages.spawnSet();
        }else {
            message = MainConfig.messages.spawnTP();
            player.teleport(spawnLocation);
        }
        if (message.contains("{prefix}"))
            message = message.replace("{prefix}", MainConfig.messages.prefix());
        sendMessage(sender, message);
        return true;
    }
}
