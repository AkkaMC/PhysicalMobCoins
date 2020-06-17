package store.jseries.jhoppers.utils.enums;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import store.jseries.jhoppers.JHoppers;

import java.io.File;

public enum Message {

    PREFIX("prefix"),
    SUFFIX("suffix"),
    NO_PERMISSION("no-permission"),
    OPEN_HOPPER("open-hopper"),
    CLOSE_HOPPER("close-hopper"),
    INTERACT_HOPPER_OTHER("interact-hopper-other"),
    NO_HOPPER_PERMISSION("no-hopper-permission"),
    BREAK_SUCCESSFUL("break-successful"),
    ALREADY_IN_CHUNK("already-in-chunk"),
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
        if(this == PREFIX || this == SUFFIX)
            return file.getString(configId);
        return PREFIX.getMessage() + file.getString(configId) + SUFFIX.getMessage();
    }

}
