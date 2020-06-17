package store.jseries.jhoppers.inventories;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import store.jseries.framework.utils.inventory.ItemStackBuilder;
import store.jseries.framework.utils.inventory.JInventory;
import store.jseries.framework.utils.inventory.JInventoryHolder;
import store.jseries.framework.xseries.XMaterial;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.utils.ConfigUtil;
import store.jseries.jhoppers.utils.hopper.JHopper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HopperInventory extends JInventory {
    @Override
    public void open(Player player, Object... args) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(JHoppers.getInstance().getDataFolder(), "guis.yml"));
        int page = (Integer) args[0];
        final JHopper hopper = (JHopper) args[1];
        if(hopper == null) {
            player.closeInventory();
            return;
        }
        String path = "hopper-menu";
        createInventory("jhopper", config.contains(path + ".name") ? config.getString(path + ".name") : "&8Hopper Menu",45);
        for(int i = 0 ; i < getSize() ; i++)
            addItem(i, ConfigUtil.fromConfigSection(config, path+".background"));
        List<Integer> slots = ConfigUtil.slotsFromSection(config,path+".items");
        int pos;
        List<XMaterial> items = JHoppers.getInstance().getHopperTypeManager().getType(hopper.getHopperType()).getPickupItems();
        Map<XMaterial, Long> itemAmounts = hopper.getItems();
        pos = page*slots.size();
        for(int i : slots) {
            if(pos>=items.size())
                addItem(i, new ItemStack(Material.AIR));
            else {
                XMaterial mat = items.get(pos);
                ItemStackBuilder builder = new ItemStackBuilder(ConfigUtil.fromConfigSection(config, path + ".items", mat));
                builder.displayName(builder.getDisplayName().replaceAll("%item_type%", mat.name().replaceAll("_",""))
                        .replaceAll("%item_amount%", itemAmounts.containsKey(mat) ? itemAmounts.get(mat) + "" : "0"));
                List<String> lore = builder.getLore();
                for(int x = 0 ; x < lore.size() ; x++)
                    lore.set(x,lore.get(x).replaceAll("%item_type%", mat.name().replaceAll("_",""))
                            .replaceAll("%item_amount%", itemAmounts.containsKey(mat) ? itemAmounts.get(mat) + "" : "0"));
                addButton(i, builder.build())
                        .setLeftClick(e -> {
                            if (!hopper.getMembers().containsKey(e.getWhoClicked().getUniqueId())) {
                                e.getWhoClicked().closeInventory();
                            } else {
                                try {
                                    JHopper jhopper = (JHopper) ((JInventoryHolder) e.getClickedInventory().getHolder()).getData(0);

                                } catch (Exception ex) {
                                    e.getWhoClicked().closeInventory();
                                }
                            }
                        });
            }
            pos+=1;
        }
        player.openInventory(getInventory(hopper));
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
