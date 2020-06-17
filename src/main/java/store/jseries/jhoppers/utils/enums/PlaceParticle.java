package store.jseries.jhoppers.utils.enums;

import lombok.Getter;
import org.bukkit.Location;
import store.jseries.framework.particles.ParticleEffect;
import store.jseries.framework.xseries.XMaterial;

public enum PlaceParticle {

    NONE("NO", XMaterial.GLASS, null),
    REDSTONE("REDSTONE", XMaterial.REDSTONE, ParticleEffect.REDSTONE),
    FIRE("FIRE", XMaterial.BLAZE_POWDER, ParticleEffect.FLAME),
    VILLAGER("VILLAGER", XMaterial.EMERALD, ParticleEffect.VILLAGER_HAPPY);

    @Getter
    private String name;
    @Getter
    private XMaterial id;
    private ParticleEffect effect;

    PlaceParticle(String str, XMaterial mat, ParticleEffect particleEffect) {
        name = str;
        id = mat;
        effect = particleEffect;
    }

    public void display(Location loc) {
        if (effect != null)
            effect.display(3, 3, 3, 3, 25, loc);
    }

    public static PlaceParticle fromString(String s) {
        for (PlaceParticle part : PlaceParticle.values()) {
            if (part.getName().equalsIgnoreCase(s))
                return part;
        }
        return null;
    }

}
