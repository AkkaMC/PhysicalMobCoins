package store.jseries.jhoppers.inventories;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import store.jseries.framework.chat.ChatResponse;
import store.jseries.framework.utils.inventory.ItemStackBuilder;
import store.jseries.framework.utils.inventory.JInventory;
import store.jseries.framework.xseries.XMaterial;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.supports.HolographicDisplaysSupport;
import store.jseries.jhoppers.utils.hopper.HopperType;

import java.util.Arrays;

public class HologramInventory extends JInventory {
    @Override
    public void open(Player player, Object... args) {
        if (args.length > 0) {
            createInventory("hologram", "&8JHoppers Menu", 45);
            HopperType type = (HopperType) args[0];
            for (int i = 0; i < 45; i++)
                addItem(i, new ItemStackBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).displayName(" ").build());
            addButton(25, new ItemStackBuilder(XMaterial.BARRIER).displayName("&c&l< GO BACK").build())
                    .setClick(e -> {
                        new HopperEditInventory().open(player, type);
                    });
            boolean enabled = HolographicDisplaysSupport.isEnabled();
            addItem(29, new ItemStackBuilder(enabled ? XMaterial.LIME_STAINED_GLASS_PANE : XMaterial.RED_STAINED_GLASS_PANE).displayName(enabled ? "&a&lHD FOUND" : "&c&lHD NOT FOUND").lore(enabled ? "&7HolographicDisplays is enabled!" : "Requires HolographicDisplays.").build());
            boolean isEnabled = enabled && !type.getHologram().equalsIgnoreCase("");
            addButton(31, new ItemStackBuilder(isEnabled ? XMaterial.LIME_STAINED_GLASS_PANE : XMaterial.RED_STAINED_GLASS_PANE).displayName(isEnabled ? "&a&lENABLED" : "&c&lDISABLED").lore(isEnabled ? "&7Click to disable." : "Click to enable.").build())
                    .setClick(e -> {
                        type.toggleHologram();
                        new HologramInventory().open(player, type);
                    });
            addButton(12, new ItemStackBuilder(XMaterial.PAPER).displayName("&9&lHOLOGRAM TEXT").lore("", "&b&lCurrent Text", isEnabled ? type.getHologram() : "&cDISABLED", "", "&eClick to change.").build())
                    .setClick(e -> {
                        if (!isEnabled) {
                            message(player, "&cThis hologram is disabled.");
                            return;
                        }
                        message(player, "&7Type the hologram text. Enter 'exit' to cancel and return to menu.");
                        player.closeInventory();
                        ChatResponse.addResponse(player.getUniqueId(), event -> {
                            if (event.getMessage().equalsIgnoreCase("exit")) {
                                new HologramInventory().open(event.getPlayer(), type);
                            } else {
                                type.setHologram(event.getMessage());
                                message(player, "&aSuccessfully updated the hologram.");
                                new HologramInventory().open(event.getPlayer(), type);
                            }
                            ChatResponse.removeResponse(event.getPlayer().getUniqueId());
                        });
                    });
            player.openInventory(getInventory(type));
        }
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerPersonalInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }
}
