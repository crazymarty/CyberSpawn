package dev.crazymarty.cyberspawn.commands;

import dev.crazymarty.cyberspawn.Core;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


import static dev.crazymarty.cyberspawn.commands.SetSpawn.spawnLocation;
import static dev.crazymarty.cyberspawn.utils.TextFormatter.sendMessage;

public class Spawn implements CommandExecutor {

    private final Core instance;
    private final Configuration config;

    public Spawn(Core core) {
        this.instance = core.getInstance();
        this.config = core.getConfig();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[SPAWN] This command can only be executed by players.");
            return true;
        }
        Player player = (Player) sender;
        String spawnTpPerm = config.getString("permissions.teleportToSpawn");
        if (!player.hasPermission(spawnTpPerm)) {
            String prefix = config.getString("messages.prefix");
            String noPerm = config.getString("messages.noPermission");
            noPerm = noPerm.replace("{prefix}", prefix);
            sendMessage(player, noPerm);
            return true;
        }
        if (spawnLocation == null) {
            String prefix = config.getString("messages.prefix");
            String spawnNotSet = config.getString("messages.spawnNotSet");
            spawnNotSet = spawnNotSet.replace("{prefix}", prefix);
            sendMessage(player, spawnNotSet);
            return true;
        }
        Location spawnLocation = instance.getSpawnLocation();
        player.teleport(spawnLocation);


        return true;
    }
}
