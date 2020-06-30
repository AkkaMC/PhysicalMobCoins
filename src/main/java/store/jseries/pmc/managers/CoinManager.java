package store.jseries.pmc.managers;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import store.jseries.pmc.PhysicalMobCoins;
import store.jseries.pmc.utils.warse.ItemStackBuilder;
import store.jseries.pmc.utils.warse.SkullUtils;
import store.jseries.pmc.utils.warse.xseries.XMaterial;

public class CoinManager {

    @Getter
    private boolean enabled;

    private ItemStack coinItem = null;

    private double yChange;

    public CoinManager() {
        FileConfiguration config = PhysicalMobCoins.getInstance().getConfig();
        enabled = true;
        yChange = 0;
        if(config.contains("item-type")) {
            try {
                 String type = config.getString("item-type").toUpperCase();
                 if(type.startsWith("HEAD:"))
                     coinItem = new ItemStackBuilder(SkullUtils.getSkullFromUrl(type.substring(5))).displayName("").build();
                 else
                     coinItem = new ItemStackBuilder(XMaterial.valueOf(type)).displayName("").build();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if(coinItem == null)
                    coinItem = new ItemStackBuilder(XMaterial.REDSTONE_BLOCK).displayName("&c&lInvalid Config.YML").build();
            }
        } else {
            coinItem = new ItemStackBuilder(XMaterial.REDSTONE_BLOCK).displayName("&c&lInvalid Config.YML").build();
        }
        if(config.contains("enabled"))
            enabled = config.getBoolean("enabled");
        if(config.contains("y-change-amount"))
            yChange = config.getDouble("y-change-amount");
    }

    public static int getCoins(ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);
        if(!nbtItem.hasKey("mobcoins"))
            return 0;
        return nbtItem.getInteger("mobcoins");
    }

    public ItemStack getCoinItem(int amt) {
        NBTItem nbtItem = new NBTItem(coinItem);
        nbtItem.setInteger("mobcoins", amt);
        return nbtItem.getItem();
    }

    public void spawnItem(Location loc, int amt) {
        if(loc != null && loc.getWorld() != null)
            loc.getWorld().dropItem(loc.add(0,yChange,0), getCoinItem(amt));
    }



}
