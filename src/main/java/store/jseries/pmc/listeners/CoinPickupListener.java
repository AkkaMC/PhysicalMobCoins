package store.jseries.pmc.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import store.jseries.pmc.PhysicalMobCoins;
import store.jseries.pmc.api.CoinPickupEvent;
import store.jseries.pmc.managers.SoundManager;
import store.jseries.pmc.utils.Message;

public class CoinPickupListener implements Listener {

    private boolean sendMessage;

    public CoinPickupListener() {
        sendMessage = true;
        if(PhysicalMobCoins.getInstance().getConfig().contains("send-message-on-pickup"))
            sendMessage = PhysicalMobCoins.getInstance().getConfig().getBoolean("send-message-on-pickup");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCoinPickup(CoinPickupEvent e) {
        if(!e.isCancelled() && e.getCoinAmount() > 0) {
            PhysicalMobCoins.getInstance().getCurrencySupport().giveCoins(e.getPlayer().getUniqueId(), e.getCoinAmount());
            e.getItem().remove();
            PhysicalMobCoins.getInstance().getSoundManager().playSound(e.getPlayer());
            if(sendMessage)
                e.getPlayer().sendMessage(Message.PICKUP_COINS.getMessage().replaceAll("%amount%", e.getCoinAmount()+""));
        }
    }

}
