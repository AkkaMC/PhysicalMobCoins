package store.jseries.jhoppers.utils.enums;

import lombok.Getter;

public enum Permission {

    USE("use-hopper", "jhoppers.use"),
    ADD_MEMBERS("addmembers", "jhoppers.addmembers"),
    ADMIN_PERMISSION("admin-permission", "jhoppers.admin");

    @Getter
    private String configId;
    @Getter
    private String defaultValue;

    Permission(String id, String defaultPermission ) {
        configId = id;
        defaultValue = defaultPermission;
    }

}
