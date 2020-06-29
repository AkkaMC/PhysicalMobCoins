package store.jseries.jhoppers.supports;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class SuperBoostersSupport {

    @Getter
    @Setter
    public static boolean enabled = false;

    public static double getBooster(UUID uuid) {
        if(!SuperBoostersSupport.isEnabled())
            return 1.0;
        for(me.swanis.boosters.booster.ActiveBooster booster : me.swanis.boosters.BoostersAPI.getBoosterManager().getActiveBoosters()) {
            if(booster.getBooster().getName().equalsIgnoreCase("money"))
                return 2.0;
        }
        for(me.swanis.boosters.booster.ActiveBooster booster : me.swanis.boosters.BoostersAPI.getBoosterManager().getActivePersonalBoosters(uuid)) {
            if(booster.getBooster().getName().equalsIgnoreCase("money"))
                return 2.0;
        }
        return 1.0;
    }
}
