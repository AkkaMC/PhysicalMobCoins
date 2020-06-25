package store.jseries.jhoppers.utils.enums;

import lombok.Getter;
import store.jseries.framework.xseries.XMaterial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum HopperFeature {

    AUTO_KILL("auto-kill", XMaterial.SPAWNER, '6', false, "&7Mobs will be automatically killed as", "&7they are spawned and their drops", "&7will be put inside the hopper.", "&c&lNOTE:&c This is coming soon."),
    AUTO_HARVEST("auto-harvest", XMaterial.GOLDEN_HOE, 'e', true, "&7Crops in the chunk will be", "&7automatically harvested and the", "&7hopper will receive the drops."),
    AUTO_SELL("auto-sell", XMaterial.ENDER_CHEST, '5', true, "&7Items will be automatically sold as they", "&7are collected by the hopper.", "&7The owner will get the money.");

    @Getter
    private String configId;
    @Getter
    private XMaterial item;
    @Getter
    private List<String> info;
    @Getter
    private Character color;
    @Getter
    private final boolean active;


    HopperFeature(String id, XMaterial mat, Character color, boolean active, String... lore) {
        configId = id;
        this.item = mat;
        this.color = color;
        info = new ArrayList<>();
        this.active = active;
        info.addAll(Arrays.asList(lore));
    }

    public static HopperFeature fromString(String s) {
        for(HopperFeature feature : HopperFeature.values()) {
            if(feature.getConfigId().equalsIgnoreCase(s))
                return feature;
        }
        return null;
    }

}
