package store.jseries.pmc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SupportCommand {

    public static void execute(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.BLUE + ChatColor.BOLD.toString() + "Support Discord: " + ChatColor.GRAY + "https://discord.jseries.store");
    }

}
