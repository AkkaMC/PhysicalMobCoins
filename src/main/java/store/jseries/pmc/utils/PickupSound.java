package store.jseries.pmc.utils;

import lombok.Getter;
import store.jseries.pmc.utils.warse.Sound;
import store.jseries.pmc.utils.warse.xseries.XMaterial;

public enum PickupSound {

    NONE(XMaterial.REDSTONE_BLOCK,null),
    EXPERIENCE_PICKUP(XMaterial.EXPERIENCE_BOTTLE, Sound.ORB_PICKUP),
    PORTAL(XMaterial.OBSIDIAN, Sound.PORTAL),
    SLIME_ATTACK(XMaterial.SLIME_BALL, Sound.SLIME_ATTACK),
    SLIME_JUMP(XMaterial.SLIME_BLOCK, Sound.SLIME_WALK),
    CHICKEN_PLOP(XMaterial.EGG, Sound.CHICKEN_EGG_POP),
    SHEAR(XMaterial.SHEARS, Sound.SHEEP_SHEAR),
    BURP(XMaterial.BREAD, Sound.BURP),
    ANVIL_LAND(XMaterial.ANVIL,Sound.ANVIL_LAND),
    SHOOT_ARROW(XMaterial.ARROW, Sound.SHOOT_ARROW),
    CLICK(XMaterial.STONE_BUTTON, Sound.CLICK);


    @Getter
    private XMaterial icon;
    @Getter
    private Sound sound;

    PickupSound(XMaterial icon, Sound sound) {
        this.icon = icon;
        this.sound = sound;
    }

}
