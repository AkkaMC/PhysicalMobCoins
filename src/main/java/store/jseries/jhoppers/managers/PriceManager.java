package store.jseries.jhoppers.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import store.jseries.framework.xseries.XMaterial;
import store.jseries.jhoppers.JHoppers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PriceManager {

    private Map<XMaterial, Double> prices;
    private double defaultPrice;

    public PriceManager() {
        prices = new HashMap<>();
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(JHoppers.getInstance().getDataFolder(),"prices.yml"));
        defaultPrice = config.contains("default-price") ? config.getDouble("default-price") : 5.0;
        if(config.contains("prices")) {
            for(String key : config.getConfigurationSection("prices").getKeys(false)) {
                try {
                    XMaterial material = XMaterial.getXMaterialIfDuplicated(key.toUpperCase());
                    double price = config.getDouble("prices." + key);
                    prices.put(material,price);
                } catch (Exception ignored) {}
            }
        }
    }

    public double getPrice(XMaterial mat) {
        if(prices.containsKey(mat))
            return prices.get(mat);
        prices.put(mat,defaultPrice);
        return defaultPrice;
    }

    public void setPrice(XMaterial mat, double amt) {
        prices.put(mat,amt);
    }

}
