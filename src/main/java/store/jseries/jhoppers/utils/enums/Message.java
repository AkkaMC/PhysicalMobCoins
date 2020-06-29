package store.jseries.jhoppers.utils.enums;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import store.jseries.jhoppers.JHoppers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Message {

    PREFIX("prefix"),
    SUFFIX("suffix"),
    NO_PERMISSION("no-permission"),
    OPEN_HOPPER("open-hopper"),
    CLOSE_HOPPER("close-hopper"),
    INTERACT_HOPPER_OTHER("interact-hopper-other"),
    NO_HOPPER_PERMISSION("no-hopper-permission"),
    BREAK_SUCCESSFUL("break-successful"),
    NO_ITEMS_LEFT("no-items-left"),
    AUTO_SELL_ENABLED("auto-sell-enabled"),
    ENTER_MEMBER("enter-member"),
    UNKNOWN_PLAYER("unknown-player"),
    ALREADY_MEMBER("already-member"),
    ADDED_MEMBER("added-member"),
    SOLD_ITEMS("sold-items"),
    WITHDRAW_ITEMS("withdraw-items"),
    FULL_INVENTORY("full-inventory"),
    ALREADY_IN_CHUNK("already-in-chunk"),
    DELETED_TYPE("deleted-type"),
    HELP_MESSAGE("help-message", true);

    private String configId;
    private boolean multiline;
    private FileConfiguration file;
    @Getter
    @Setter
    private static FileConfiguration config;

    Message(String id) {
        if(Message.getConfig()==null)
            Message.setConfig(YamlConfiguration.loadConfiguration(new File(JHoppers.getInstance().getDataFolder(),"messages.yml")));
        file = Message.getConfig();
        this.configId = id;
        this.multiline = false;
    }

    Message(String id, boolean multi) {
        if(Message.getConfig()==null)
            Message.setConfig(YamlConfiguration.loadConfiguration(new File(JHoppers.getInstance().getDataFolder(),"messages.yml")));
        file = Message.getConfig();
        this.configId = id;
        this.multiline = multi;
    }

    public String getMessage() {
        return getMessage(false).get(0);
    }

    public List<String> getMessage(boolean multiline) {
        if (!multiline) {
            if (this == PREFIX || this == SUFFIX)
                return Collections.singletonList(ChatColor.translateAlternateColorCodes('&', file.getString(configId)));
            return Collections.singletonList(ChatColor.translateAlternateColorCodes('&', PREFIX.getMessage() + file.getString(configId) + SUFFIX.getMessage()));
        } else {
            List<String> message = new ArrayList<>();
            for(String s : file.getStringList(configId))
                message.add(ChatColor.translateAlternateColorCodes('&',s));
            return message;
        }
    }



}
