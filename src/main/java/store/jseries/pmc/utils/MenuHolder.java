package store.jseries.pmc.utils;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class MenuHolder implements InventoryHolder {

    private final String id;
    private final List<Object> data;

    public MenuHolder(String inventory, Object... storage) {
        id = inventory;
        data= Arrays.asList(storage);
    }

    public @NotNull Inventory getInventory() {
        return Bukkit.createInventory(null, 27,"");
    }

    public String getId() {
        return id;
    }

    public List<Object> getData() {
        return data;
    }

    public Object getData(int i) {
        return data.get(i);
    }
}
