package store.jseries.jhoppers.supports;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.utils.hopper.HopperType;
import store.jseries.jhoppers.utils.hopper.JHopper;

import java.util.HashMap;
import java.util.Map;

public class HolographicDisplaysSupport {

    @Getter
    private static boolean enabled = false;
    private static Map<String, com.gmail.filoghost.holographicdisplays.api.Hologram> holograms = new HashMap<>();
    private static double hologram_height = 1.3;


    public static void setEnabled(FileConfiguration config) {
        if(config.contains("hologram-height"))
            hologram_height = config.getDouble("hologram-height");
        enabled = true;
    }

    public static void createHologram(JHopper hopper) {
        if(HolographicDisplaysSupport.isEnabled()) {
            if(holograms.containsKey(getId(hopper)))
                holograms.get(getId(hopper)).delete();
            holograms.remove(getId(hopper));
            com.gmail.filoghost.holographicdisplays.api.Hologram hologram = com.gmail.filoghost.holographicdisplays.api.HologramsAPI.createHologram(JHoppers.getInstance(), new Location(hopper.getLocation().getWorld(), hopper.getLocation().getBlockX()+0.5, hopper.getLocation().getBlockY()+hologram_height,hopper.getLocation().getBlockZ()+0.5));
            hologram.appendTextLine(ChatColor.translateAlternateColorCodes('&', JHoppers.getInstance().getHopperTypeManager().getType(hopper.getHopperType()).getHologram()));
            holograms.put(getId(hopper),hologram);
        }
    }

    public static void updateHologram(JHopper hopper) {
        if(HolographicDisplaysSupport.isEnabled()) {
            HopperType type = JHoppers.getInstance().getHopperTypeManager().getType(hopper.getHopperType());
            if(!type.getHologram().equalsIgnoreCase(""))
                createHologram(hopper);
            else
                removeHologram(hopper);
        }
    }

    public static void removeHologram(JHopper hopper) {
        if(HolographicDisplaysSupport.isEnabled()) {
            if (holograms.containsKey(getId(hopper))) {
                holograms.get(getId(hopper)).delete();
                holograms.remove(getId(hopper));
                if (holograms.containsKey(getId(hopper))) {
                    holograms.get(getId(hopper)).delete();
                    holograms.remove(getId(hopper));
                }
            }
        }
    }

    private static String getId(Location loc) {
        return loc.getWorld() + "/" + loc.getChunk().getX() + "/" + loc.getChunk().getZ();
    }

    private static String getId(JHopper hopper) {
        return getId(hopper.getLocation());
    }

}
