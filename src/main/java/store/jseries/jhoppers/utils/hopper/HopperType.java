package store.jseries.jhoppers.utils.hopper;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import store.jseries.framework.utils.inventory.ItemStackBuilder;
import store.jseries.framework.xseries.XMaterial;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.supports.HolographicDisplaysSupport;
import store.jseries.jhoppers.utils.enums.HopperFeature;
import store.jseries.jhoppers.utils.enums.PlaceParticle;

import java.util.*;

public class HopperType {

    @Getter
    @Setter
    private XMaterial type;
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String itemName;
    @Getter
    @Setter
    private List<String> itemLore;
    @Getter
    @Setter
    private boolean enchanted;
    @Getter
    @Setter
    private List<HopperFeature> features;
    @Getter
    @Setter
    private XMaterial blockType;
    @Getter
    @Setter
    private List<XMaterial> pickupItems;
    @Getter
    @Setter
    private PlaceParticle placeParticle;
    @Getter
    private String hologram;

    public HopperType(String name) {
        this.id = name.toLowerCase();
        type = XMaterial.HOPPER;
        itemName = "&e&l" + name.toUpperCase() + " HOPPER";
        itemLore = Collections.singletonList("&7Place to activate.");
        enchanted = false;
        features = new ArrayList<>();
        blockType = XMaterial.HOPPER;
        pickupItems = new ArrayList<>();
        placeParticle = PlaceParticle.NONE;
        hologram = itemName;
    }

    public ItemStack getItem() {
        return getItem(1);
    }

    public ItemStack getItem(int amt) {
        ItemStack item = enchanted ? new ItemStackBuilder(type).amount(amt).displayName(itemName).lore(itemLore).addItemFlag(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES).enchant(Enchantment.DURABILITY, 0).build() : new ItemStackBuilder(type).amount(amt).displayName(itemName).lore(itemLore).addItemFlag(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES).build();
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString("jhopper", id.toLowerCase());
        return nbtItem.getItem();
    }

    public void togglePickupItem(XMaterial mat) {
        if (pickupItems.contains(mat))
            pickupItems.remove(mat);
        else
            pickupItems.add(mat);
    }

    public void toggleEnchanted() {
        enchanted = !enchanted;
    }

    public void toggleFeature(HopperFeature feature) {
        if (features.contains(feature))
            features.remove(feature);
        else
            features.add(feature);
    }

    public void toggleHologram() {
        if (hologram.equalsIgnoreCase("")) {
            if (HolographicDisplaysSupport.isEnabled())
                setHologram("&e&lEXAMPLE");
        } else {
            setHologram("");
            for (JHopper hopper : JHoppers.getInstance().getHopperManager().getHopperChunks().values()) {
                if (id.equalsIgnoreCase(hopper.getHopperType()))
                    HolographicDisplaysSupport.removeHologram(hopper);
            }
        }

    }

    public void setHologram(String text) {
        hologram = text;
        for (JHopper hopper : JHoppers.getInstance().getHopperManager().getHopperChunks().values()) {
            if (id.equalsIgnoreCase(hopper.getHopperType()))
                HolographicDisplaysSupport.updateHologram(hopper);
        }
    }

    public void updateHopperBlocks() {
        Bukkit.getScheduler().runTask(JHoppers.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(JHopper hopper : JHoppers.getInstance().getHopperManager().getHopperChunks().values()) {
                    if(hopper.getHopperType().equalsIgnoreCase(id))
                        hopper.getLocation().getBlock().setType(Objects.requireNonNull(blockType.parseMaterial()));
                }
            }
        });
    }
}