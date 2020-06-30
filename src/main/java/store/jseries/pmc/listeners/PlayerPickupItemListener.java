package store.jseries.pmc.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import store.jseries.pmc.api.CoinPickupEvent;
import store.jseries.pmc.managers.CoinManager;

public class PlayerPickupItemListener implements Listener {

    @EventHandler
    public void onPlayerPickupItemEvent(PlayerPickupItemEvent e) {
        ItemStack item = e.getItem().getItemStack();
        if (item.getType() != Material.AIR) {
            int amt = CoinManager.getCoins(item);
            if(amt > 0) {
                e.setCancelled(true);
                Bukkit.getPluginManager().callEvent(new CoinPickupEvent(e.getPlayer(), amt, e.getItem()));
            }
        }
    }

}
