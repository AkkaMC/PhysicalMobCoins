package store.jseries.framework.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import store.jseries.framework.JFramework;
import store.jseries.framework.utils.inventory.InventoryButton;
import store.jseries.framework.utils.inventory.JInventory;
import store.jseries.framework.utils.inventory.JInventoryHolder;
import store.jseries.jhoppers.JHoppers;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager implements Listener {

    private static final Map<String, JInventory> INVENTORIES = new HashMap<>();

    public static void addInventory(String name, JInventory inventory) {
        if (!InventoryManager.INVENTORIES.containsKey(name.toLowerCase()))
            InventoryManager.INVENTORIES.put(name.toLowerCase(), inventory);
    }

    public static boolean hasInventory(String name) {
        return INVENTORIES.containsKey(name.toLowerCase());
    }

    public static void removeInventory(String name) {
        InventoryManager.INVENTORIES.remove(name.toLowerCase());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() != null && e.getInventory().getHolder() != null && e.getInventory().getHolder() instanceof JInventoryHolder) {
            if (e.getClickedInventory().getHolder() == null || !(e.getClickedInventory().getHolder() instanceof JInventoryHolder)) {
                ((JInventoryHolder) e.getInventory().getHolder()).getjInventory().onPlayerPersonalInventoryClick(e);
                return;
            }
            JInventory gui = ((JInventoryHolder) e.getInventory().getHolder()).getjInventory();
            gui.onInventoryClick(e);
            InventoryButton button = gui.getButtons().getOrDefault(e.getSlot(), null);
            if (button != null)
                button.onClick(e);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() != null && e.getInventory().getHolder() instanceof JInventoryHolder) {
            JInventory gui = ((JInventoryHolder) e.getInventory().getHolder()).getjInventory();
            gui.onClose(e);
        }
    }

    private JInventory getInventory(String id) {
        return InventoryManager.INVENTORIES.getOrDefault(id, null);
    }

    public static void enable() {
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryManager(), JFramework.getPlugin());
    }
}
