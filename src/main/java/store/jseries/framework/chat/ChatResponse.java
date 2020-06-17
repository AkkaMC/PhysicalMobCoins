package store.jseries.framework.chat;

import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import store.jseries.framework.JFramework;
import store.jseries.framework.chat.listeners.ChatResponseListener;
import store.jseries.jhoppers.JHoppers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class ChatResponse {

    private static Map<UUID, ResponseType> responses;

    public static void enable() {
        responses = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(new ChatResponseListener(), JFramework.getPlugin());
    }

    public static void addResponse(UUID uuid, Consumer<AsyncPlayerChatEvent> onResponse) {
        responses.put(uuid,new ResponseType(onResponse));
    }

    public static boolean hasResponse(UUID uuid) {
        return responses.containsKey(uuid);
    }

    public static void removeResponse(UUID uuid) {
        responses.remove(uuid);
    }
    public static void handleResponse(UUID uuid, AsyncPlayerChatEvent e) {

        Bukkit.getScheduler().runTask(JHoppers.getInstance(), new Runnable() {
            @Override
            public void run() {
                responses.get(uuid).chat(e);
            }
        });
    }



}
