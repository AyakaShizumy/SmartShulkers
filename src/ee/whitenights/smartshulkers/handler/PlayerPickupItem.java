package ee.whitenights.smartshulkers.handler;

import ee.whitenights.smartshulkers.shulker.Shulker;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.lang.reflect.Field;

public class PlayerPickupItem implements Listener {

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event){
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
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
