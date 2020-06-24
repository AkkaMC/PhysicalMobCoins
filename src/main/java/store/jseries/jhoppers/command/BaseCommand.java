package store.jseries.jhoppers.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import store.jseries.framework.utils.JUtils;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.inventories.MainInventory;
import store.jseries.jhoppers.utils.enums.Message;
import store.jseries.jhoppers.utils.enums.Permission;

public class BaseCommand extends JUtils {

    public static void handle(CommandSender sender, String[] args) {
        if(!sender.hasPermission(JHoppers.getInstance().getPermissionManager().getPermission(Permission.ADMIN_PERMISSION))) {
            sender.sendMessage(Message.NO_PERMISSION.getMessage());
            return;
        }
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can do this command.");
            return;
        }
        new MainInventory().open((Player) sender, 1);
    }
}
