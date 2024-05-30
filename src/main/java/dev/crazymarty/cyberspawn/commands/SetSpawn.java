package dev.crazymarty.cyberspawn.commands;

import dev.crazymarty.cyberspawn.Core;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static dev.crazymarty.cyberspawn.utils.TextFormatter.sendMessage;

public class SetSpawn implements CommandExecutor {

    private final Core instance;
    private final Configuration config;
    public static Location spawnLocation;


    public SetSpawn(Core core) {
        this.instance = core.getInstance();
        this.config = core.getConfig();
        spawnLocation = core.getSpawnLocation();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[SPAWN] This command is can only be used by players.");
            return true;
        }
        Player player = (Player) sender;
        if (!config.getBoolean("settings.enabled")) {
            String prefix = config.getString("messages.prefix");
            String disabledMessage = config.getString("messages.pluginDisabled");
            disabledMessage = disabledMessage.replace("{prefix}", prefix);
            sendMessage(player, disabledMessage);
            return true;
        }
        String setSpawnPerm = Objects.requireNonNull(config.getString("permissions.setSpawn"));
        if (!player.hasPermission(setSpawnPerm)) {
            String prefix = config.getString("messages.prefix");
            String noPerm = config.getString("messages.noPermission");
            noPerm = noPerm.replace("{prefix}", prefix);
            sendMessage(player, noPerm);
            return true;
        }
        spawnLocation = instance.setSpawnLocation(player.getLocation());
        String prefix = config.getString("messages.prefix");
        String spawnSet = config.getString("messages.spawnSet");
        spawnSet = spawnSet.replace("{prefix}", prefix);
        spawnSet = spawnSet.replace("{loc}", spawnLocation.toString());
        sendMessage(player, spawnSet);
        return true;
    }

}
