package store.jseries.framework.chat.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import store.jseries.framework.chat.ChatResponse;

public class ChatResponseListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if(ChatResponse.hasResponse(e.getPlayer().getUniqueId())) {
            ChatResponse.handleResponse(e.getPlayer().getUniqueId(), e);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if(ChatResponse.hasResponse(e.getPlayer().getUniqueId()))
            ChatResponse.removeResponse(e.getPlayer().getUniqueId());
    }

}
