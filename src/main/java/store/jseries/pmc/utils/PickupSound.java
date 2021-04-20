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
    CLICK(XMaterial.STONE_BUTTON, Sound.CLICK),
    DOOR_CLOSE(XMaterial.OAK_DOOR, Sound.DOOR_CLOSE),
    HIT(XMaterial.DIAMOND_SWORD, Sound.SUCCESSFUL_HIT),
    BLAZE_HIT(XMaterial.BLAZE_ROD, Sound.BLAZE_HIT),
    FIRE(XMaterial.FLINT_AND_STEEL, Sound.FIRE),
    CHEST_OPEN(XMaterial.CHEST, Sound.CHEST_OPEN);

    @Getter
    private XMaterial icon;
    @Getter
    private Sound sound;

    PickupSound(XMaterial icon, Sound sound) {
        this.icon = icon;
        this.sound = sound;
    }

}
