package store.jseries.jhoppers.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.inventories.HopperInventory;
import store.jseries.jhoppers.utils.enums.HopperPermission;
import store.jseries.jhoppers.utils.enums.Message;
import store.jseries.jhoppers.utils.enums.Permission;
import store.jseries.jhoppers.utils.hopper.JHopper;
import store.jseries.framework.utils.JUtils;

public class PlayerInteractReceiver extends JUtils implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() != null && e.getClickedBlock().getType() != Material.AIR) {
            JHopper hopper = JHoppers.getInstance().getHopperManager().getJHopper(e.getClickedBlock().getLocation());
            if(hopper != null) {
                if(e.getAction() == Action.LEFT_CLICK_BLOCK && e.getPlayer().isSneaking()) {
                    if(!e.getPlayer().hasPermission(JHoppers.getInstance().getPermissionManager().getPermission(Permission.ADMIN_PERMISSION)) && !hopper.hasPermission(e.getPlayer().getUniqueId(), HopperPermission.BREAK)) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(Message.NO_HOPPER_PERMISSION.getMessage());
                    }
                } else {
                    e.setCancelled(true);
                    if(!e.getPlayer().hasPermission(JHoppers.getInstance().getPermissionManager().getPermission(Permission.USE))) {
                        e.getPlayer().sendMessage(Message.NO_PERMISSION.getMessage());
                        return;
                    }
                    if(!hopper.getMembers().containsKey(e.getPlayer().getUniqueId())) {

                        e.getPlayer().sendMessage(Message.NO_HOPPER_PERMISSION.getMessage());
                        return;
                    }
                    new HopperInventory().open(e.getPlayer(),1,hopper);
                }
            }
        }
    }

}
