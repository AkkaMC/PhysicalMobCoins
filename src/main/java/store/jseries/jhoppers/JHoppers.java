package store.jseries.jhoppers;

import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import store.jseries.framework.JFramework;
import store.jseries.jhoppers.command.MainCommand;
import store.jseries.jhoppers.listeners.ItemSpawnReceiver;
import store.jseries.jhoppers.managers.HopperTypeManager;
import store.jseries.jhoppers.managers.JHopperManager;
import store.jseries.jhoppers.managers.PermissionManager;
import store.jseries.jhoppers.managers.PriceManager;
import store.jseries.jhoppers.supports.HolographicDisplaysSupport;
import store.jseries.jhoppers.supports.UltimateStackerSupport;
import store.jseries.jhoppers.supports.WildStackerSupport;
import store.jseries.jhoppers.utils.hopper.HopperType;

import java.io.File;

public class JHoppers extends JavaPlugin {

    private static final String[] FILES = {"guis.yml", "hopper-types.yml", "messages.yml", "prices.yml", "storage.yml"};
    private static final Listener[] LISTENERS = {new ItemSpawnReceiver()};

    @Getter
    private static JHoppers instance;

    @Getter
    private HopperTypeManager hopperTypeManager;
    @Getter
    private PriceManager priceManager;
    @Getter
    private JHopperManager hopperManager;
    @Getter
    private PermissionManager permissionManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        for (String s : FILES)
            if (!new File(getDataFolder(), s).exists())
                saveResource(s, false);
        JFramework.enable(instance);
        priceManager = new PriceManager();
        hopperManager = new JHopperManager();
        hopperTypeManager = new HopperTypeManager();
        permissionManager = new PermissionManager();
        if(getServer().getPluginManager().getPlugin("WildStacker") != null)
            WildStackerSupport.setEnabled(true);
        if(getServer().getPluginManager().getPlugin("UltimateStacker") != null)
            UltimateStackerSupport.setEnabled(true);
        if(getServer().getPluginManager().getPlugin("HolographicDisplays") != null)
            HolographicDisplaysSupport.setEnabled(true);
        getCommand("jhoppers").setExecutor(new MainCommand());
        for(Listener listener : LISTENERS)
            getServer().getPluginManager().registerEvents(listener,this);
    }

    @Override
    public void onDisable() {
        hopperManager.disable();
        JFramework.disable();
    }
}
