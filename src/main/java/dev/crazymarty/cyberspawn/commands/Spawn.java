package dev.crazymarty.cyberspawn.commands;

import dev.crazymarty.cyberspawn.CyberSpawn;
import dev.crazymarty.cyberspawn.config.MainConfig;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


import static dev.crazymarty.cyberspawn.commands.SetSpawn.spawnLocation;
import static dev.crazymarty.cyberspawn.utils.TextFormatter.sendMessage;

public class Spawn implements CommandExecutor {

    private final CyberSpawn instance;
//    private final MainConfig config;

    public Spawn(CyberSpawn cyberSpawn) {
        this.instance = cyberSpawn.getInstance();
//        this.config = cyberSpawn.getMainConfig();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[SPAWN] This command can only be executed by players.");
            return true;
        }
        Player player = (Player) sender;
        String spawnTpPerm = MainConfig.permissions.teleportToSpawn;; // .getString("permissions.teleportToSpawn");
        if (!player.hasPermission(spawnTpPerm)) {
            String prefix = MainConfig.messages.prefix; //config.getString("messages.prefix");
            String noPerm = MainConfig.messages.noPermission; // config.getString("messages.noPermission");
            noPerm = noPerm.replace("{prefix}", prefix);
            sendMessage(player, noPerm);
            return true;
        }
        if (spawnLocation == null) {
            String prefix = MainConfig.messages.prefix; // .getString("messages.prefix");
            String spawnNotSet = MainConfig.messages.spawnNotSet; // config.getString("messages.spawnNotSet");
            spawnNotSet = spawnNotSet.replace("{prefix}", prefix);
            sendMessage(player, spawnNotSet);
            return true;
        }
        Location spawnLocation = instance.getSpawnLocation();
        player.teleport(spawnLocation);


        return true;
    }
}
