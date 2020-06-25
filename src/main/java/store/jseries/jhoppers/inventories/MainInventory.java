package store.jseries.jhoppers.inventories;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
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
import store.jseries.framework.utils.inventory.SkullUtils;
import store.jseries.framework.xseries.XMaterial;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.utils.enums.Message;
import store.jseries.jhoppers.utils.enums.Skin;
import store.jseries.jhoppers.utils.hopper.HopperType;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MainInventory extends JInventory {

    @Override
    public void open(Player player, Object... args) {
        int page;
        List<HopperType> types = JHoppers.getInstance().getHopperTypeManager().getTypes();
        int totalPages = types.size() / 15 + 1;
        if (args.length >= 1)
            page = (int) args[0];
        else
            page = 1;
        if (totalPages < page)
            page = totalPages;
        createInventory("main", "&8JHoppers Menu", 45);
        for (int i : Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 15, 17, 18, 24, 26, 27, 33, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44))
            addItem(i, new ItemStackBuilder(XMaterial.BLACK_STAINED_GLASS_PANE).displayName(" ").build());
        final int finalPage = page;
        addButton(16, new ItemStackBuilder(SkullUtils.getSkullFromUrl(Skin.PLUS.getUrl())).displayName("&9&lCREATE HOPPER").lore("&7Click to add a new hopper type.").build())
                .setClick(e -> {
                    e.getWhoClicked().closeInventory();
                    TextComponent message = buildTextComponent(Message.PREFIX.getMessage() + ChatColor.AQUA + ChatColor.BOLD.toString() + "CLICK ME" + ChatColor.GRAY + " to create a new hopper type.");
                    setHoverMessage(message, ChatColor.DARK_GRAY + "Click to create a new jhopper type");
                    setClickAction(message, ClickEvent.Action.SUGGEST_COMMAND, "/jh create ");
                    ((Player) e.getWhoClicked()).spigot().sendMessage(message);
                });
        addButton(25, new ItemStackBuilder(SkullUtils.getSkullFromUrl(Skin.REDSTONE_BLOCK.getUrl())).displayName("&9&lMANAGE PERMISSIONS").lore("&7Click to manage the permissions.").build())
                .setClick(e -> {
                    new PermissionsInventory().open(player);
                });
        addButton(34, new ItemStackBuilder(SkullUtils.getSkullFromUrl(Skin.MONEY.getUrl())).displayName("&9&lCONFIGURE PRICES").lore("&7Click to manage the prices.").build())
                .setClick(e -> {
                    message(e.getWhoClicked(), "&7In order to configure prices - ");
                    message(e.getWhoClicked(), "&b&l1. &7Turn the server off.");
                    message(e.getWhoClicked(), "&b&l2. &7Manage any items in prices.yml.");
                    message(e.getWhoClicked(), "&b&l3. &7Save the file.");
                    message(e.getWhoClicked(), "&b&l4. &7You're good to go!");
                });
        if (page > 1)
            addButton(18, new ItemStackBuilder(XMaterial.STONE_BUTTON).displayName("&a&l< PREVIOUS PAGE").build())
                    .setClick(e -> {
                        new MainInventory().open(player, finalPage - 1);
                    });
        if (page < totalPages)
            addButton(24, new ItemStackBuilder(XMaterial.STONE_BUTTON).displayName("&a&lNEXT PAGE >").build())
                    .setClick(e -> {
                        new MainInventory().open(player, finalPage + 1);
                    });
        int slot = 10;
        for (int i = (page-1) * 15; i < page * 15; i++) {
            if (slot == 15)
                slot = 19;
            if (slot == 24)
                slot = 28;
            if (types.size() > i) {
                final int x = i;
                addButton(slot, new ItemStackBuilder(types.get(i).getItem()).lore(types.get(i).getItemLore(), "","&7(( &7Type &f/jh give " + types.get(i).getId() + " &7to receive one! ))").build())
                        .setClick(e -> {
                            new HopperEditInventory().open(player,types.get(x));
                        });
            } else {
                addItem(slot, new ItemStack(Material.AIR));
            }
            slot += 1;
        }
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
