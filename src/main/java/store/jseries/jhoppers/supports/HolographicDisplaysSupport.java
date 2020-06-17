package store.jseries.jhoppers.supports;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.utils.hopper.JHopper;

import java.util.HashMap;
import java.util.Map;

public class HolographicDisplaysSupport {

    @Getter
    @Setter
    private static boolean enabled = false;
    private static Map<String, com.gmail.filoghost.holographicdisplays.api.Hologram> holograms = new HashMap<>();

    public static void createHologram(JHopper hopper) {
        if(HolographicDisplaysSupport.isEnabled() && !holograms.containsKey(getId(hopper))) {
            com.gmail.filoghost.holographicdisplays.api.Hologram hologram = com.gmail.filoghost.holographicdisplays.api.HologramsAPI.createHologram(JHoppers.getInstance(), hopper.getLocation().add(0,0.3,0));
            hologram.appendTextLine(ChatColor.translateAlternateColorCodes('&', JHoppers.getInstance().getHopperTypeManager().getType(hopper.getHopperType()).getHologram()));
            holograms.put(getId(hopper),hologram);
        }
    }

    public static void updateHologram(JHopper hopper) {
        if(HolographicDisplaysSupport.isEnabled()) {
            if (holograms.containsKey(getId(hopper))) {
                com.gmail.filoghost.holographicdisplays.api.Hologram hologram = holograms.get(getId(hopper));
                if (hologram == null) {
                    createHologram(hopper);
                } else {
                    hologram.clearLines();
                    hologram.appendTextLine(ChatColor.translateAlternateColorCodes('&', JHoppers.getInstance().getHopperTypeManager().getType(hopper.getHopperType()).getHologram()));
                }
            }
        }
    }

    public static void removeHologram(JHopper hopper) {
        if(HolographicDisplaysSupport.isEnabled()) {
            if (holograms.containsKey(getId(hopper))) {
                holograms.get(getId(hopper)).delete();
                holograms.remove(getId(hopper));
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
