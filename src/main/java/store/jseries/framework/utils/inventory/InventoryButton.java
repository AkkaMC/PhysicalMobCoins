package store.jseries.framework.utils.inventory;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import store.jseries.framework.xseries.XMaterial;

import java.util.function.Consumer;

public class InventoryButton {

    private ItemStackBuilder displayItem;
    private Consumer<InventoryClickEvent> onClick;
    private Consumer<InventoryClickEvent> onMiddleClick;
    private Consumer<InventoryClickEvent> onLeftClick;
    private Consumer<InventoryClickEvent> onRightClick;

    public InventoryButton(ItemStack item) {
        displayItem = new ItemStackBuilder(item);
    }


    public InventoryButton(XMaterial mat) {
        displayItem = new ItemStackBuilder(mat);
    }

    public InventoryButton setClick(Consumer<InventoryClickEvent> onClick) {
        this.onClick = onClick;
        return this;
    }

    public InventoryButton setMiddleClick(Consumer<InventoryClickEvent> onMiddleClick) {
        this.onMiddleClick = onMiddleClick;
        return this;
    }

    public InventoryButton setLeftClick(Consumer<InventoryClickEvent> onLeftClick) {
        this.onLeftClick = onLeftClick;
        return this;
    }

    public InventoryButton setRightClick(Consumer<InventoryClickEvent> onRightClick) {
        this.onRightClick = onRightClick;
        return this;
    }

    public ItemStack getDisplayItem() {
        return displayItem.build();
    }

    public void setDisplayItem(ItemStack item) {
        displayItem = new ItemStackBuilder(item);
    }

    public void setDisplayItem(ItemStackBuilder builder) {
        displayItem = builder;
    }

    public void onClick(InventoryClickEvent event) {
        if (onClick != null)
            onClick.accept(event);
        if (event.getClick().equals(ClickType.MIDDLE) && onMiddleClick != null)
            onMiddleClick.accept(event);
        else if (event.getClick().equals(ClickType.RIGHT) && onRightClick != null)
            onRightClick.accept(event);
        else if (event.getClick().equals(ClickType.LEFT) && onLeftClick != null)
            onLeftClick.accept(event);
    }


}
