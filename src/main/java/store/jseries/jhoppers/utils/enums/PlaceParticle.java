package store.jseries.jhoppers.utils.enums;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import store.jseries.framework.particles.ParticleEffect;
import store.jseries.framework.xseries.XMaterial;

import java.util.ArrayList;

public enum PlaceParticle {

    NONE("NO", XMaterial.GLASS, 0,null),
    REDSTONE("REDSTONE", XMaterial.REDSTONE,15, ParticleEffect.REDSTONE),
    FIRE("FIRE", XMaterial.BLAZE_POWDER,3, ParticleEffect.FLAME),
    VILLAGER("VILLAGER", XMaterial.EMERALD, 15,ParticleEffect.VILLAGER_HAPPY);

    @Getter
    private String name;
    @Getter
    private XMaterial id;
    private int speed;
    private ParticleEffect effect;

    PlaceParticle(String str, XMaterial mat, int speed,ParticleEffect particleEffect) {
        name = str;
        id = mat;
        this.speed = speed;
        effect = particleEffect;
    }

    public void display(Location loc) {
        if (effect != null) {
            effect.display(1, 1, 1, speed, 25, loc, new ArrayList<>(Bukkit.getOnlinePlayers()));
        }
    }

    public static PlaceParticle fromString(String s) {
        for (PlaceParticle part : PlaceParticle.values()) {
            if (part.getName().equalsIgnoreCase(s))
                return part;
        }
        return null;
    }

}
