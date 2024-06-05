package dev.crazymarty.cyberspawn.events;

import dev.crazymarty.cyberspawn.CyberSpawn;
import dev.crazymarty.cyberspawn.config.MainConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import static dev.crazymarty.cyberspawn.utils.TextFormatter.returnMessage;

public class PlayerEventHandler implements Listener {

    private final CyberSpawn instance;

    public PlayerEventHandler(CyberSpawn cyberSpawn) {
        instance = cyberSpawn;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String message;
        if (!player.hasPlayedBefore()) {
            if (!MainConfig.settings.sendFirstJoinMessage()) {return;}
            message = MainConfig.messages.firstJoin();
            message = message.replace("{player}", player.getName());
            Component msgcomp = returnMessage(player, message);
            event.joinMessage(msgcomp);
            // Check whether new players should be teleported to spawn, if false then omit the whole process.
            if (!MainConfig.settings.teleportFirstJoin()) {return;}
            // If above is true then check if in config players can teleport to first spawn
            // If yes then do stuff
            if (MainConfig.settings.teleportToFirstSpawn()) {
                 Location spawn;
                 // If first spawn location is not set, teleport to default spawn
                 if (instance.getFirstSpawnLocation() == null) {
                     spawn = instance.getSpawnLocation();
                     player.teleport(spawn);
                     return;
                 }
            }
            player.teleport(instance.getFirstSpawnLocation());
        } else {
            if (!MainConfig.settings.sendJoinLeaveMessage()) {return;}
            message = MainConfig.messages.playerJoin();
            message = message.replace("{player}", player.getName());
            Component msgcomp = returnMessage(player, message);
            event.joinMessage(msgcomp);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String message;
        if (!MainConfig.settings.sendFirstJoinMessage()) {return;}
        message = MainConfig.messages.playerLeave();
        message = message.replace("{player}", player.getName());
        Component msgcomp = returnMessage(player, message);
        event.quitMessage(msgcomp);
    }

//    @EventHandler
//    public void onPlayerDeath(PlayerDeathEvent event) {
//        Player player = event.getPlayer();
//        if (!MainConfig.settings.teleportToSpawnOnDeath()) {return;}
//        if (MainConfig.settings.teleportToBedIfAvailable()) {
//            if (player.getPotentialBedLocation() != null) {
//                Location bedSpawn = player.getPotentialBedLocation();
//                player.setRespawnLocation(bedSpawn);
//                return;
//            }
//        }
//        Location spawn = instance.getSpawnLocation();
//        player.setRespawnLocation(spawn);
//    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (!MainConfig.settings.teleportToSpawnOnDeath()) {return;}
        if (MainConfig.settings.teleportToBedIfAvailable()) {
            if (player.getPotentialBedLocation() != null) {
                Location bedSpawn = player.getPotentialBedLocation();
                event.setRespawnLocation(bedSpawn);
                return;
            }
        }
        Location spawn = instance.getSpawnLocation();
        event.setRespawnLocation(spawn);
    }

}