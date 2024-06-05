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
//    private final Configuration config;
//    private final MainConfig config;
    public static Location spawnLocation;


    public SetSpawn(CyberSpawn cyberSpawn) {
        this.instance = cyberSpawn.getInstance();
//        this.config = cyberSpawn.getMainConfig();
        spawnLocation = cyberSpawn.getSpawnLocation();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[SPAWN] This command is can only be used by players.");
            return true;
        }
        Player player = (Player) sender;
        if (!MainConfig.settings.enabled) {
            String prefix = MainConfig.messages.prefix;
            String disabledMessage = MainConfig.messages.pluginDisabled;
            disabledMessage = disabledMessage.replace("{prefix}", prefix);
            sendMessage(player, disabledMessage);
            return true;
        }
        String setSpawnPerm = Objects.requireNonNull(MainConfig.permissions.setSpawn);
        if (!player.hasPermission(setSpawnPerm)) {
            String prefix = MainConfig.messages.prefix; // .getString("messages.prefix");
            String noPerm = MainConfig.messages.noPermission; //.getString("messages.noPermission");
            noPerm = noPerm.replace("{prefix}", prefix);
            sendMessage(player, noPerm);
            return true;
        }
        spawnLocation = instance.setSpawnLocation(player.getLocation());
        String prefix = MainConfig.messages.prefix; // .getString("messages.prefix");
        String spawnSet = MainConfig.messages.spawnSet; //.getString("messages.spawnSet");
        spawnSet = spawnSet.replace("{prefix}", prefix);
        String x = String.valueOf(spawnLocation.getX());
        String y = String.valueOf(spawnLocation.getY());
        String z = String.valueOf(spawnLocation.getZ());
        String world = spawnLocation.getWorld().getName();
        spawnSet = spawnSet.replace("{loc}", x + ", " + y + ", " + z + " in " + world);
        sendMessage(player, spawnSet);
        return true;
    }

}
