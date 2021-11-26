package ee.whitenights.smartshulkers.handler;

import ee.whitenights.smartshulkers.shulker.Shulker;
import org.bukkit.block.ShulkerBox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class PlayerInventoryClose implements Listener {

    @EventHandler
    public void onPlayerInventoryClose(InventoryCloseEvent event){
        if(!event.getInventory().getName().equals(Shulker.INVENTORY_NAME.replace("%player%", event.getPlayer().getName()))) return;
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        BlockStateMeta blockMeta = (BlockStateMeta) item.getItemMeta();
        ShulkerBox shulkerBox = (ShulkerBox) blockMeta.getBlockState();
        shulkerBox.getInventory().setContents(event.getInventory().getContents());
        blockMeta.setBlockState(shulkerBox);
        event.getPlayer().getInventory().getItemInMainHand().setItemMeta(blockMeta);
    }
}
