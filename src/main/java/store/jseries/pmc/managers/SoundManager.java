package store.jseries.pmc.managers;

import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import store.jseries.pmc.PhysicalMobCoins;
import store.jseries.pmc.utils.PickupSound;

import java.util.logging.Logger;

public class SoundManager {

    @Getter
    private PickupSound sound;

    private float defaultVolume, defaultPitch;

    public SoundManager() {
        sound = PickupSound.NONE;
        defaultVolume = 1.0F;
        defaultPitch = 1.0F;
        FileConfiguration config = PhysicalMobCoins.getInstance().getConfig();
        if (config.contains("pickup-sound")) {
            try {
                sound = PickupSound.valueOf(config.getString("pickup-sound").toUpperCase());
            } catch (Exception exception) {
                Logger.getLogger("minecraft").info("PhysicalMobCoins > Invalid sound.");
                exception.printStackTrace();
            }
        }
        if (config.contains("pickup-sound-volume")) {
            try {
                defaultVolume = config.getInt("pickup-sound-volume");
            } catch (Exception exception) {
                Logger.getLogger("minecraft").info("PhysicalMobCoins > Invalid pickup sound volume.");
            }
        }
        if (config.contains("pickup-sound-pitch")) {
            try {
                defaultPitch = config.getInt("pickup-sound-pitch");
            } catch (Exception exception) {
                Logger.getLogger("minecraft").info("PhysicalMobCoins > Invalid pickup sound pitch.");
            }
        }
    }

    public void playSound(Player player) {
        if(sound != PickupSound.NONE)
            player.playSound(player.getLocation(), sound.getSound().bukkitSound(), defaultVolume, defaultPitch);
    }

    public void playSound(Player player,PickupSound sound) {
        if(sound != PickupSound.NONE)
            player.playSound(player.getLocation(), sound.getSound().bukkitSound(), defaultVolume, defaultPitch);
    }

    public PickupSound soundFromSlot(int slot) {
        if(!InventoryManager.getListSlots().contains(slot))
            return null;
        int index = InventoryManager.getListSlots().indexOf(slot);
        if(index >= PickupSound.values().length)
            return null;
        return PickupSound.values()[index];
    }

    public void setSound(PickupSound sound) {
        this.sound = sound;
        FileConfiguration config = PhysicalMobCoins.getInstance().getConfig();
        config.set("pickup-sound", sound.toString());
        InventoryManager.setCurrentSound(sound);
        PhysicalMobCoins.getInstance().saveConfig();
    }

}
