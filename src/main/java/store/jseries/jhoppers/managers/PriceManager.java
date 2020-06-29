package store.jseries.jhoppers.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import store.jseries.framework.xseries.XMaterial;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.supports.ShopGUIPlusSupport;
import store.jseries.jhoppers.supports.SuperBoostersSupport;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PriceManager {

    private Map<XMaterial, Double> prices;
    private double defaultPrice;

    public PriceManager() {
        prices = new HashMap<>();
        File pricesFile = new File(JHoppers.getInstance().getDataFolder(), "prices.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(pricesFile);
        defaultPrice = config.contains("default-price") ? config.getDouble("default-price") : 5.0;
        for (XMaterial mat : XMaterial.values()) {
            if (config.contains("prices." + mat.name())) {
                prices.put(mat, config.getDouble("prices." + mat.name()));
            } else {
                prices.put(mat, defaultPrice);
                config.set("prices." + mat.name(), defaultPrice);
            }
        }
        try {
            config.save(pricesFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public double getPrice(Player player, XMaterial mat) {
        double price = ShopGUIPlusSupport.getItemPrice(player,mat);
        double booster = SuperBoostersSupport.getBooster(player.getUniqueId());
        if(price != -1)
            return price * booster;
        if(prices.containsKey(mat))
            return prices.get(mat) * booster;
        prices.put(mat,defaultPrice);
        return defaultPrice * booster;
    }

    public void setPrice(XMaterial mat, double amt) {
        prices.put(mat,amt);
    }

}
