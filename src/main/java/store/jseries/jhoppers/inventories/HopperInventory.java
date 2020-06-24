package store.jseries.jhoppers.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import store.jseries.framework.utils.inventory.ItemStackBuilder;
import store.jseries.framework.utils.inventory.JInventory;
import store.jseries.framework.utils.inventory.JInventoryHolder;
import store.jseries.framework.xseries.XMaterial;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.utils.ConfigUtil;
import store.jseries.jhoppers.utils.enums.HopperFeature;
import store.jseries.jhoppers.utils.enums.HopperPermission;
import store.jseries.jhoppers.utils.enums.Message;
import store.jseries.jhoppers.utils.files.Placeholder;
import store.jseries.jhoppers.utils.hopper.HopperType;
import store.jseries.jhoppers.utils.hopper.JHopper;
import store.jseries.jhoppers.utils.players.Member;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HopperInventory extends JInventory {
    @Override
    public void open(Player player, Object... args) {
        if (args.length > 1) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(new File(JHoppers.getInstance().getDataFolder(), "guis.yml"));
            int page = (Integer) args[0];
            final JHopper hopper = (JHopper) args[1];
            if (hopper == null) {
                player.closeInventory();
                return;
            }
            String path = "hopper-menu";
            createInventory("jhopper", config.contains(path + ".name") ? config.getString(path + ".name") : "&8Hopper Menu", 45);
            for (int i = 0; i < getSize(); i++)
                addItem(i, ConfigUtil.fromConfigSection(config, path + ".background"));
            List<Integer> slots = ConfigUtil.slotsFromSection(config, path + ".items");
            HopperType type = JHoppers.getInstance().getHopperTypeManager().getType(hopper.getHopperType());
            List<XMaterial> items = type.getPickupItems();
            Map<XMaterial, Long> itemAmounts = hopper.getItems();
            int pos = (page - 1) * slots.size();
            boolean full = true;
            for (int i : slots) {
                if (pos >= items.size()) {
                    addItem(i, new ItemStack(Material.AIR));
                    full = false;
                } else {
                    XMaterial mat = items.get(pos);
                    String typeStr = mat.name().replaceAll("_", "");
                    long itemAmount = itemAmounts.containsKey(mat) ? itemAmounts.get(mat) : 0;
                    final double worth = JHoppers.getInstance().getPriceManager().getPrice(mat);
                    double total = worth * itemAmount;
                    ItemStackBuilder builder = new ItemStackBuilder(ConfigUtil.fromConfigSection(config, path + ".items", new Placeholder("%item_material%", mat.name())
                    , new Placeholder("%item_amount%",itemAmount + ""), new Placeholder("%item_worth%", worth + ""), new Placeholder("%items_worth%", total + ""), new Placeholder("%item_material_name%", typeStr)));
                    List<String> lore = builder.getLore();
                    String autosell = "";
                    String asSymbol = "%autosell%";
                    if (type.getFeatures().contains(HopperFeature.AUTO_SELL))
                        autosell = config.contains(path + ".items.autosell") ? config.getString(path + ".items.autosell") : "&cAUTOSELL ENABLED";
                    for (int x = 0; x < lore.size(); x++)
                        lore.set(x, lore.get(x).contains(asSymbol) ? autosell : lore.get(x).replaceAll(asSymbol, ""));
                    builder.lore(lore);
                    addButton(i, builder.build())
                            .setLeftClick(e -> {
                                JHopper jhopper = (JHopper) ((JInventoryHolder) e.getInventory().getHolder()).getData(1);
                                if (jhopper == null || !jhopper.getMembers().containsKey(e.getWhoClicked().getUniqueId())) {
                                    e.getWhoClicked().closeInventory();
                                } else {
                                    HopperType hopperType = JHoppers.getInstance().getHopperTypeManager().getType(jhopper.getHopperType());
                                    if (hopperType.getFeatures().contains(HopperFeature.AUTO_SELL)) {
                                        e.getWhoClicked().sendMessage(Message.AUTO_SELL_ENABLED.getMessage());
                                        return;
                                    }
                                    Member member = jhopper.getMembers().get(e.getWhoClicked().getUniqueId());
                                    if (!member.hasPermission(HopperPermission.SELL)) {
                                        e.getWhoClicked().sendMessage(Message.NO_HOPPER_PERMISSION.getMessage());
                                        return;
                                    }
                                    if (!jhopper.getItems().containsKey(mat) || jhopper.getItems().get(mat) <= 0) {
                                        e.getWhoClicked().sendMessage(Message.NO_ITEMS_LEFT.getMessage());
                                        return;
                                    }
                                    long amt = jhopper.getItems().get(mat);
                                    jhopper.removeAllItem(mat);
                                    double price = amt * JHoppers.getInstance().getPriceManager().getPrice(mat);
                                    jhopper.soldItems(e.getWhoClicked().getUniqueId(), price);
                                    JHoppers.getEcon().depositPlayer(Bukkit.getOfflinePlayer(e.getWhoClicked().getUniqueId()), price);
                                    e.getWhoClicked().sendMessage(Message.SOLD_ITEMS.getMessage().replaceAll("%price%", price + ""));
                                    new HopperInventory().open((Player) e.getWhoClicked(), page, jhopper);
                                }
                            }).setRightClick(e -> {
                        JHopper jhopper = (JHopper) ((JInventoryHolder) e.getInventory().getHolder()).getData(1);
                        if (!jhopper.getMembers().containsKey(e.getWhoClicked().getUniqueId())) {
                            e.getWhoClicked().closeInventory();
                        } else {
                            HopperType hopperType = JHoppers.getInstance().getHopperTypeManager().getType(jhopper.getHopperType());
                            if (hopperType.getFeatures().contains(HopperFeature.AUTO_SELL)) {
                                e.getWhoClicked().sendMessage(Message.AUTO_SELL_ENABLED.getMessage());
                                return;
                            }
                            Member member = jhopper.getMembers().get(e.getWhoClicked().getUniqueId());
                            if (!member.hasPermission(HopperPermission.WITHDRAW)) {
                                e.getWhoClicked().sendMessage(Message.NO_HOPPER_PERMISSION.getMessage());
                                return;
                            }
                            if (!jhopper.getItems().containsKey(mat) || jhopper.getItems().get(mat) <= 0) {
                                e.getWhoClicked().sendMessage(Message.NO_ITEMS_LEFT.getMessage());
                                return;
                            }
                            if (e.getWhoClicked().getInventory().firstEmpty() == -1) {
                                e.getWhoClicked().sendMessage(Message.FULL_INVENTORY.getMessage());
                                return;
                            }
                            long amt = jhopper.getItems().get(mat);
                            int left = 0;
                            for (Map.Entry<Integer, ItemStack> entry : e.getWhoClicked().getInventory().addItem(new ItemStackBuilder(mat).amount((int) amt).build()).entrySet())
                                left += entry.getValue().getAmount();
                            jhopper.removeItem(mat, amt - left);
                            if (amt - left < 1) {
                                e.getWhoClicked().sendMessage(Message.FULL_INVENTORY.getMessage());
                                return;
                            }
                            jhopper.withdrawItems(e.getWhoClicked().getUniqueId(), (int) amt - left);
                            e.getWhoClicked().sendMessage(Message.WITHDRAW_ITEMS.getMessage().replaceAll("%amount%", (amt - left) + ""));
                            new HopperInventory().open((Player) e.getWhoClicked(), page, jhopper);
                        }
                    });
                }
                pos += 1;
            }
            List<Integer> membersSlot = ConfigUtil.slotsFromSection(config, path + ".manage-members");
            for (int i : membersSlot)
                addButton(i, ConfigUtil.fromConfigSection(config, path + ".manage-members"))
                        .setClick(e -> {
                            JHopper jhopper = (JHopper) ((JInventoryHolder) e.getInventory().getHolder()).getData(1);
                            if (jhopper == null || !jhopper.getMembers().containsKey(e.getWhoClicked().getUniqueId())) {
                                e.getWhoClicked().closeInventory();
                                return;
                            }
                            new MembersInventory().open((Player) e.getWhoClicked(), 1, jhopper);
                        });
            List<Integer> previousPage = ConfigUtil.slotsFromSection(config, path + ".previous-page");
            if (page > 1) {
                for (int i : previousPage)
                    addButton(i, ConfigUtil.fromConfigSection(config, path + ".previous-page"))
                            .setClick(e -> {
                                int invPage = (int) ((JInventoryHolder) e.getInventory().getHolder()).getData(0);
                                JHopper jhopper = (JHopper) ((JInventoryHolder) e.getInventory().getHolder()).getData(1);
                                if (jhopper == null || !jhopper.getMembers().containsKey(e.getWhoClicked().getUniqueId())) {
                                    e.getWhoClicked().closeInventory();
                                    return;
                                }
                                new HopperInventory().open((Player) e.getWhoClicked(), invPage > 1 ? invPage - 1 : 1, jhopper);
                            });
            }
            List<Integer> nextPage = ConfigUtil.slotsFromSection(config, path + ".next-page");
            if (full && items.size() > page * slots.size()) {
                for (int i : nextPage)
                    addButton(i, ConfigUtil.fromConfigSection(config, path + ".next-page"))
                            .setClick(e -> {
                                int invPage = (int) ((JInventoryHolder) e.getInventory().getHolder()).getData(0);
                                JHopper jhopper = (JHopper) ((JInventoryHolder) e.getInventory().getHolder()).getData(1);
                                if (jhopper == null || !jhopper.getMembers().containsKey(e.getWhoClicked().getUniqueId())) {
                                    e.getWhoClicked().closeInventory();
                                    return;
                                }
                                new HopperInventory().open((Player) e.getWhoClicked(), invPage + 1, jhopper);
                            });
            }
            player.openInventory(getInventory(page, hopper));
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
    public void onPlayerPersonalInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }
}
