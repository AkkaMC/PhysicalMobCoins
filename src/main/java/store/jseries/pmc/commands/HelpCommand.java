package store.jseries.pmc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import store.jseries.pmc.utils.Message;

public class HelpCommand {

    public static void execute(CommandSender sender, String[] args) {
        for(String s : Message.HELP_MESSAGE.getMessage(true))
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
    }
}
