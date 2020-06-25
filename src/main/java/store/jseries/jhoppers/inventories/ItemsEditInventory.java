package store.jseries.jhoppers.inventories;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import store.jseries.framework.utils.inventory.SkullUtils;
import store.jseries.framework.xseries.XMaterial;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.utils.enums.Skin;
import store.jseries.jhoppers.utils.hopper.HopperType;
import store.jseries.jhoppers.utils.hopper.JHopper;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemsEditInventory extends JInventory {
    @Override
    public void open(Player player, Object... args) {
        if(args.length>=2) {
            createInventory("items", "&8JHoppers Menu", 45);
            HopperType type = (HopperType) args[0];
            int page = (int) args[1];
            for (int i : Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 15, 17, 18, 24, 26, 27, 33, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44))
                addItem(i, new ItemStackBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).displayName(" ").build());
            if(page>1)
                addButton(18, new ItemStackBuilder(XMaterial.STONE_BUTTON).displayName("&a&l< PREVIOUS PAGE").build())
                        .setClick(e -> {
                            new ItemsEditInventory().open(player,type,page>1 ? page-1 : 1);
                        });
            int maxPage = type.getPickupItems().size()/15 + 1;
            if(maxPage>page)
                addButton(24, new ItemStackBuilder(XMaterial.STONE_BUTTON).displayName("&a&lNEXT PAGE >").build())
                        .setClick(e -> {
                            new ItemsEditInventory().open(player,type,page+1);
                        });
            addButton(25, new ItemStackBuilder(XMaterial.BARRIER).displayName("&c&l< GO BACK").lore("&4NOTE: &cYour changes will NOT be saved.").build())
                    .setClick(e -> {
                        new HopperEditInventory().open(player, type);
                    });
            addItem(16, new ItemStackBuilder(SkullUtils.getSkullFromUrl(Skin.PLUS.getUrl())).displayName("&9&lADD ITEM").lore("&7Click an item in your inventory to toggle it.").build());
            /*addButton(34, new ItemStackBuilder(SkullUtils.getSkullFromUrl(Skin.BOOKSHELF.getUrl())).displayName("&9&lSAVE CHANGES").lore("&7Click to return and save changes.").build())
                    .setClick(e -> {
                        new HopperEditInventory().open(player, type);
                        List<String> mats = new ArrayList<>();
                        type.getPickupItems().forEach(m -> {
                            mats.add(m.name());
                        });
                        File hopperTypesFile = new File(JHoppers.getInstance().getDataFolder(),"hopper-types");
                        FileConfiguration hopperTypes = YamlConfiguration.loadConfiguration(hopperTypesFile);
                        hopperTypes.set("types." + type.getId() + ".collection-items", mats);
                        try {
                            hopperTypes.save(hopperTypesFile);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            e.getWhoClicked().sendMessage(ChatColor.RED + "There may have been an error saving the items. Check hopper-types.yml.");
                        }
                    });*/
            List<XMaterial> items =  type.getPickupItems();
            int slot = 10;
            for(int i = (page-1)*15 ; i < page*15 ; i++) {
                if (slot == 15)
                    slot = 19;
                if (slot == 24)
                    slot = 28;
                if(items.size()>i) {
                    XMaterial mat = items.get(i);
                    addButton(slot, new ItemStackBuilder(mat).displayName("&9&l" + formatUpperCamelCase(mat.name().replaceAll("_", " "))).lore("&7Click to remove.").build())
                    .setClick(e -> {
                        type.togglePickupItem(mat);
                        new ItemsEditInventory().open(player,type,page);
                    });
                } else {
                    addItem(slot, new ItemStack(Material.AIR));
                }
                slot++;
            }
            player.openInventory(getInventory(type,page));
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
    public void onPlayerPersonalInventoryClick(InventoryClickEvent e) {
        e.setCancelled(true);
        if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
            ItemStack item = e.getCurrentItem();
            HopperType type = (HopperType) ((JInventoryHolder)e.getView().getTopInventory().getHolder()).getData(0);
            int page = (int) ((JInventoryHolder)e.getView().getTopInventory().getHolder()).getData(1);
            type.togglePickupItem(XMaterial.matchXMaterial(item));
            new ItemsEditInventory().open((Player)e.getWhoClicked(), type, page);
        }
    }
}
