package store.jseries.framework.chat;

import lombok.Getter;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ResponseType {

    private Consumer<AsyncPlayerChatEvent> onResponse;
    @Getter
    private List<Object> data;

    public ResponseType(Consumer<AsyncPlayerChatEvent> onChat, Object... objects) {
        onResponse = onChat;
        data = new ArrayList<>();
        data.addAll(Arrays.asList(objects));
    }

    public void chat(AsyncPlayerChatEvent e) {
        onResponse.accept(e);
    }

}
