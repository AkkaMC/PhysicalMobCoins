package store.jseries.jhoppers.inventories;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import store.jseries.framework.utils.inventory.ItemStackBuilder;
import store.jseries.framework.utils.inventory.JInventory;
import store.jseries.framework.xseries.XMaterial;
import store.jseries.jhoppers.utils.enums.HopperFeature;
import store.jseries.jhoppers.utils.hopper.HopperType;

import java.util.Arrays;

public class HopperSettingsInventory extends JInventory {
    @Override
    public void open(Player player, Object... args) {
        if (args.length > 0) {
            createInventory("hopper-settings", "&8JHoppers Menu", 45);
            HopperType type = (HopperType) args[0];

            for (int i : Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 13, 15, 16, 17, 18, 20, 22, 24, 26, 27, 29, 31, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44))
                addItem(i, new ItemStackBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).displayName(" ").build());
            addButton(25, new ItemStackBuilder(XMaterial.BARRIER).displayName("&c&l< GO BACK").build())
                    .setClick(e -> {
                        new HopperEditInventory().open((Player) e.getWhoClicked(), type);
                    });
            HopperFeature[] features = HopperFeature.values();
            for (int i = 0; i < features.length; i++) {
                HopperFeature feature = features[i];
                boolean has = type.getFeatures().contains(feature);
                addButton(10 + i * 2, new ItemStackBuilder(has ? XMaterial.LIME_STAINED_GLASS_PANE : XMaterial.RED_STAINED_GLASS_PANE).displayName(has ? ("&a&l" + feature.getConfigId().toUpperCase() + " &7[Enabled]") : ("&c&l" + feature.getConfigId().toUpperCase() + " &7[Disabled]")).lore(has ? "&7Click to disable." : "&7Click to enable.").build())
                        .setClick(e -> {
                            type.toggleFeature(feature);
                            new HopperSettingsInventory().open(player, type);
                        });
                addButton(19 + i * 2, new ItemStackBuilder(feature.getItem()).displayName("&" + feature.getColor() + "&l" + feature.getConfigId().toUpperCase() + (has ? " &7[Enabled]" : " &7[Disabled]")).lore(feature.getInfo(), "", (has ? "&eClick to disable." : "&eClick to enable.")).build())
                        .setClick(e -> {
                            type.toggleFeature(feature);
                            new HopperSettingsInventory().open(player, type);
                        });
                addButton(28 + i * 2, new ItemStackBuilder(has ? XMaterial.LIME_STAINED_GLASS_PANE : XMaterial.RED_STAINED_GLASS_PANE).displayName(has ? ("&a&l" + feature.getConfigId().toUpperCase() + " &7[Enabled]") : ("&c&l" + feature.getConfigId().toUpperCase() + " &7[Disabled]")).lore(has ? "&7Click to disable." : "&7Click to enable.").build())
                        .setClick(e -> {
                            type.toggleFeature(feature);
                            new HopperSettingsInventory().open(player, type);
                        });
            }
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
