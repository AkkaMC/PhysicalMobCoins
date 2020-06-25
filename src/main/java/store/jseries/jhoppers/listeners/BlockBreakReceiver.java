package store.jseries.jhoppers.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.inventories.HopperInventory;
import store.jseries.jhoppers.utils.enums.HopperPermission;
import store.jseries.jhoppers.utils.enums.Message;
import store.jseries.jhoppers.utils.enums.Permission;
import store.jseries.jhoppers.utils.hopper.JHopper;

public class BlockBreakReceiver implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        JHopper hopper = JHoppers.getInstance().getHopperManager().getJHopper(e.getBlock().getLocation());
        if (hopper != null) {
            e.setCancelled(true);
            if (e.getPlayer().isSneaking()) {
                if (!e.getPlayer().hasPermission(JHoppers.getInstance().getPermissionManager().getPermission(Permission.ADMIN_PERMISSION)) && !hopper.hasPermission(e.getPlayer().getUniqueId(), HopperPermission.BREAK)) {
                    e.getPlayer().sendMessage(Message.NO_HOPPER_PERMISSION.getMessage());
                    return;
                }
                e.getBlock().setType(Material.AIR);
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), JHoppers.getInstance().getHopperTypeManager().getType(hopper.getHopperType()).getItem());
                JHoppers.getInstance().getHopperManager().removeHopper(hopper);
            } else {
                if (hopper.hasPermission(e.getPlayer().getUniqueId(), HopperPermission.BREAK))
                    e.getPlayer().sendMessage(Message.NO_HOPPER_PERMISSION.getMessage());
            }
        }
    }

}
