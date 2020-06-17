package store.jseries.framework.utils.players;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Cooldowns {

    private static Map<String, Map<UUID, Long>> cooldowns = new HashMap<>();

    public static Map<UUID, Long> get(String key) {
        return cooldowns.getOrDefault(key, new HashMap<>());
    }

    public static void add(String key, UUID uuid, long length) {
        cooldowns.putIfAbsent(key, new HashMap<>());
        get(key).put(uuid, System.currentTimeMillis() + length);
    }

    public static void add(String key, UUID uuid, TimeUnit unit, long length) {
        cooldowns.putIfAbsent(key, new HashMap<>());
        get(key).put(uuid, System.currentTimeMillis() + unit.toMillis(length));
    }

    public static boolean has(String key, UUID uuid) {
        return get(key, uuid) != 0;
    }

    public static long get(String key, UUID uuid) {
        if (!cooldowns.containsKey(key))
            return 0;
        Map<UUID, Long> keyCooldowns = get(key);
        if (!keyCooldowns.containsKey(uuid))
            return 0;
        long expire = keyCooldowns.get(uuid) - System.currentTimeMillis();
        if (expire <= 0) {
            keyCooldowns.remove(uuid);
            return 0;
        }
        return expire;
    }
}
