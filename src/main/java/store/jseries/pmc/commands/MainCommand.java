package store.jseries.pmc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import store.jseries.pmc.utils.Message;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("pmc.admin")) {
            sender.sendMessage(Message.NO_PERMISSION.getMessage());
            return true;
        }
        if(args.length < 1) {
            BaseCommand.execute(sender,args);
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "enable":
                EnableCommand.execute(sender,args);
                break;
            case "disable":
                DisableCommand.execute(sender,args);
                break;
            case "setitem":
                SetItemCommand.execute(sender,args);
                break;
            case "setsound":
                SetSoundCommand.execute(sender,args);
                break;
            case "support":
                SupportCommand.execute(sender,args);
                break;
            case "reload":
                ReloadCommand.execute(sender,args);
                break;
            default:
                HelpCommand.execute(sender,args);
                break;
        }
        return true;
    }

}
