package store.jseries.jhoppers.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import store.jseries.framework.utils.JUtils;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.utils.enums.Message;
import store.jseries.jhoppers.utils.hopper.HopperType;
import store.jseries.jhoppers.utils.hopper.JHopper;

public class BlockPlaceReceiver extends JUtils implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent e) {
        if(!e.isCancelled()) {
            HopperType type = JHoppers.getInstance().getHopperTypeManager().getType(e.getItemInHand());
            if(type != null) {
                e.setCancelled(true);
                if(JHoppers.getInstance().getHopperManager().getJHopper(e.getBlock().getChunk()) != null) {
                    message(e.getPlayer(), Message.ALREADY_IN_CHUNK.getMessage());
                    return;
                }
                JHopper hopper = new JHopper(type.getId(), e.getBlock().getLocation(), e.getPlayer().getUniqueId());
                JHoppers.getInstance().getHopperManager().placeAtLocation(hopper, e.getPlayer());
                removeItemsFromHand(e.getPlayer(),1);
            }
        }
    }

}
