package store.jseries.pmc.api;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CoinPickupEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private boolean isCancelled;
    @Getter
    private Player player;
    private int amount;
    @Getter
    private Item item;


    public CoinPickupEvent(Player player, int amount, Item item) {
        isCancelled = false;
        this.player = player;
        this.amount = amount;
        this.item = item;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        isCancelled = b;
    }

    public int getCoinAmount() {
        return amount;
    }

    public void setCoinAmount(int amount) {
        this.amount = amount;
    }

}
