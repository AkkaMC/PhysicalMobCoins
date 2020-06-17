package store.jseries.jhoppers.managers;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import store.jseries.framework.utils.JUtils;
import store.jseries.framework.xseries.XMaterial;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.utils.enums.HopperFeature;
import store.jseries.jhoppers.utils.enums.PlaceParticle;
import store.jseries.jhoppers.utils.hopper.HopperType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HopperTypeManager extends JUtils {

    private Map<String, HopperType> types;

    public HopperTypeManager() {
        types = new HashMap<>();
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(JHoppers.getInstance().getDataFolder(), "hopper-types.yml"));
        if(config.contains("types")) {
            for(String key : config.getConfigurationSection("types").getKeys(false)) {
                HopperType type = new HopperType(key);
                if(config.contains("types." + key + ".item.name"))
                    type.setItemName(config.getString("types." + key + ".item.name"));
                if(config.contains("types." + key + ".item.lore"))
                    type.setItemLore(config.getStringList("types." + key + ".item.lore"));
                if(config.contains("types." + key + ".item.material"))
                    type.setType(XMaterial.valueOf(config.getString("types." + key + ".item.material").toUpperCase()));
                if(config.contains("types." + key + ".item.enchanted"))
                    type.setEnchanted(config.getBoolean("types." + key + ".item.enchanted"));
                if(config.contains("types." + key + ".features")) {
                    List<HopperFeature> features = new ArrayList<>();
                    if(config.getStringList("types." + key + ".features").size()>0) {
                        for (String s : config.getStringList("types." + key + ".features"))
                            features.add(HopperFeature.valueOf(s.toUpperCase()));
                    }
                    type.setFeatures(features);
                }
                if(config.contains("types." + key + ".block"))
                    type.setBlockType(XMaterial.valueOf(config.getString("types." + key + ".block").toUpperCase()));
                if(config.contains("types." + key + ".collection-items")) {
                    List<XMaterial> mats = new ArrayList<>();
                    if(config.getStringList("types." + key + ".collection-items").size() > 0) {
                        for (String s : config.getStringList("types." + key + ".collection-items"))
                            mats.add(XMaterial.valueOf(s.toUpperCase()));
                    }
                    type.setPickupItems(mats);
                }
                if(config.contains("types." + key + ".particle"))
                    type.setPlaceParticle(PlaceParticle.fromString(config.getString("types." + key + ".particle")));
                if(config.contains("types." + key + ".hologram"))
                    type.setHologram(config.getString("types." + key + ".hologram"));
                addType(key,type);
            }
        }
    }

    public HopperType getType(String id) {
        return types.get(id.toLowerCase());
    }

    public boolean hasType(String id) {
        return types.containsKey(id.toLowerCase());
    }

    public void addType(String id, HopperType type) {
        types.put(id.toLowerCase(), type);
    }

    public List<HopperType> getTypes() {
        return new ArrayList<>(types.values());
    }

    public HopperType getType(ItemStack item) {
        if(item == null || item.getType() == Material.AIR)
            return null;
        NBTItem nbtItem = new NBTItem(item);
        if(nbtItem.hasKey("jhopper")) {
            if(types.containsKey(nbtItem.getString("jhopper").toLowerCase()))
                return types.get(nbtItem.getString("jhopper".toLowerCase()));
        }
        return null;
    }

}
