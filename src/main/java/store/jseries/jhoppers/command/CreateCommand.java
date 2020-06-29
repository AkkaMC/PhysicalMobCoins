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

public class CreateCommand extends JUtils {

    public static void handle(CommandSender sender, String[] args) {
        if(!sender.hasPermission(JHoppers.getInstance().getPermissionManager().getPermission(Permission.ADMIN_PERMISSION))) {
            sender.sendMessage(Message.NO_PERMISSION.getMessage());
            return;
        }
        if(args.length < 2) {
            sender.sendMessage(ChatColor.RED + "/jh create (name)");
            return;
        }
        HopperType type = JHoppers.getInstance().getHopperTypeManager().getType(args[1]);
        if(type != null) {
            sender.sendMessage(ChatColor.RED + "That hopper type already exists.");
            return;
        }
        type = JHoppers.getInstance().getHopperTypeManager().createType(args[1]);
        if(sender instanceof Player)
            new HopperEditInventory().open((Player) sender, type);
    }

}
