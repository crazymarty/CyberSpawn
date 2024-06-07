package dev.crazymarty.cyberspawn.utils;

import dev.crazymarty.cyberspawn.CyberSpawn;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import org.bukkit.command.CommandSender;

public class TextFormatter {


    public static Component translateLegacyColor(String string) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(string);
    }

    public static Component translateModernColorCode(String string) {
        return MiniMessage.miniMessage().deserialize(string);
    }

    public static void sendMessage(CommandSender sender, String string) {
        if (CyberSpawn.getLegacyColor()) {
            sender.sendMessage(translateLegacyColor(string));
//            sender.sendMessage(string);
        } else {
            sender.sendMessage(translateModernColorCode(string));
        }
    }

    public static Component returnMessage(CommandSender sender, String string) {
        if (CyberSpawn.getLegacyColor()) {
            return translateLegacyColor(string);
//            return string;
        } else {
            return translateModernColorCode(string);
        }
    }
}
