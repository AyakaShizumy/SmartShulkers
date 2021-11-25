package ee.whitenights.smartshulkers.command;

import ee.whitenights.smartshulkers.shulker.Shulker;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        player.getInventory().addItem(Shulker.createShulker());
        return true;
    }
}
