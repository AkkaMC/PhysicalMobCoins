package store.jseries.jhoppers.inventories;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import store.jseries.framework.utils.inventory.ItemStackBuilder;
import store.jseries.framework.utils.inventory.JInventory;
import store.jseries.framework.xseries.XMaterial;
import store.jseries.jhoppers.utils.hopper.HopperType;

import java.util.Arrays;

public class HopperEditInventory extends JInventory {
    @Override
    public void open(Player player, Object... args) {
        if (args.length > 0 && args[0] instanceof HopperType) {
            HopperType type = (HopperType) args[0];
            createInventory("hopper", "&8JHoppers Menu", 45);
            for (int i : Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11,13,14,15,16,17,18,19,20,21,22,23,24,26,27,33,34,35,36,37,38,39,40,41,42,43,44))
                addItem(i, new ItemStackBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).displayName(" ").build());
            addButton(25, new ItemStackBuilder(XMaterial.BARRIER).displayName("&c&l< GO BACK").build())
                    .setClick(e -> {
                        new MainInventory().open((Player)e.getWhoClicked(),1);
                    });
            addItem(12, type.getItem());
            addButton(28, new ItemStackBuilder(XMaterial.ITEM_FRAME).displayName("&9&lEDIT ITEM").lore("&7Change the itemstack that represents", "&7the hopper.").build())
                    .setClick(e -> {
                       new HopperItemEditInventory().open((Player)e.getWhoClicked(),type);
                    });
            addButton(29, new ItemStackBuilder(XMaterial.CHEST).displayName("&9&lCOLLECTION ITEMS").lore("&7Modify the items that the hopper collects.").build())
                    .setClick(e -> {
                        new ItemsEditInventory().open((Player)e.getWhoClicked(),type,1);
                    });
            addButton(30, new ItemStackBuilder(XMaterial.BLAZE_POWDER).displayName("&9&lPLACE PARTICLES").lore("&7Change the particle effects used on place.").build())
                    .setClick(e -> {
                        new ParticleEffectInventory().open((Player)e.getWhoClicked(),type);
                    });
            addButton(31, new ItemStackBuilder(XMaterial.ARMOR_STAND).displayName("&9&lHOLOGRAM").lore("&7Change the hologram text. (if enabled)").build())
                    .setClick(e -> {
                        new HologramInventory().open((Player)e.getWhoClicked(),type);
                    });
            addButton(32, new ItemStackBuilder(XMaterial.REDSTONE).displayName("&9&lMISC. SETTINGS").lore("&7Change the settings for the hopper.").build())
                    .setClick(e -> {
                        new HopperSettingsInventory().open((Player)e.getWhoClicked(),type);
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
