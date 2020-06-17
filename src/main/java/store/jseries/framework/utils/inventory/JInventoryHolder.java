package store.jseries.framework.utils.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Arrays;
import java.util.List;

public class JInventoryHolder implements InventoryHolder {

    private final String id;
    private final List<Object> data;
    private JInventory jInventory;

    public JInventoryHolder(String inventory, JInventory jInventory, Object... storage) {
        id = inventory;
        this.jInventory = jInventory;
        data= Arrays.asList(storage);
    }

    public Inventory getInventory() {
        return null;
    }

    public JInventory getjInventory() {
        return jInventory;
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
