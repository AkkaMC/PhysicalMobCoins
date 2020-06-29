package store.jseries.jhoppers.managers;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import store.jseries.framework.utils.JUtils;
import store.jseries.framework.xseries.XMaterial;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.supports.HolographicDisplaysSupport;
import store.jseries.jhoppers.utils.hopper.HopperType;
import store.jseries.jhoppers.utils.hopper.JHopper;
import store.jseries.jhoppers.utils.players.Member;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public class JHopperManager extends JUtils {

    @Getter
    private Map<String, JHopper> hopperChunks;
    private boolean saving;

    public JHopperManager() {
        hopperChunks = new HashMap<>();
        saving = false;
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(JHoppers.getInstance().getDataFolder(), "storage.yml"));
        if(config.contains("hoppers")) {
            for(String key : config.getConfigurationSection("hoppers").getKeys(false)) {
                Location loc = changeStringLocationToLocation(Objects.requireNonNull(config.getString("hoppers." + key + ".location")));
                String type = config.contains("hoppers." + key + ".type") ? config.getString("hoppers." + key + ".type") : JHoppers.getInstance().getHopperTypeManager().getTypes().get(0).getId();
                Map<XMaterial, Long> items = new HashMap<>();
                if (config.contains("hoppers." + key + ".items")) {
                    for (String mat : config.getConfigurationSection("hoppers." + key + ".items").getKeys(false)) {
                        try {
                            items.put(XMaterial.matchXMaterial(mat.toUpperCase()).get(), config.getLong("hoppers." + key + ".items." + mat));
                        } catch (Exception exception) {
                            Logger.getLogger("minecraft").info("JHOPPERS >> " + mat + " IS NOT A VALID MATERIAL NAME.");
                        }
                    }
                }
                Map<UUID, Member> members = new HashMap<>();
                for (String uuid : config.getConfigurationSection("hoppers." + key + ".members").getKeys(false))
                    members.put(UUID.fromString(uuid), new Member().deserializeToMember(config.getString("hoppers." + key + ".members." + uuid)));
                JHopper hopper = new JHopper();
                hopper.setLocation(loc);
                hopper.setHopperType(type);
                hopper.setItems(items);
                hopper.setMembers(members);
                hopperChunks.put(getId(loc.getChunk()), hopper);
            }
        }
        FileConfiguration configuration = JHoppers.getInstance().getConfig();
        int autosave = configuration.contains("auto-save-time") ? configuration.getInt("auto-save-time") : 300;
        if(autosave>0) {
            Bukkit.getScheduler().runTaskTimer(JHoppers.getInstance(), new Runnable() {
                @Override
                public void run() {
                    saveHoppers();
                    // Logger.getLogger("minecraft").info("JHOPPERS >> AUTOSAVING");
                }
            }, autosave*20, autosave*20);
        }
    }

    public void disable() {
        saveHoppers();
    }

    public JHopper getJHopper(Chunk chunk) {
        if(!hopperChunks.containsKey(getId(chunk)))
            return null;
        return hopperChunks.get(getId(chunk));

    }

    public JHopper getJHopper(Location loc) {
        if(!hopperChunks.containsKey(getId(loc.getChunk())))
            return null;

        JHopper hopper = hopperChunks.get(getId(loc.getChunk()));
        if(hopper.getLocation().equals(loc))
            return hopper;

        return null;
    }

    public Map<Location, XMaterial> getChangedHopperBlocks() {
        return null;
    }

    public void changeBlocks(Map<Location, XMaterial> blocks) {

    }

    public void removeHopper(JHopper hopper) {
        HolographicDisplaysSupport.removeHologram(hopper);
        hopperChunks.remove(getId(hopper.getLocation().getChunk()));
        hopper.getLocation().getBlock().setType(Material.AIR);
    }

    public void placeHolograms() {
        if (HolographicDisplaysSupport.isEnabled()) {
            for (JHopper hopper : hopperChunks.values())
                HolographicDisplaysSupport.createHologram(hopper);
        }
    }

    public void saveHoppers() {
        if (!saving) {
            saving = true;
            File file = new File(JHoppers.getInstance().getDataFolder(), "storage.yml");
            FileConfiguration storage = YamlConfiguration.loadConfiguration(file);
            storage.set("hoppers", null);
            if (hopperChunks.size() > 0) {
                for (JHopper hopper : hopperChunks.values()) {
                    String path = "hoppers." + getId(hopper);
                    storage.set(path + ".type", hopper.getHopperType());
                    if (hopper.getItems().size() > 0) {
                        for (Map.Entry<XMaterial, Long> entry : hopper.getItems().entrySet())
                            storage.set(path + ".items." + entry.getKey().name(), entry.getValue());
                    }
                    for (Map.Entry<UUID, Member> entry : hopper.getMembers().entrySet())
                        storage.set(path + ".members." + entry.getKey().toString(), entry.getValue().serialize());
                    storage.set(path + ".location", changeLocationToString(hopper.getLocation()));
                }
            }
            try {
                storage.save(file);
            } catch (Exception ex) {
                Logger.getLogger("minecraft").info("JHOPPERS > ERROR SAVING STORAGE.YML");
                ex.printStackTrace();
            } finally {
                saving = false;
            }
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
                hopperChunks.put(getId(loc.getChunk()),hopper);
            }
        });

    }


    private static String getId(Location loc) {
        return loc.getWorld().getName() + "/" + loc.getChunk().getX() + "/" + loc.getChunk().getZ();
    }
    private static String getId(Chunk loc) {
        return loc.getWorld().getName() + "/" + loc.getX() + "/" + loc.getZ();
    }

    private static String getId(JHopper hopper) {
        return getId(hopper.getLocation());
    }

}
