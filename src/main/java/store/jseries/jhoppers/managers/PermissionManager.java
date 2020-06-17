package store.jseries.jhoppers.managers;

import org.bukkit.configuration.file.FileConfiguration;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.utils.enums.Permission;
import store.jseries.jhoppers.utils.hopper.JHopper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PermissionManager {

    private Map<Permission, String> permissions;

    public PermissionManager() {
        permissions = new HashMap<>();
        FileConfiguration config = JHoppers.getInstance().getConfig();
        for(Permission permission : Permission.values())
            permissions.put(permission, config.contains("permissions." + permission.getConfigId()) ? config.getString("permissions." + permission.getConfigId()) : permission.getDefaultValue());
    }

    public String getPermission(Permission permission) {
        return permissions.get(permission);
    }

    public void setPermission(Permission permission, String value) {
        permissions.remove(permission);
        permissions.put(permission, value);
        FileConfiguration config = JHoppers.getInstance().getConfig();
        config.set("permissions." + permission.getConfigId(), value);
        try {
            config.save(new File(JHoppers.getInstance().getDataFolder(), "config.yml"));
        } catch (Exception ex) {
            Logger.getLogger("minecraft").info("JHOPPERS > ERROR SAVING CONFIG.YML");
            ex.printStackTrace();
        }
    }

}
