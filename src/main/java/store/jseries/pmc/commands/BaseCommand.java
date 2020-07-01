package store.jseries.pmc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import store.jseries.pmc.managers.InventoryManager;

import java.util.List;

public class BaseCommand {

    public static void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            ((Player) sender).openInventory(InventoryManager.getMain());
        } else {
            sender.sendMessage(ChatColor.RED + "Only players can do this.");
        }
    }
}
