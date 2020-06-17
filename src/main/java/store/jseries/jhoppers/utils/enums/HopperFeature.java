package store.jseries.jhoppers.utils.enums;

import lombok.Getter;
import store.jseries.framework.xseries.XMaterial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum HopperFeature {

    AUTO_KILL("auto-kill", XMaterial.SPAWNER, '6', "&7Mobs will be automatically killed as", "&7they are spawned and their drops", "&7will be put inside the hopper."),
    AUTO_HARVEST("auto-harvest", XMaterial.GOLDEN_HOE, 'e', "&7Crops in the chunk will be", "&7automatically harvested and the", "&7hopper will receive the drops."),
    AUTO_SELL("auto-sell", XMaterial.ENDER_CHEST,'5', "&7Items will be automatically sold as they", "&7are collected by the hopper.", "&7The owner will get the money.");

    @Getter
    private String configId;
    @Getter
    private XMaterial item;
    @Getter
    private List<String> info;
    @Getter
    private Character color;


    HopperFeature(String id, XMaterial mat, Character color, String... lore) {
        configId = id;
        this.item = mat;
        this.color = color;
        info = new ArrayList<>();
        info.addAll(Arrays.asList(lore));
    }

}
