package dev.crazymarty.cyberspawn.commands;

import dev.crazymarty.cyberspawn.CyberSpawn;
import dev.crazymarty.cyberspawn.config.MainConfig;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
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
            message = MainConfig.messages.spawnNotSet();
        }else {
            int warmupTime = MainConfig.settings.spawnTpWarmup();
            if (warmupTime != 0) {
                Location playerLocation = player.getLocation();
                player.sendMessage(" Here -> " + playerLocation);
                String msg = MainConfig.messages.spawnTpWarmupMsg();
                msg = msg.replace("{warmup}", String.valueOf(warmupTime));
                msg = msg.replace("{prefix}", MainConfig.messages.prefix());
                sendMessage(player, msg);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (player.getLocation() != playerLocation) {
                            player.sendMessage(" Here Second -> " + player.getLocation());
                            String msg = MainConfig.messages.playerMoved();
                            msg = msg.replace("{prefix}", MainConfig.messages.prefix());
                            sendMessage(player, msg);
                            cancel();
                        } else {
                            String message = MainConfig.messages.spawnTP();
                            message = message.replace("{prefix}", MainConfig.messages.prefix());
                            player.teleport(spawnLocation);
                            sendMessage(player, message);
                        }
                    }
                }.runTaskLater(instance, 20L * warmupTime);
            } else {
                message = MainConfig.messages.spawnTP();
                message = message.replace("{prefix}", MainConfig.messages.prefix());
                player.teleport(spawnLocation);
                sendMessage(player, message);
            }
        }
//        if (message.contains("{prefix}"))
//            message = message.replace("{prefix}", MainConfig.messages.prefix());
//        sendMessage(sender, message);
        return true;
    }

}
