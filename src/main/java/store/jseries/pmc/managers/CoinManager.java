package store.jseries.pmc.managers;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import store.jseries.pmc.PhysicalMobCoins;
import store.jseries.pmc.api.CoinPickupEvent;
import store.jseries.pmc.utils.Skin;
import store.jseries.pmc.utils.warse.ItemStackBuilder;
import store.jseries.pmc.utils.warse.SkullUtils;
import store.jseries.pmc.utils.warse.xseries.XMaterial;

import java.util.List;

public class CoinManager {

    @Getter
    @Setter
    private boolean enabled;

    private ItemStack coinItem = null;

    private double yChange, xDistance, yDistance,zDistance;

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
        int check = config.contains("pickup-check-delay") ? config.getInt("pickup-check-delay") : 10;
        xDistance = config.contains("x-distance") ? config.getDouble("x-distance") : 1.0;
        yDistance = config.contains("y-distance") ? config.getDouble("y-distance") : 1.0;
        zDistance = config.contains("z-distance") ? config.getDouble("z-distance") : 1.0;
        Bukkit.getScheduler().runTaskTimerAsynchronously(PhysicalMobCoins.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    List<Entity> nearby = player.getNearbyEntities(xDistance,yDistance,zDistance);
                    if(nearby.size() > 0) {
                        for(Entity entity : nearby) {
                            if(entity.getType() != EntityType.DROPPED_ITEM)
                                continue;
                            ItemStack item = ((Item) entity).getItemStack();
                            if(item.getType() != Material.AIR) {
                                int amt = CoinManager.getCoins(item);
                                Bukkit.getPluginManager().callEvent(new CoinPickupEvent(player, amt, (Item) entity));
                            }
                        }
                    }
                }
            }
        }, check,check);
    }

    public static int getCoins(ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);
        if(!nbtItem.hasKey("mobcoins"))
            return 0;
        return nbtItem.getInteger("mobcoins") * item.getAmount();

    }

    public ItemStack getCoinItem(int amt) {
        NBTItem nbtItem = new NBTItem(coinItem);
        nbtItem.setInteger("mobcoins", amt);
        return nbtItem.getItem();
    }

    public void spawnItem(Location loc, int amt) {
        if(loc != null && loc.getWorld() != null) {
            Item item = loc.getWorld().dropItem(loc.add(0, yChange, 0), getCoinItem(amt));
            item.setCustomNameVisible(false);
            item.setCustomName("");
            item.setPickupDelay(Integer.MAX_VALUE);
        }
    }

    public void setCoinItem(ItemStack item, Skin skin) {
        coinItem = new ItemStackBuilder(item).amount(1).addItemFlag(ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_ENCHANTS).lore().build();
        InventoryManager.setCurrentItem(skin);
    }



}
