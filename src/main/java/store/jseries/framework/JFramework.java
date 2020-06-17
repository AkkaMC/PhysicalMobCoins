package store.jseries.framework;

import org.bukkit.plugin.java.JavaPlugin;
import store.jseries.framework.chat.ChatResponse;
import store.jseries.framework.inventory.InventoryManager;

public class JFramework {

    private static boolean enabled = false;
    private static JavaPlugin plugin;

    // public static void setEnabled(boolean enabled) {
    //    JFramework.enabled = enabled;
    // }

    public static void enable(JavaPlugin plugin) {
        JFramework.plugin = plugin;
        InventoryManager.enable();
        ChatResponse.enable();
        JFramework.enabled = true;
    }

    public static void disable() {
        JFramework.enabled = false;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}
