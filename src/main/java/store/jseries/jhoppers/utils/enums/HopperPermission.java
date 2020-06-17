package store.jseries.jhoppers.utils.enums;

import lombok.Getter;
import store.jseries.jhoppers.utils.hopper.HopperType;

public enum HopperPermission {

    OWNER("owner"),
    BREAK("break"),
    SELL("sell"),
    WITHDRAW("withdraw");

    @Getter
    private String name;

    HopperPermission(String id) {
        this.name = id;
    }

}
