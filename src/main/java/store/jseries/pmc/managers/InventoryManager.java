package store.jseries.pmc.managers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import store.jseries.pmc.PhysicalMobCoins;
import store.jseries.pmc.utils.MenuHolder;
import store.jseries.pmc.utils.warse.ItemStackBuilder;
import store.jseries.pmc.utils.warse.xseries.XMaterial;

import java.util.Arrays;
import java.util.List;

public class InventoryManager {

    private static final List<Integer> listSlots = Arrays.asList(10,11,12,13,14,19,20,21,22,23,28,29,30,31,32);

    private static Inventory main, items, sounds;

    public static void init() {
        FileConfiguration config = PhysicalMobCoins.getInstance().getConfig();
        main = Bukkit.createInventory(new MenuHolder("pmc-main"), 45, "&8PMC Menu");
        items = Bukkit.createInventory(new MenuHolder("pmc-items"), 45, "&8PMC Items");
        sounds = Bukkit.createInventory(new MenuHolder("pmc-sounds"), 45, "&8PMC Sounds");
        for(int i = 0 ; i < 45 ; i++) {
            main.setItem(i, new ItemStackBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).build());
            items.setItem(i, new ItemStackBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).build());
            sounds.setItem(i, new ItemStackBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).build());
        }
        main.setItem(13, new ItemStackBuilder(PhysicalMobCoins.getInstance().getCoinManager().getCoinItem(1)).displayName("&e&lPhysicalMobCoins").lore("&7jseries.store/pmb").build());
        main.setItem(30, new ItemStackBuilder(XMaterial.BOOK).displayName("&e&lChange Item").lore("&7Click to view other item types.").build());
        main.setItem(31, new ItemStackBuilder(XMaterial.MUSIC_DISC_WAIT).displayName("&e&lChange Sound").lore("&7Click to change the pickup sound.").addItemFlag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS).build());
        main.setItem(32, new ItemStackBuilder(XMaterial.IRON_DOOR).displayName("&e&lRequest Support").lore("&7Click to request support.").build());

        items.setItem(25, new ItemStackBuilder(XMaterial.BARRIER).displayName("&c&l< GO BACK").build());
        sounds.setItem(25, new ItemStackBuilder(XMaterial.BARRIER).displayName("&c&l< GO BACK").build());

    }

}
