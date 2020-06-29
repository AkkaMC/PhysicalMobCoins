package store.jseries.jhoppers.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import store.jseries.framework.utils.JUtils;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.inventories.HopperEditInventory;
import store.jseries.jhoppers.utils.enums.Message;
import store.jseries.jhoppers.utils.enums.Permission;
import store.jseries.jhoppers.utils.hopper.HopperType;

public class DeleteCommand extends JUtils {

    public static void handle(CommandSender sender, String[] args) {
        if(!sender.hasPermission(JHoppers.getInstance().getPermissionManager().getPermission(Permission.ADMIN_PERMISSION))) {
            sender.sendMessage(Message.NO_PERMISSION.getMessage());
            return;
        }
        if(args.length < 2) {
            sender.sendMessage(ChatColor.RED + "/jh delete (name)");
            return;
        }
        HopperType type = JHoppers.getInstance().getHopperTypeManager().getType(args[1]);
        if(type == null) {
            sender.sendMessage(ChatColor.RED + "That hopper type doesn't exist.");
            return;
        }
        JHoppers.getInstance().getHopperTypeManager().deleteType(type);
        sender.sendMessage(Message.DELETED_TYPE.getMessage());
    }

}
