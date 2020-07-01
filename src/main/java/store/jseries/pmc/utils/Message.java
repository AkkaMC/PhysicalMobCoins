package store.jseries.pmc.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import store.jseries.pmc.PhysicalMobCoins;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public enum Message {

    PREFIX("prefix"),
    SUFFIX("suffix"),
    NO_PERMISSION("no-permission"),
    PICKUP_COINS("pickup-coins"),
    HELP_MESSAGE("help-message", true);

    private String configId;
    private boolean multiline;
    @Getter
    @Setter
    private static FileConfiguration config;

    Message(String id) {
        if(Message.getConfig()==null)
            Message.setConfig(YamlConfiguration.loadConfiguration(new File(PhysicalMobCoins.getInstance().getDataFolder(),"messages.yml")));
        this.configId = id;
        this.multiline = false;
    }

    Message(String id, boolean multi) {
        if(Message.getConfig()==null)
            Message.setConfig(YamlConfiguration.loadConfiguration(new File(PhysicalMobCoins.getInstance().getDataFolder(),"messages.yml")));
        this.configId = id;
        this.multiline = multi;
    }

    public String getMessage() {
        return getMessage(false).get(0);
    }

    public List<String> getMessage(boolean multiline) {
        if (!multiline) {
            if (this == PREFIX || this == SUFFIX)
                return Collections.singletonList(ChatColor.translateAlternateColorCodes('&', Message.getConfig().getString(configId)));
            return Collections.singletonList(ChatColor.translateAlternateColorCodes('&', PREFIX.getMessage() + Message.getConfig().getString(configId) + SUFFIX.getMessage()));
        } else {
            List<String> message = new ArrayList<>();
            for(String s : Message.getConfig().getStringList(configId))
                message.add(ChatColor.translateAlternateColorCodes('&',s));
            return message;
        }
    }



}
