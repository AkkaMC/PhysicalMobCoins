package store.jseries.jhoppers.inventories;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import store.jseries.framework.chat.ChatResponse;
import store.jseries.framework.utils.inventory.ItemStackBuilder;
import store.jseries.framework.utils.inventory.JInventory;
import store.jseries.framework.xseries.XMaterial;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.utils.enums.Permission;

public class PermissionsInventory extends JInventory {
    @Override
    public void open(Player player, Object... args) {
        createInventory("permissions", "&8JHoppers Menu", 45);
        for (int i = 0; i < 45; i++)
            addItem(i, new ItemStackBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).displayName(" ").build());
        addButton(25, new ItemStackBuilder(XMaterial.BARRIER).displayName("&c&l< GO BACK").build())
                .setClick(e -> {
                    new MainInventory().open(player);
                });
        addButton(19, new ItemStackBuilder(XMaterial.REDSTONE).displayName("&c&lUSE PERMISSION").lore("","&4&lCurrent Permission", "&7" + JHoppers.getInstance().getPermissionManager().getPermission(Permission.USE),"","&eClick to change.").build())
                .setClick(e -> {
                    message(e.getWhoClicked(), "&7Type the new use permission. Enter 'exit' to cancel and return.");
                    e.getWhoClicked().closeInventory();
                    ChatResponse.addResponse(e.getWhoClicked().getUniqueId(),event -> {
                        if(!event.getMessage().equalsIgnoreCase("exit"))
                            JHoppers.getInstance().getPermissionManager().setPermission(Permission.USE, event.getMessage());
                        ChatResponse.removeResponse(event.getPlayer().getUniqueId());
                        new PermissionsInventory().open(player);
                    });
                });
        addButton(20, new ItemStackBuilder(XMaterial.REDSTONE).displayName("&c&lADD MEMBERS PERMISSION").lore("","&4&lCurrent Permission", "&7" + JHoppers.getInstance().getPermissionManager().getPermission(Permission.ADD_MEMBERS),"","&eClick to change.").build())
                .setClick(e -> {
                    message(e.getWhoClicked(), "&7Type the new add members permission. Enter 'exit' to cancel and return.");
                    e.getWhoClicked().closeInventory();
                    ChatResponse.addResponse(e.getWhoClicked().getUniqueId(),event -> {
                        if(!event.getMessage().equalsIgnoreCase("exit"))
                            JHoppers.getInstance().getPermissionManager().setPermission(Permission.ADD_MEMBERS, event.getMessage());
                        ChatResponse.removeResponse(event.getPlayer().getUniqueId());
                        new PermissionsInventory().open(player);
                    });
                });
        addButton(21, new ItemStackBuilder(XMaterial.REDSTONE).displayName("&c&lADMIN PERMISSION").lore("","&4&lCurrent Permission", "&7" + JHoppers.getInstance().getPermissionManager().getPermission(Permission.ADMIN_PERMISSION),"","&eClick to change.").build())
                .setClick(e -> {
                    message(e.getWhoClicked(), "&7Type the new admin permission. Enter 'exit' to cancel and return.");
                    e.getWhoClicked().closeInventory();
                    ChatResponse.addResponse(e.getWhoClicked().getUniqueId(),event -> {
                        if(!event.getMessage().equalsIgnoreCase("exit"))
                            JHoppers.getInstance().getPermissionManager().setPermission(Permission.ADMIN_PERMISSION, event.getMessage());
                        ChatResponse.removeResponse(event.getPlayer().getUniqueId());
                        new PermissionsInventory().open(player);
                    });
                });
        addItem(22, new ItemStackBuilder(XMaterial.GUNPOWDER).displayName("&8&lCOMING SOON").build());
        addItem(23, new ItemStackBuilder(XMaterial.GUNPOWDER).displayName("&8&lCOMING SOON").build());
        player.openInventory(getInventory());
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
