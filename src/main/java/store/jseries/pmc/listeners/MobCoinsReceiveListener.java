package store.jseries.pmc.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.swanis.mobcoins.events.MobCoinsReceiveEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import store.jseries.pmc.PhysicalMobCoins;
import store.jseries.pmc.api.CoinPickupEvent;
import store.jseries.pmc.managers.CoinManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MobCoinsReceiveListener implements Listener {

    private Map<UUID, Integer> nextKilled;

    public MobCoinsReceiveListener() {
        nextKilled = new HashMap<>();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        nextKilled.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDeathEvent(EntityDeathEvent e) {
        if (e.getEntity().getKiller() != null ) {
            Player player = e.getEntity().getKiller();
            if(nextKilled.containsKey(player.getUniqueId())) {
                PhysicalMobCoins.getInstance().getCoinManager().spawnItem(e.getEntity().getLocation(),nextKilled.get(player.getUniqueId()));
                nextKilled.remove(player.getUniqueId());
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onMobCoinsReceiveEvent(MobCoinsReceiveEvent e) {
        if (!e.isCancelled()) {
            if (PhysicalMobCoins.getInstance().getCoinManager().isEnabled()) {
                Player player = e.getProfile().getPlayer();
                if (!nextKilled.containsKey(player.getUniqueId())) {
                    e.setCancelled(true);
                    nextKilled.put(player.getUniqueId(),e.getAmount());
                }
            }
        }
    }
}
