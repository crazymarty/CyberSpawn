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

        if (!player.hasPermission(MainConfig.permissions.teleportToSpawn())) {
            sendMessage(player, MainConfig.messages.noPermission().replace("{prefix}", MainConfig.messages.prefix()));
            return true;
        }

        Location spawnLocation = instance.getSpawnLocation();
        if (spawnLocation == null) {
            sendMessage(player, MainConfig.messages.spawnNotSet().replace("{prefix}", MainConfig.messages.prefix()));
            return true;
        }

        int warmupTime = MainConfig.settings.spawnTpWarmup();
        if (warmupTime > 0) {
            Location playerLocation = player.getLocation();
            String warmupMessage = MainConfig.messages.spawnTpWarmupMsg()
                    .replace("{warmup}", String.valueOf(warmupTime))
                    .replace("{prefix}", MainConfig.messages.prefix());
            sendMessage(player, warmupMessage);

            instance.getServer().getScheduler().runTaskLater(instance, runnable -> {
                if (player.getLocation().distance(playerLocation) > 0.1) {
                    String movedMessage = MainConfig.messages.playerMoved()
                            .replace("{prefix}", MainConfig.messages.prefix());
                    sendMessage(player, movedMessage);
                    runnable.cancel();
                } else {
                    String teleportMessage = MainConfig.messages.spawnTP()
                            .replace("{prefix}", MainConfig.messages.prefix());
                    player.teleport(spawnLocation);
                    sendMessage(player, teleportMessage);
                }
            }, 20L * warmupTime);
        } else {
            String teleportMessage = MainConfig.messages.spawnTP()
                    .replace("{prefix}", MainConfig.messages.prefix());
            player.teleport(spawnLocation);
            sendMessage(player, teleportMessage);
        }

        return true;
    }
}
