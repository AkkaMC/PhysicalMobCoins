package store.jseries.jhoppers.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.utils.enums.Message;
import store.jseries.jhoppers.utils.enums.Permission;
import store.jseries.framework.utils.JUtils;
import store.jseries.jhoppers.utils.hopper.HopperType;

public class GiveCommand extends JUtils {

    public static void handle(CommandSender sender, String[] args) {
        if(!sender.hasPermission(JHoppers.getInstance().getPermissionManager().getPermission(Permission.ADMIN_PERMISSION))) {
            sender.sendMessage(Message.NO_PERMISSION.getMessage());
            return;
        }
        if(args.length<2) {
            sender.sendMessage(ChatColor.RED + "/jh give [type] (player) (amount)");
            return;
        }
        if(args.length == 2 && ! (sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You can't give yourself a hopper.");
            return;
        }
        HopperType type = JHoppers.getInstance().getHopperTypeManager().getType(args[1]);
        if(type == null) {
            sender.sendMessage(ChatColor.RED + "Unknown hopper type " + args[1] + ".");
            return;
        }
        if(args.length == 2) {
            sender.sendMessage(ChatColor.GREEN + "You gave yourself a " + args[0] + " hopper.");
            ((Player) sender).getInventory().addItem(type.getItem());
            return;
        }
        Player player = Bukkit.getPlayer(args[2]);
        if(player == null) {
            sender.sendMessage(ChatColor.RED + "That is an invalid player.");
            return;
        }
        int amt = 1;
        if(args.length>=4) {
            try {
                amt = Integer.parseInt(args[3]);
            } catch (Exception ex) {
                sender.sendMessage(ChatColor.RED + "That is not a valid number.");
                return;
            }
        }
        player.getInventory().addItem(type.getItem(amt));
    }
}
