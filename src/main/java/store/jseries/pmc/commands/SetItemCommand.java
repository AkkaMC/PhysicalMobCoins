package store.jseries.pmc.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import store.jseries.pmc.PhysicalMobCoins;
import store.jseries.pmc.utils.Skin;
import store.jseries.pmc.utils.warse.ItemStackBuilder;
import store.jseries.pmc.utils.warse.xseries.XMaterial;

import java.util.List;

public class SetItemCommand {

    public static void execute(CommandSender sender, String[] args) {
        if(args.length < 2) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "/pmc setitem (item_name)");
                return;
            }
            ItemStack item = ((Player) sender).getInventory().getItem(((Player) sender).getInventory().getHeldItemSlot());
            if(item == null || item.getType() == Material.AIR) {
                sender.sendMessage(ChatColor.RED + "You can't set the item type to air.");
                return;
            }
            item = item.clone();
            PhysicalMobCoins.getInstance().getCoinManager().setCoinItem(item, Skin.CUSTOM);
            sender.sendMessage(ChatColor.GREEN + "Successfully set the coin item.");
            return;
        }
        try {
            XMaterial mat = XMaterial.valueOf(args[1].replaceAll(" ", "_").toUpperCase());
            if(mat.parseMaterial() == null) {
                sender.sendMessage(ChatColor.RED + "Invalid material name " + args[1]);
                return;
            }
            PhysicalMobCoins.getInstance().getCoinManager().setCoinItem(new ItemStackBuilder(mat).build(), Skin.CUSTOM);
            sender.sendMessage(ChatColor.GREEN + "Successfully set the coin item.");
        } catch (Exception exception) {
            sender.sendMessage(ChatColor.RED + "Invalid material name " + args[1]);
            return;
        }
    }
}
