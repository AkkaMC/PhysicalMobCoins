package store.jseries.pmc.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import store.jseries.pmc.PhysicalMobCoins;
import store.jseries.pmc.managers.InventoryManager;
import store.jseries.pmc.utils.MenuHolder;
import store.jseries.pmc.utils.PickupSound;
import store.jseries.pmc.utils.Skin;
import store.jseries.pmc.utils.warse.ItemStackBuilder;
import store.jseries.pmc.utils.warse.SkullUtils;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if (e.getCurrentItem() != null && e.getClickedInventory() != null) {
            if (e.getInventory().getHolder() != null && e.getInventory().getHolder() instanceof MenuHolder) {
                e.setCancelled(true);
                if (e.getClickedInventory() != null && e.getClickedInventory().getHolder() != null && e.getClickedInventory().getHolder() instanceof MenuHolder) {
                    MenuHolder holder = (MenuHolder) e.getClickedInventory().getHolder();
                    if (holder.getId().equals("pmc-main")) {
                        if (e.getSlot() == 30)
                            e.getWhoClicked().openInventory(InventoryManager.getItems());
                        if (e.getSlot() == 31)
                            e.getWhoClicked().openInventory(InventoryManager.getSounds());
                        if (e.getSlot() == 32)
                            Bukkit.dispatchCommand(e.getWhoClicked(), "pmc support");
                    } else if (holder.getId().equals("pmc-items")) {
                        if (e.getSlot() == 25)
                            e.getWhoClicked().openInventory(InventoryManager.getMain());
                        else {
                            if (InventoryManager.getListSlots().contains(e.getSlot())) {
                                try {
                                    Skin skin = Skin.values()[InventoryManager.getListSlots().indexOf(e.getSlot())];
                                    if (skin != Skin.CUSTOM) {
                                        PhysicalMobCoins.getInstance().getCoinManager().setCoinItem(new ItemStackBuilder(SkullUtils.getSkullFromUrl(skin.getLink())).build(), skin);
                                        e.getWhoClicked().openInventory(InventoryManager.getItems());
                                    }
                                } catch (Exception ignored) {
                                }
                            }
                        }
                    } else if (holder.getId().equals("pmc-sounds")) {
                        if (e.getSlot() == 25)
                            e.getWhoClicked().openInventory(InventoryManager.getMain());
                        else {
                            PickupSound sound = PhysicalMobCoins.getInstance().getSoundManager().soundFromSlot(e.getSlot());
                            if (sound != null) {
                                if(e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.SHIFT_RIGHT) {
                                    PhysicalMobCoins.getInstance().getSoundManager().playSound((Player) e.getWhoClicked(), sound);
                                } else {
                                    if (sound == PhysicalMobCoins.getInstance().getSoundManager().getSound()) {
                                        e.getWhoClicked().sendMessage(ChatColor.RED + "That is already selected.");
                                        return;
                                    }
                                    PhysicalMobCoins.getInstance().getSoundManager().setSound(sound);
                                    e.getWhoClicked().openInventory(InventoryManager.getSounds());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
