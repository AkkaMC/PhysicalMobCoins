package store.jseries.pmc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import store.jseries.pmc.PhysicalMobCoins;
import store.jseries.pmc.utils.Message;

import java.io.File;

public class ReloadCommand {

    public static void execute(CommandSender sender, String[] args) {
        Message.setConfig(YamlConfiguration.loadConfiguration(new File(PhysicalMobCoins.getInstance().getDataFolder(), "messages.yml")));
        sender.sendMessage(ChatColor.GREEN + "Reloaded messages.yml.");
    }

}
