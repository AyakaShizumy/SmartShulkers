package ee.whitenights.smartshulkers.handler;

import ee.whitenights.smartshulkers.shulker.GUI;
import ee.whitenights.smartshulkers.shulker.NBTManager;
import ee.whitenights.smartshulkers.shulker.Shulker;
import org.bukkit.Bukkit;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class PlayerInteractHandler implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(!Shulker.isShulker(item)) return;
        if(!NBTManager.checkNBT(item)) return;
        if(event.getAction().toString().startsWith("LEFT_CLICK")){
            event.setCancelled(true);
            GUI.openGUI(player, item);
        }else if(event.getAction().toString().startsWith("RIGHT_CLICK")){
            event.setCancelled(true);
            BlockStateMeta blockMeta = (BlockStateMeta) item.getItemMeta();
            ShulkerBox shulkerBox = (ShulkerBox) blockMeta.getBlockState();
            Inventory inv = Bukkit.createInventory(null, 27, player.getDisplayName() + "`s Box");
            inv.setContents(shulkerBox.getInventory().getContents());
            player.openInventory(inv);
        }
    }
}
