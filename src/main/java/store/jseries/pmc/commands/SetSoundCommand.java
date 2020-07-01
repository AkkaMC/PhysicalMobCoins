package store.jseries.pmc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import store.jseries.pmc.PhysicalMobCoins;
import store.jseries.pmc.utils.PickupSound;

import java.util.List;

public class SetSoundCommand {

    public static void execute(CommandSender sender, String[] args) {
        if(args.length < 1) {
            sender.sendMessage(ChatColor.RED + "/pmc setsound (sound_name)");
            return;
        }
        try {
            PickupSound sound = PickupSound.valueOf(args[1].toUpperCase().replaceAll("-","_"));
            PhysicalMobCoins.getInstance().getSoundManager().setSound(sound);
            sender.sendMessage(ChatColor.GREEN + "Successfully set the sound!");
        } catch (Exception ex) {
            StringBuilder sounds = new StringBuilder();
            for (PickupSound value : PickupSound.values())
                sounds.append(value.name());
            sender.sendMessage(ChatColor.RED + "Unknown sound. Must be " + sounds.toString());
        }
    }
}
