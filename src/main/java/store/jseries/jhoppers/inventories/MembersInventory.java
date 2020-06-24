package store.jseries.jhoppers.inventories;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import store.jseries.framework.chat.ChatResponse;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MembersInventory extends JInventory {
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
            String path = "members-menu";
            createInventory("members", config.contains(path + ".name") ? config.getString(path + ".name") : "&8Hopper Menu", 45);
            for (int i = 0; i < getSize(); i++)
                addItem(i, ConfigUtil.fromConfigSection(config, path + ".background"));
            List<Integer> slots = ConfigUtil.slotsFromSection(config, path + ".members");
            List<UUID> memberUUIDS = new ArrayList<>(hopper.getMembers().keySet());
            Map<UUID, Member> members = hopper.getMembers();
            int pos = (page - 1) * slots.size();
            boolean full = true;
            for (int i : slots) {
                if (pos >= members.size()) {
                    addItem(i, new ItemStack(Material.AIR));
                    full = false;
                } else {
                    final UUID uuid = memberUUIDS.get(pos);
                    String memberName = Bukkit.getOfflinePlayer(uuid).getName();
                    Member member = members.get(uuid);
                    String isOwner = member.hasPermission(HopperPermission.OWNER) ? "YES" : "NO;";
                    ItemStack memberItem = new ItemStackBuilder(ConfigUtil.fromConfigSection(config, path + ".members", new Placeholder("%player_head%", "NAME:" + Bukkit.getOfflinePlayer(uuid).getName())
                    , new Placeholder("%member_name%", memberName), new Placeholder("%member_isOwner%", isOwner), new Placeholder("%member_sold%", member.getMoneySold()+""), new Placeholder("%member_withdraw%", member.getItemsWithdrawn()+""))).build();
                    addButton(i, memberItem)
                            .setClick(e -> {
                                JHopper jhopper = (JHopper) ((JInventoryHolder) e.getInventory().getHolder()).getData(1);
                                if (jhopper == null || !jhopper.getMembers().containsKey(e.getWhoClicked().getUniqueId()) || !jhopper.getMembers().containsKey(uuid)) {
                                    e.getWhoClicked().closeInventory();
                                } else {
                                    new MemberEditInventory().open((Player) e.getWhoClicked(), jhopper, uuid);
                                }
                            });
                }
                pos += 1;
            }
            List<Integer> membersSlot = ConfigUtil.slotsFromSection(config, path + ".invite-member");
            for (int i : membersSlot)
                addButton(i, ConfigUtil.fromConfigSection(config, path + ".invite-member"))
                        .setClick(e -> {
                            final JHopper jhopper = (JHopper) ((JInventoryHolder) e.getInventory().getHolder()).getData(1);
                            if (jhopper == null || !jhopper.getMembers().containsKey(e.getWhoClicked().getUniqueId())) {
                                e.getWhoClicked().closeInventory();
                                return;
                            }
                            if (!jhopper.getMembers().get(e.getWhoClicked().getUniqueId()).hasPermission(HopperPermission.OWNER)) {
                                e.getWhoClicked().sendMessage(Message.NO_HOPPER_PERMISSION.getMessage());
                                return;
                            }
                            e.getWhoClicked().closeInventory();
                            e.getWhoClicked().sendMessage(Message.ENTER_MEMBER.getMessage());
                            ChatResponse.addResponse(e.getWhoClicked().getUniqueId(), event -> {
                                if (event.getMessage().equalsIgnoreCase("exit")) {
                                    ChatResponse.removeResponse(event.getPlayer().getUniqueId());
                                    new MembersInventory().open(event.getPlayer(), page, jhopper);
                                    return;
                                }
                                Player pl = Bukkit.getPlayer(event.getMessage());
                                if (pl == null || !pl.isOnline()) {
                                    event.getPlayer().sendMessage(Message.UNKNOWN_PLAYER.getMessage());
                                    return;
                                }
                                if (jhopper.getMembers().containsKey(pl.getUniqueId())) {
                                    event.getPlayer().sendMessage(Message.ALREADY_MEMBER.getMessage());
                                    return;
                                }
                                jhopper.getMembers().put(pl.getUniqueId(), new Member());
                                event.getPlayer().sendMessage(Message.ADDED_MEMBER.toString());
                                ChatResponse.removeResponse(event.getPlayer().getUniqueId());
                                new MemberEditInventory().open(event.getPlayer(), jhopper, pl.getUniqueId());
                            });
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
                                new MembersInventory().open((Player) e.getWhoClicked(), invPage > 1 ? invPage - 1 : 1, jhopper);
                            });
            }
            List<Integer> nextPage = ConfigUtil.slotsFromSection(config, path + ".next-page");
            if (full && memberUUIDS.size() > page * slots.size()) {
                for (int i : nextPage)
                    addButton(i, ConfigUtil.fromConfigSection(config, path + ".next-page"))
                            .setClick(e -> {
                                int invPage = (int) ((JInventoryHolder) e.getInventory().getHolder()).getData(0);
                                JHopper jhopper = (JHopper) ((JInventoryHolder) e.getInventory().getHolder()).getData(1);
                                if (jhopper == null || !jhopper.getMembers().containsKey(e.getWhoClicked().getUniqueId())) {
                                    e.getWhoClicked().closeInventory();
                                    return;
                                }
                                new MembersInventory().open((Player) e.getWhoClicked(), invPage + 1, jhopper);
                            });
            }
            List<Integer> goBack = ConfigUtil.slotsFromSection(config, path + ".back-button");
            for (int i : goBack)
                addButton(i, ConfigUtil.fromConfigSection(config, path + ".back-button"))
                        .setClick(e -> {
                            JHopper jhopper = (JHopper) ((JInventoryHolder) e.getInventory().getHolder()).getData(1);
                            if (jhopper == null || !jhopper.getMembers().containsKey(e.getWhoClicked().getUniqueId())) {
                                e.getWhoClicked().closeInventory();
                                return;
                            }
                            new HopperInventory().open((Player) e.getWhoClicked(), 1, jhopper);
                        });

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
