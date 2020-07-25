package store.jseries.pmc.managers;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import store.jseries.pmc.PhysicalMobCoins;
import store.jseries.pmc.utils.MenuHolder;
import store.jseries.pmc.utils.PickupSound;
import store.jseries.pmc.utils.Skin;
import store.jseries.pmc.utils.warse.ItemStackBuilder;
import store.jseries.pmc.utils.warse.SkullUtils;
import store.jseries.pmc.utils.warse.xseries.XMaterial;

import java.util.Arrays;
import java.util.List;

public class InventoryManager {

    @Getter
    private static final List<Integer> listSlots = Arrays.asList(10,11,12,13,14,19,20,21,22,23,28,29,30,31,32);

    @Getter
    private static Inventory main, items, sounds;

    public static void init() {
        main = Bukkit.createInventory(new MenuHolder("pmc-main"), 45, ChatColor.DARK_GRAY + "PMC Menu");
        items = Bukkit.createInventory(new MenuHolder("pmc-items"), 45, ChatColor.DARK_GRAY + "PMC Items");
        sounds = Bukkit.createInventory(new MenuHolder("pmc-sounds"), 45, ChatColor.DARK_GRAY + "PMC Sounds");
        for(int i = 0 ; i < 45 ; i++) {
            main.setItem(i, new ItemStackBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).build());
            items.setItem(i, new ItemStackBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).build());
            sounds.setItem(i, new ItemStackBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).build());
        }
        main.setItem(13, new ItemStackBuilder(SkullUtils.getSkullFromUrl(Skin.ONE.getLink())).displayName("&e&lPhysicalMobCoins").lore("&7jseries.store/pmb").build());
        main.setItem(30, new ItemStackBuilder(XMaterial.BOOK).displayName("&e&lChange Item").lore("&7Click to view other item types.").build());
        main.setItem(31, new ItemStackBuilder(XMaterial.MUSIC_DISC_WAIT).displayName("&e&lChange Sound").lore("&7Click to change the pickup sound.").addItemFlag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS).build());
        main.setItem(32, new ItemStackBuilder(XMaterial.IRON_DOOR).displayName("&e&lRequest Support").lore("&7Click to request support.").build());

        items.setItem(25, new ItemStackBuilder(XMaterial.BARRIER).displayName("&c&l< GO BACK").build());
        sounds.setItem(25, new ItemStackBuilder(XMaterial.BARRIER).displayName("&c&l< GO BACK").build());
        items.setItem(16, new ItemStackBuilder(SkullUtils.getSkullFromUrl("69b861aabb316c4ed73b4e5428305782e735565ba2a053912e1efd834fa5a6f")).displayName("&e&lCustom Item").lore("&61. &7Hold the desired item in your hand.", "&62. &7Run the command /pmc setitem.", "&63. &7Done!").build());
        sounds.setItem(16, new ItemStackBuilder(SkullUtils.getSkullFromUrl("69b861aabb316c4ed73b4e5428305782e735565ba2a053912e1efd834fa5a6f")).displayName("&e&lCustom Sound").lore("&7Want a sound added? Head over to the discord!").build());
        PickupSound current = PhysicalMobCoins.getInstance().getSoundManager().getSound();
        setCurrentSound(current);
        setCurrentItem();
    }

    public static void setCurrentItem() {
        ItemStack current = PhysicalMobCoins.getInstance().getCoinManager().getCoinItem(1);
        items.setItem(34, new ItemStackBuilder(PhysicalMobCoins.getInstance().getCoinManager().getCoinItem(1)).displayName("&e&lCurrent Coin").build());
        main.setItem(13, new ItemStackBuilder(current).displayName("&e&lPhysicalMobCoins").lore("&7jseries.store/pmb").build());
        int id = 0;
        for(Skin skin : Skin.values()) {
            if (skin != Skin.CUSTOM) {
                items.setItem(listSlots.get(id), new ItemStackBuilder(SkullUtils.getSkullFromUrl(skin.getLink())).displayName("&e&lHead #" + (id + 1)).lore("&7Click to select.").build());
                id++;
            }
        }
    }

    public static void setCurrentItem(Skin skinId) {
        ItemStack current = PhysicalMobCoins.getInstance().getCoinManager().getCoinItem(1);
        main.setItem(13, new ItemStackBuilder(current).displayName("&e&lPhysicalMobCoins").lore("&7jseries.store/pmb").build());
        items.setItem(34, new ItemStackBuilder(PhysicalMobCoins.getInstance().getCoinManager().getCoinItem(1)).displayName("&e&lCurrent Coin").build());
        int id = 0;
        for(Skin skin : Skin.values()) {
            if(skin != Skin.CUSTOM) {
                items.setItem(listSlots.get(id), new ItemStackBuilder(SkullUtils.getSkullFromUrl(skin.getLink())).displayName("&e&lHead #" + (id + 1)).lore("&7Click to select.").build());
                id++;
            }
        }
        FileConfiguration config = PhysicalMobCoins.getInstance().getConfig();
        config.set("item-type", skinId==Skin.CUSTOM ? XMaterial.matchXMaterial(current).name() : "HEAD:" + skinId.getLink());
        PhysicalMobCoins.getInstance().saveConfig();
    }

    public static void setCurrentSound(PickupSound current) {
        sounds.setItem(34, new ItemStackBuilder(current.getIcon()).displayName("&e&lCurrent Sound").lore("&7" + current.toString().replaceAll("_"," ")).build());
        int id = 0;
        for(PickupSound sound : PickupSound.values()) {
            sounds.setItem(listSlots.get(id), new ItemStackBuilder(sound.getIcon()).displayName("&e&l" + sound.toString().replaceAll("_"," ") + "&7 " + (sound == current ? "[Enabled]" : "[Disabled]")).lore("&7Right click to preview.", "&7" + (sound == current ? "Left click to disable." : "Left click to enable.")).build());
            id++;
        }
    }


}
