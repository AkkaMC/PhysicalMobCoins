package store.jseries.pmc;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import store.jseries.pmc.commands.MainCommand;
import store.jseries.pmc.listeners.*;
import store.jseries.pmc.managers.CoinManager;
import store.jseries.pmc.managers.InventoryManager;
import store.jseries.pmc.managers.SoundManager;
import store.jseries.pmc.supports.CoralShardsSupport;
import store.jseries.pmc.supports.SuperMobCoinsSupport;
import store.jseries.pmc.utils.Support;

import java.io.File;
import java.util.logging.Logger;

public class PhysicalMobCoins extends JavaPlugin {

    @Getter
    private static PhysicalMobCoins instance;

    @Getter
    private CoinManager coinManager;
    @Getter
    private SoundManager soundManager;
    @Getter
    private Support currencySupport;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        File messages = new File(getDataFolder(), "messages.yml");
        if(!messages.exists())
            saveResource("messages.yml", false);

        coinManager = new CoinManager();
        soundManager = new SoundManager();
        InventoryManager.init();

        getServer().getPluginManager().registerEvents(new CoinPickupListener(),this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(),this);
        getServer().getPluginManager().registerEvents(new InventoryPickupItemListener(),this);

        getCommand("pmc").setExecutor(new MainCommand());

        currencySupport = null;
        if(Bukkit.getPluginManager().getPlugin("SuperMobCoins") != null) {
            currencySupport = new SuperMobCoinsSupport();
        } else if (Bukkit.getPluginManager().getPlugin("CoralShards") != null) {
            currencySupport = new CoralShardsSupport();
        }

        /* This means no shard plugin was found */
        if(currencySupport == null) {
            Logger.getLogger("minecraft").info("PhysicalMobCoins > Disabling.");
            Logger.getLogger("minecraft").info("PhysicalMobCoins > No support plugin found.");
            Logger.getLogger("minecraft").info("PhysicalMobCoins > Must be SuperMobCoins.");
            this.getPluginLoader().disablePlugin(this);
        }
    }



}
