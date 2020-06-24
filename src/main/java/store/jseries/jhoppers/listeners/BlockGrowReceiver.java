package store.jseries.jhoppers.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import store.jseries.framework.xseries.XBlock;
import store.jseries.framework.xseries.XMaterial;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.utils.enums.HopperFeature;
import store.jseries.jhoppers.utils.hopper.HopperType;
import store.jseries.jhoppers.utils.hopper.JHopper;

import java.util.Arrays;
import java.util.List;

public class BlockGrowReceiver implements Listener {

    private static final List<XMaterial> BLOCK_GROWTH_MATERIALS = Arrays.asList(XMaterial.SUGAR_CANE, XMaterial.MELON, XMaterial.PUMPKIN, XMaterial.CACTUS);

    @EventHandler
    public void onBlowGrow(final BlockGrowEvent e) {
        //Bukkit.getScheduler().runTaskAsynchronously(JHoppers.getInstance(), new Runnable() {
            //public void run() {
                JHopper hopper = JHoppers.getInstance().getHopperManager().getJHopper(e.getBlock().getChunk());
                XMaterial mat = XMaterial.matchXMaterial(e.getNewState().getType());
                if (mat != null && hopper != null) {
                    HopperType type = JHoppers.getInstance().getHopperTypeManager().getType(hopper.getHopperType());
                    if (type.getPickupItems().contains(mat) && type.getFeatures().contains(HopperFeature.AUTO_HARVEST)) {
                        e.setCancelled(true);
                        if (BLOCK_GROWTH_MATERIALS.contains(mat)) {
                            hopper.addItem(mat, 1);
                            return;
                        }
                        int age = XBlock.getAge(e.getBlock());
                        int minimumAge = Integer.MAX_VALUE;
                        if (mat == XMaterial.WHEAT || mat == XMaterial.CARROT || mat == XMaterial.POTATO)
                            minimumAge = 7;
                        if (mat == XMaterial.NETHER_WART)
                            minimumAge = 3;
                        if (age < minimumAge) {
                            e.setCancelled(false);
                            return;
                        }
                        hopper.addItem(mat, 1);
                        XBlock.setAge(e.getBlock(),1);
                    }
                }
          //  }
        //});

    }
}