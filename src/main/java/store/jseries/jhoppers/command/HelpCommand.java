package store.jseries.jhoppers.command;

import org.bukkit.command.CommandSender;
import store.jseries.framework.utils.JUtils;
import store.jseries.jhoppers.JHoppers;
import store.jseries.jhoppers.utils.enums.Message;
import store.jseries.jhoppers.utils.enums.Permission;

public class HelpCommand extends JUtils {

    public static void handle(CommandSender sender, String[] args) {
        for(String s : Message.HELP_MESSAGE.getMessage(true))
            sender.sendMessage(s);
    }
}
