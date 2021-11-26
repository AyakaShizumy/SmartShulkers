package ee.whitenights.smartshulkers.shulker.handler;

import ee.whitenights.smartshulkers.shulker.Shulker;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class PlayerPickupItem implements Listener {

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event){
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if(!player.hasPermission("smartshulker.shulker.use")) return;
        ItemStack[] list = player.getInventory().getStorageContents();
        ItemStack currentItem = event.getItem().getItemStack();
        for (ItemStack itemStack : list) {
            if(!Shulker.isShulker(itemStack)) continue;
            if(Shulker.isShulker(currentItem)) continue;
            if(!Shulker.canPickup(itemStack, currentItem)) continue;
            BlockStateMeta blockMeta = (BlockStateMeta) itemStack.getItemMeta();
            ShulkerBox shulkerBox = (ShulkerBox) blockMeta.getBlockState();
            Inventory inventory = shulkerBox.getInventory();
            if(!Shulker.isShulkerFull(inventory, currentItem)) continue;
            inventory.addItem(currentItem);
            blockMeta.setBlockState(shulkerBox);
            itemStack.setItemMeta(blockMeta);
            event.setCancelled(true);
            event.getItem().remove();
            break;
        }
    }
}
