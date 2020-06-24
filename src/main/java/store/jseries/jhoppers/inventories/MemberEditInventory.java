package store.jseries.jhoppers.inventories;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import store.jseries.framework.utils.inventory.ItemStackBuilder;
import store.jseries.framework.utils.inventory.JInventory;
import store.jseries.framework.utils.inventory.JInventoryHolder;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.utils.ConfigUtil;
import store.jseries.jhoppers.utils.enums.HopperPermission;
import store.jseries.jhoppers.utils.enums.Message;
import store.jseries.jhoppers.utils.files.Placeholder;
import store.jseries.jhoppers.utils.hopper.JHopper;
import store.jseries.jhoppers.utils.players.Member;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class MemberEditInventory extends JInventory {
    @Override
    public void open(Player player, Object... args) {
        if (args.length > 1) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(new File(JHoppers.getInstance().getDataFolder(), "guis.yml"));
            final JHopper hopper = (JHopper) args[0];
            final UUID member = (UUID) args[1];
            if (!hopper.getMembers().containsKey(member)) {
                new MembersInventory().open(player, 1, hopper);
                return;
            }
            Member memberObject = hopper.getMembers().get(member);
            String path = "member-edit-menu";
            createInventory("member-edit", config.contains(path + ".name") ? config.getString(path + ".name") : "&8Hopper Menu", 45);
            for (int i = 0; i < getSize(); i++)
                addItem(i, ConfigUtil.fromConfigSection(config, path + ".background"));

            List<Integer> goBack = ConfigUtil.slotsFromSection(config, path + ".back-button");
            for (int i : goBack)
                addButton(i, ConfigUtil.fromConfigSection(config, path + ".back-button"))
                        .setClick(e -> {
                            JHopper jhopper = (JHopper) ((JInventoryHolder) e.getInventory().getHolder()).getData(0);
                            if (jhopper == null || !jhopper.getMembers().containsKey(e.getWhoClicked().getUniqueId())) {
                                e.getWhoClicked().closeInventory();
                                return;
                            }
                            new MembersInventory().open((Player) e.getWhoClicked(), 1, jhopper);
                        });
            List<Integer> playerHead = ConfigUtil.slotsFromSection(config, path + ".player-head");
            Member hopperMember = hopper.getMembers().get(member);
            double moneySold = hopperMember.getMoneySold();
            long itemsWithdrawn = hopperMember.getItemsWithdrawn();
           /* builder.displayName(builder.getDisplayName().replaceAll("%member_name%", Bukkit.getOfflinePlayer(member).getName())
                    .replaceAll("%member_sold%", moneySold+"")
                    .replaceAll("%member_withdraw%", itemsWithdrawn+""));
            List<String> lore = builder.getLore();
            for (int x = 0; x < lore.size(); x++)
                lore.set(x, lore.get(x).replaceAll("%member_name%", Bukkit.getOfflinePlayer(member).getName())
                        .replaceAll("%member_sold%", moneySold+"")
                        .replaceAll("%member_withdraw%", itemsWithdrawn+""));
            builder.lore(lore);*/
            ItemStack playerItem = new ItemStackBuilder(ConfigUtil.fromConfigSection(config, path + ".player-head", new Placeholder("%player_head%", "NAME:" + Bukkit.getOfflinePlayer(member).getName())
                    , new Placeholder("%member_name%", Bukkit.getOfflinePlayer(member).getName()), new Placeholder("%member_sold%", moneySold + ""), new Placeholder("%member_withdraw%", itemsWithdrawn + ""))).build();
            for (int i : playerHead)
                addItem(i, playerItem);
            
            List<Integer> toggleBreak = ConfigUtil.slotsFromSection(config, path + ".toggle-break");
            boolean canBreak = memberObject.hasPermission(HopperPermission.BREAK);
            ItemStack toggleBreakItem = new ItemStackBuilder(ConfigUtil.fromConfigSection(config, path + ".toggle-break", new Placeholder("%member_canBreak%", canBreak ? "YES" : "NO"), new Placeholder("%opposite%", canBreak ? "disable" : "enable"))).build();
            for (int i : toggleBreak)
                addButton(i, toggleBreakItem)
                        .setClick(e -> {
                            hopper.togglePermission(member, HopperPermission.BREAK);
                            new MemberEditInventory().open((Player) e.getWhoClicked(), hopper, member);
                        });

            List<Integer> toggleSell = ConfigUtil.slotsFromSection(config, path + ".toggle-sell");
            boolean canSell = memberObject.hasPermission(HopperPermission.BREAK);
            ItemStack toggleSellItem = new ItemStackBuilder(ConfigUtil.fromConfigSection(config, path + ".toggle-sell", new Placeholder("%member_canSell%", canSell ? "YES" : "NO"), new Placeholder("%opposite%", canSell ? "disable" : "enable"))).build();
            for (int i : toggleSell)
                addButton(i, toggleSellItem)
                        .setClick(e -> {
                            hopper.togglePermission(member, HopperPermission.SELL);
                            new MemberEditInventory().open((Player) e.getWhoClicked(), hopper, member);
                        });

            List<Integer> toggleWithdraw = ConfigUtil.slotsFromSection(config, path + ".toggle-withdraw");
            boolean canWithdraw = memberObject.hasPermission(HopperPermission.BREAK);
            ItemStack toggleWithdrawItem = new ItemStackBuilder(ConfigUtil.fromConfigSection(config, path + ".toggle-withdraw", new Placeholder("%member_canWithdraw%", canWithdraw ? "YES" : "NO"), new Placeholder("%opposite%", canWithdraw ? "disable" : "enable"))).build();
            for (int i : toggleWithdraw)
                addButton(i, toggleWithdrawItem)
                        .setClick(e -> {
                            hopper.togglePermission(member, HopperPermission.WITHDRAW);
                            new MemberEditInventory().open((Player) e.getWhoClicked(), hopper, member);
                        });

            List<Integer> transferOwnership = ConfigUtil.slotsFromSection(config, path + ".transfer-ownership");
            ItemStack transferOwnershipItem = new ItemStackBuilder(ConfigUtil.fromConfigSection(config, path + ".transfer-ownership")).build();
            for (int i : transferOwnership)
                addButton(i, transferOwnershipItem)
                        .setClick(e -> {
                            if(!hopper.getMembers().containsKey(e.getWhoClicked().getUniqueId()) || !hopper.getMembers().get(e.getWhoClicked().getUniqueId()).hasPermission(HopperPermission.OWNER)) {
                                e.getWhoClicked().sendMessage(Message.NO_HOPPER_PERMISSION.getMessage());
                                e.getWhoClicked().closeInventory();
                                return;
                            }
                            if(!hopper.getMembers().containsKey(member)) {
                                e.getWhoClicked().closeInventory();
                                return;
                            }
                            hopper.togglePermission(e.getWhoClicked().getUniqueId(),HopperPermission.OWNER);
                            hopper.togglePermission(member, HopperPermission.OWNER);
                            new HopperInventory().open((Player) e.getWhoClicked(),1, hopper);
                        });

            List<Integer> kickMember = ConfigUtil.slotsFromSection(config, path + ".kick-member");
            ItemStack kickMemberItem = new ItemStackBuilder(ConfigUtil.fromConfigSection(config, path + ".kick-member")).build();
            for (int i : kickMember)
                addButton(i, kickMemberItem)
                        .setClick(e -> {
                            if(!hopper.getMembers().containsKey(e.getWhoClicked().getUniqueId()) || !hopper.getMembers().get(e.getWhoClicked().getUniqueId()).hasPermission(HopperPermission.OWNER)) {
                                e.getWhoClicked().sendMessage(Message.NO_HOPPER_PERMISSION.getMessage());
                                e.getWhoClicked().closeInventory();
                                return;
                            }
                            if(!hopper.getMembers().containsKey(member)) {
                                e.getWhoClicked().closeInventory();
                                return;
                            }
                            hopper.removeMember(member);
                            new MembersInventory().open((Player) e.getWhoClicked(),1, hopper);
                        });

            player.openInventory(getInventory(hopper, member));
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
