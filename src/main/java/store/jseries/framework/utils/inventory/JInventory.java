package store.jseries.framework.utils.inventory;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import store.jseries.framework.utils.JUtils;
import store.jseries.framework.xseries.XMaterial;

import java.util.HashMap;
import java.util.Map;

public abstract class JInventory extends JUtils {

    @Getter
    private Map<Integer, InventoryButton> buttons;
    @Getter
    private String id;
    @Getter
    private Object[] args;
    @Getter
    private Map<Integer, ItemStack> items;
    @Getter
    private String inventoryName;
    @Getter
    private int size;

    protected JInventory createInventory(String id, String name) {
        return createInventory(id, name, 54);
    }

    protected JInventory createInventory(String id, String name, int size) {
        inventoryName = ChatColor.translateAlternateColorCodes('&',name);
        this.size = size;
        this.id = id;
        items = new HashMap<>();
        buttons = new HashMap<>();
        args = new Object[]{};
        return this;
    }

    public void addItem(int slot, ItemStack item) {
        this.items.put(slot,item);
    }

    public InventoryButton addButton(int slot, XMaterial material, String name) {
        return addButton(slot, new ItemStackBuilder(material).displayName(name).build());
    }

    public InventoryButton addButton(int slot, ItemStack item) {
        InventoryButton button = new InventoryButton(item);
        this.buttons.put(slot, button);
        this.items.put(slot,item);
        return button;
    }

    public Inventory getInventory(Object... args) {
        this.args = args;
        Inventory inv = Bukkit.createInventory(new JInventoryHolder("cove-" + getId(), this,args),size,inventoryName);
        for(int i : items.keySet())
            inv.setItem(i, items.get(i));
        return inv;
    }

    public abstract void open(Player player, Object... args);

    public abstract void onClose(InventoryCloseEvent event);

    public abstract void onInventoryClick(InventoryClickEvent event);

    public abstract void onPlayerPersonalInventoryClick(InventoryClickEvent event);

    public static JInventory fromInventory(Inventory inv) {
        if(inv.getHolder() == null || ! (inv.getHolder() instanceof JInventoryHolder))
            return null;
        return ((JInventoryHolder) inv.getHolder()).getjInventory();
    }

}
