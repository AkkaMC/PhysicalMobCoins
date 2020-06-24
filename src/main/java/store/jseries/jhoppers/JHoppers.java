package store.jseries.jhoppers;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import store.jseries.framework.JFramework;
import store.jseries.jhoppers.command.MainCommand;
import store.jseries.jhoppers.listeners.BlockGrowReceiver;
import store.jseries.jhoppers.listeners.BlockPlaceReceiver;
import store.jseries.jhoppers.listeners.ItemSpawnReceiver;
import store.jseries.jhoppers.listeners.PlayerInteractReceiver;
import store.jseries.jhoppers.managers.HopperTypeManager;
import store.jseries.jhoppers.managers.JHopperManager;
import store.jseries.jhoppers.managers.PermissionManager;
import store.jseries.jhoppers.managers.PriceManager;
import store.jseries.jhoppers.supports.HolographicDisplaysSupport;
import store.jseries.jhoppers.supports.UltimateStackerSupport;
import store.jseries.jhoppers.supports.WildStackerSupport;

import java.io.File;
import java.util.logging.Logger;

public class JHoppers extends JavaPlugin {

    private static final String[] FILES = {"guis.yml", "hopper-types.yml", "messages.yml", "prices.yml", "storage.yml"};
    private static final Listener[] LISTENERS = {new ItemSpawnReceiver(), new BlockPlaceReceiver(), new PlayerInteractReceiver()
    , new BlockGrowReceiver()};

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
    @Getter
    private static Economy econ = null;

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
            HolographicDisplaysSupport.setEnabled(getConfig());


        if (!setupEconomy() ) {
            Logger.getLogger("minecraft").severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getCommand("jhoppers").setExecutor(new MainCommand());
        for(Listener listener : LISTENERS)
            getServer().getPluginManager().registerEvents(listener,this);

    }

    @Override
    public void onDisable() {
        hopperManager.disable();
        JFramework.disable();
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
