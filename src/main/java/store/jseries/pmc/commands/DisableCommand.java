package store.jseries.pmc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import store.jseries.pmc.PhysicalMobCoins;

import java.util.List;

public class DisableCommand {

    public static void execute(CommandSender sender, String[] args) {
        boolean disabled = !PhysicalMobCoins.getInstance().getCoinManager().isEnabled();
        if(disabled) {
            sender.sendMessage(ChatColor.RED + "PhysicalMobCoins is already disabled.");
            return;
        }
        PhysicalMobCoins.getInstance().getCoinManager().setEnabled(false);
        sender.sendMessage(ChatColor.GREEN + "PhysicalMobCoins has been disabled.");
    }
}
