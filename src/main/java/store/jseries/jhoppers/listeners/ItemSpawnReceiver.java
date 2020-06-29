package store.jseries.jhoppers.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import store.jseries.framework.xseries.XMaterial;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.supports.UltimateStackerSupport;
import store.jseries.jhoppers.supports.WildStackerSupport;
import store.jseries.jhoppers.utils.hopper.JHopper;

public class ItemSpawnReceiver implements Listener {

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent e) {
        JHoppers jh = JHoppers.getInstance();
        JHopper hopper = jh.getHopperManager().getJHopper(e.getLocation().getChunk());
        if (hopper != null && e.getEntity().getItemStack().getType() != Material.AIR) {
            XMaterial mat = XMaterial.matchXMaterial(e.getEntity().getItemStack());
            if (jh.getHopperTypeManager().getType(hopper.getHopperType()).getPickupItems().contains(mat)) {
                int amt = e.getEntity().getItemStack().getAmount();
                if (UltimateStackerSupport.isEnabled())
                    amt = UltimateStackerSupport.getItemAmount(e.getEntity());
                if (WildStackerSupport.isEnabled())
                    amt = WildStackerSupport.getItemAmount(e.getEntity());
                hopper.addItem(mat, amt);
                e.setCancelled(true);
            }
        }
    }

}
