package ee.whitenights.smartshulkers.shulker.command;

import ee.whitenights.smartshulkers.Main;
import ee.whitenights.smartshulkers.shulker.util.Message;
import ee.whitenights.smartshulkers.shulker.Shulker;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        Player sender = null;
        if(!(commandSender instanceof ConsoleCommandSender)){
            sender = (Player) commandSender;
        }
        if(strings.length > 0){
            switch (strings[0]){
                case "give":
                    Player player = Bukkit.getPlayer(strings[1]);
                    if(commandSender instanceof ConsoleCommandSender){
                        if(player == null){
                            Main.logger.warning(Message.PLAYER_NOT_FOUND);
                            return true;
                        }
                        Main.logger.fine(Message.SHULKER_GIVE_OK);
                        player.getInventory().addItem(Shulker.createShulker());
                        return true;
                    }

                    if(!sender.hasPermission("smartshulker.shulker.give")){
                        sender.sendMessage(Message.NO_PERMISSIONS);
                    }else if(player == null){
                        sender.sendMessage(Message.PLAYER_NOT_FOUND);
                    }else{
                        player.getInventory().addItem(Shulker.createShulker());
                        sender.sendMessage(Message.SHULKER_GIVE_OK);
                    }
                    return true;
                case "convert":
                    if(commandSender instanceof ConsoleCommandSender){
                        Main.logger.warning(Message.CMD_PLAYER_ONLY);
                        return true;
                    }

                    if(!sender.hasPermission("smartshulker.shulker.convert")) {
                        sender.sendMessage(Message.NO_PERMISSIONS);
                    }else if(!Shulker.isShulker(sender.getInventory().getItemInMainHand())){
                        sender.sendMessage(Message.SHULKER_CONVERT_ERROR);
                    }else{
                        sender.getInventory().setItemInMainHand(Shulker.createShulker());
                        sender.sendMessage(Message.SHULKER_CONVERT_OK);
                    }
                    return true;
            }
        }else{
            /*HELP*/
        }
        return true;
    }
}
