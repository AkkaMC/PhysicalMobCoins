package store.jseries.pmc.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryPickupItemListener implements Listener {

    @EventHandler
    public void onInventoryPickupItemEvent(InventoryPickupItemEvent e) {
        ItemStack item = e.getItem().getItemStack();
        if(item.getType() != Material.AIR) {
            NBTItem nbtItem = new NBTItem(item);
            if(nbtItem.hasKey("mobcoins"))
                e.setCancelled(true);
        }
    }

}
