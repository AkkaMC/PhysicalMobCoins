package store.jseries.jhoppers.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import store.jseries.jhoppers.inventories.MainInventory;
import store.jseries.jhoppers.utils.enums.Message;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
           if(args.length < 1)
               BaseCommand.handle(sender,args);
           else {
               if (args[0].equalsIgnoreCase("give"))
                   GiveCommand.handle(sender, args);
               else if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("new"))
                   CreateCommand.handle(sender, args);
               else if (args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("del"))
                   DeleteCommand.handle(sender, args);
               else
                   HelpCommand.handle(sender, args);
           }
        return true;
    }
}
