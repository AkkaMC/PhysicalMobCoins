package store.jseries.pmc.supports;

import me.swanis.mobcoins.MobCoinsAPI;
import me.swanis.mobcoins.profile.Profile;
import store.jseries.pmc.PhysicalMobCoins;
import store.jseries.pmc.listeners.MobCoinsReceiveListener;
import store.jseries.pmc.utils.Support;

import java.util.UUID;

public class SuperMobCoinsSupport extends Support {

    public SuperMobCoinsSupport() {
        PhysicalMobCoins.getInstance().getServer().getPluginManager().registerEvents(new MobCoinsReceiveListener(), PhysicalMobCoins.getInstance());
    }

    @Override
    public void giveCoins(UUID player, int amount) {
        Profile profile = MobCoinsAPI.getProfileManager().getProfile(player);
        profile.setMobCoins(profile.getMobCoins() + amount);
    }

}
