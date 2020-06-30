package store.jseries.pmc.utils;

import lombok.Getter;
import store.jseries.pmc.utils.warse.xseries.XMaterial;

public enum PickupSound {

    NONE(null,null),
    EXPERIENCE_PICKUP(XMaterial.EXPERIENCE_BOTTLE, Sound.ORB_PICKUP),
    PORTAL(XMaterial.NETHER_PORTAL, Sound.PORTAL),
    SLIME_ATTACK(XMaterial.SLIME_BALL, Sound.SLIME_ATTACK),
    SLIME_JUMP(XMaterial.SLIME_BLOCK, Sound.SLIME_WALK),
    CHICKEN_PLOP(XMaterial.EGG, Sound.CHICKEN_EGG_POP),
    SHEAR(XMaterial.SHEARS, Sound.SHEEP_SHEAR);


    @Getter
    private XMaterial icon;
    @Getter
    private Sound sound;

    PickupSound(XMaterial icon, Sound sound) {
        this.icon = icon;
        this.sound = sound;
    }

}
