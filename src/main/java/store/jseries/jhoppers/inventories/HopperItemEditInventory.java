package store.jseries.jhoppers.inventories;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemFlag;
import store.jseries.framework.chat.ChatResponse;
import store.jseries.framework.utils.inventory.ItemStackBuilder;
import store.jseries.framework.utils.inventory.JInventory;
import store.jseries.framework.utils.inventory.JInventoryHolder;
import store.jseries.framework.xseries.XMaterial;
import store.jseries.jhoppers.utils.hopper.HopperType;

import java.util.Arrays;

public class HopperItemEditInventory extends JInventory {
    @Override
    public void open(Player player, Object... args) {
        if(args.length>0) {
            createInventory("hopper-item", "&8JHoppers Menu", 45);
            HopperType type = (HopperType) args[0];
            for (int i : Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11,13,14,15,16,17,18,19,20,21,22,23,24,26,27,33,34,35,36,37,38,39,40,41,42,43,44))
                addItem(i, new ItemStackBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).displayName(" ").build());
            addButton(25, new ItemStackBuilder(XMaterial.BARRIER).displayName("&c&l< GO BACK").build())
                    .setClick(e -> {
                        new HopperEditInventory().open((Player)e.getWhoClicked(),type);
                    });
            addItem(12, type.getItem());
            addItem(28, new ItemStackBuilder(XMaterial.ITEM_FRAME).displayName("&9&lHOPPER ITEM").lore("&7Left click an item in your inventory", "&7to set it as the item.").build());
            addButton(29, new ItemStackBuilder(XMaterial.NAME_TAG).displayName("&9&lITEM NAME").lore().build())
            .setClick(e -> {
                e.getWhoClicked().closeInventory();
                message(player, "&7Type the new hopper item's name. Enter 'exit' to cancel and return to menu.");
                ChatResponse.addResponse(e.getWhoClicked().getUniqueId(), event -> {
                    if(!event.getMessage().equalsIgnoreCase("exit")) {
                        type.setItemName(event.getMessage());
                    }
                    new HopperItemEditInventory().open(player,type);
                    ChatResponse.removeResponse(event.getPlayer().getUniqueId());
                });
            });
            addButton(30, new ItemStackBuilder(XMaterial.PAPER).displayName("&9&lITEM LORE").lore().build());
            addButton(31, type.isEnchanted() ? new ItemStackBuilder(XMaterial.ENCHANTED_BOOK).displayName("&9&lTOGGLE GLOWING").enchant(Enchantment.DURABILITY,0).addItemFlag(ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_ATTRIBUTES).lore("&7Click to remove glowing.").build() : new ItemStackBuilder(XMaterial.BOOK).displayName("&9&lTOGGLE GLOWING").addItemFlag(ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_ATTRIBUTES).lore("&7Click to enable glowing.").build())
            .setClick(e -> {
                type.toggleEnchanted();
                new HopperItemEditInventory().open(player,type);
            });
            addItem(32, new ItemStackBuilder(type.getBlockType()).displayName("&9&lHOPPER BLOCK").lore("&7Right click an item in your inventory", "&7to set it as the block type.").build());
            player.openInventory(getInventory(type));
        }
    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }

    @Override
    public void onInventoryClick(InventoryClickEvent e) {

        e.setCancelled(true);
    }

    @Override
    public void onPlayerPersonalInventoryClick(InventoryClickEvent e) {
        if(e.getCurrentItem() != null){
            XMaterial mat = XMaterial.matchXMaterial(e.getCurrentItem());
            if(mat != null && mat != XMaterial.AIR) {
                HopperType type = (HopperType) ((JInventoryHolder)e.getInventory().getHolder()).getData(0);
                if (e.getClick() == ClickType.RIGHT) {
                    type.setBlockType(mat);
                    new HopperItemEditInventory().open((Player)e.getWhoClicked(), type);
                } else if (e.getClick() == ClickType.LEFT) {
                    type.setType(mat);
                    new HopperItemEditInventory().open((Player)e.getWhoClicked(), type);
                }
            }
        }
        e.setCancelled(true);
    }
}
