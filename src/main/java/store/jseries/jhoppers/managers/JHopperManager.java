package store.jseries.jhoppers.managers;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import store.jseries.framework.utils.JUtils;
import store.jseries.framework.xseries.XBlock;
import store.jseries.framework.xseries.XMaterial;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.supports.HolographicDisplaysSupport;
import store.jseries.jhoppers.utils.hopper.HopperType;
import store.jseries.jhoppers.utils.hopper.JHopper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class JHopperManager extends JUtils {

    @Getter
    private Map<Chunk, JHopper> hopperChunks;

    public JHopperManager() {
        hopperChunks = new HashMap<>();
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(JHoppers.getInstance().getDataFolder(), "storage.yml"));
        if(config.contains("hoppers")) {
            for(String key : config.getConfigurationSection("hoppers").getKeys(false)) {
                Chunk chunk = changeStringToChunk(key);
                Location loc = changeStringLocationToLocation(Objects.requireNonNull(config.getString("hoppers." + key + ".location")));
                String type = config.contains("hoppers." + key + ".type") ? config.getString("hoppers." + key + ".type") : JHoppers.getInstance().getHopperTypeManager().getTypes().get(0).getId();
                Map<XMaterial, Long> items = new HashMap<>();
                if(config.contains("hoppers." + key + ".items")) {
                    for(String mat : config.getConfigurationSection("hoppers." + key + ".items").getKeys(false)) {
                        try {
                            items.put(XMaterial.matchXMaterial(mat.toUpperCase()).get(),config.getLong("hoppers." + key + ".items." + mat));
                        } catch (Exception ignored) {}
                    }
                }

            }
        }
        FileConfiguration configuration = JHoppers.getInstance().getConfig();
        int autosave = configuration.contains("auto-save-time") ? configuration.getInt("auto-save-time") : 300;
        if(autosave>0) {
            Bukkit.getScheduler().runTaskTimer(JHoppers.getInstance(), new Runnable() {
                @Override
                public void run() {
                    saveHoppers();
                }
            }, autosave*20, autosave*20);
        }
    }

    public void disable() {
        saveHoppers();
    }

    public JHopper getJHopper(Chunk chunk) {
        if(!hopperChunks.containsKey(chunk))
            return null;
        return hopperChunks.get(chunk);
    }

    public JHopper getJHopper(Location loc) {
        if(!hopperChunks.containsKey(loc.getChunk()))
            return null;
        JHopper hopper = hopperChunks.get(loc.getChunk());
        if(hopper.getLocation().equals(loc))
            return hopper;
        return null;
    }

    public Map<Location, XMaterial> getChangedHopperBlocks() {
        return null;
    }

    public void changeBlocks(Map<Location, XMaterial> blocks) {

    }

    public void saveHoppers() {
        File file = new File(JHoppers.getInstance().getDataFolder(), "storage.yml");
        FileConfiguration storage = YamlConfiguration.loadConfiguration(file);
        storage.set("hoppers",null);
        if(hopperChunks.size() > 0) {
            for(JHopper hopper : hopperChunks.values()) {
                String path = "hoppers." + getId(hopper);

            }
        } try {
            storage.save(file);
        } catch (Exception ex) {
            Logger.getLogger("minecraft").info("JHOPPERS > ERROR SAVING STORAGE.YML");
            ex.printStackTrace();
        }
    }

    public void placeAtLocation(JHopper hopper, Player player) {
        Bukkit.getScheduler().runTask(JHoppers.getInstance(), new Runnable() {
            @Override
            public void run() {
                Location loc = hopper.getLocation();
                HopperType type = JHoppers.getInstance().getHopperTypeManager().getType(hopper.getHopperType());
                loc.getBlock().setType(Objects.requireNonNull(type.getBlockType().parseMaterial()));
                type.getPlaceParticle().display(loc);
                HolographicDisplaysSupport.createHologram(hopper);
                hopperChunks.put(loc.getChunk(),hopper);
            }
        });

    }


    private static String getId(Location loc) {
        return loc.getWorld() + "/" + loc.getChunk().getX() + "/" + loc.getChunk().getZ();
    }

    private static String getId(JHopper hopper) {
        return getId(hopper.getLocation());
    }

}
