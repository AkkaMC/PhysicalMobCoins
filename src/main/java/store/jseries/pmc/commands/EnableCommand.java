package store.jseries.pmc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import store.jseries.pmc.PhysicalMobCoins;

import java.util.List;

public class EnableCommand {

    public static void execute(CommandSender sender, String[] args) {
        boolean enabled = PhysicalMobCoins.getInstance().getCoinManager().isEnabled();
        if(enabled) {
            sender.sendMessage(ChatColor.RED + "PhysicalMobCoins is already enabled.");
            return;
        }
        PhysicalMobCoins.getInstance().getCoinManager().setEnabled(true);
        sender.sendMessage(ChatColor.GREEN + "PhysicalMobCoins has been enabled.");
    }
}
