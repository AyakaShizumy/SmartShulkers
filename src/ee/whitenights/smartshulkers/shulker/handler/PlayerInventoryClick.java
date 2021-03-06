package ee.whitenights.smartshulkers.shulker.handler;

import ee.whitenights.smartshulkers.shulker.util.GUI;
import ee.whitenights.smartshulkers.shulker.util.NBTManager;
import ee.whitenights.smartshulkers.shulker.Shulker;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerInventoryClick implements Listener {

    @EventHandler
    public void onPlayerInventoryClick(InventoryClickEvent event){
        Inventory inventory = event.getClickedInventory();
        if(event.getInventory().getName().equals(Shulker.INVENTORY_NAME.replace("%player%", event.getWhoClicked().getName()))){
            if(Shulker.isShulker(event.getCurrentItem())) event.setCancelled(true);
        }
        if(event.getWhoClicked().getOpenInventory().getTopInventory().getName().equals(Shulker.SETTINGS_NAME) && !inventory.getName().equals(Shulker.SETTINGS_NAME)){
            if(Shulker.isShulker(event.getCurrentItem())) event.setCancelled(true);
            if(event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) event.setCancelled(true);
        }
        if(!inventory.getName().equals(Shulker.SETTINGS_NAME)) return;
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        int slot = Shulker.getShulker(inventory, player.getInventory());
        ItemStack shulker = player.getInventory().getItem(slot);
        if(item.equals(GUI.ENABLE)){
            NBTManager.setNBT(shulker, Shulker.TAG_ENABLE, "false");
            GUI.openGUI(player, shulker);
            return;
        }
        if(item.equals(GUI.DISABLE)){
            NBTManager.setNBT(shulker, Shulker.TAG_ENABLE, "true");
            GUI.openGUI(player, shulker);
            return;
        }
        if(item.equals(GUI.WHITELIST)){
            NBTManager.setNBT(shulker, Shulker.TAG_MODE, "blacklist");
            GUI.openGUI(player, shulker);
            return;
        }
        if(item.equals(GUI.BLACKLIST)){
            NBTManager.setNBT(shulker, Shulker.TAG_MODE, "whitelist");
            GUI.openGUI(player, shulker);
            return;
        }
        if(item.equals(GUI.BACKGROUND)){
            return;
        }
        if(Shulker.isShulker(item)){
            return;
        }
        if(item.getType().equals(Material.AIR)){
            if(event.getCursor().getType() == Material.AIR) return;
            if(Shulker.isShulker(event.getCursor())) return;
            Shulker.addItem(shulker, event.getCursor());
            player.getInventory().addItem(event.getCursor());
            event.setCursor(new ItemStack(Material.AIR));
            GUI.openGUI(player, shulker);
            return;
        }
        Shulker.removeItem(shulker, item);
        player.getInventory().addItem(event.getCursor());
        event.setCursor(new ItemStack(Material.AIR));
        GUI.openGUI(player, shulker);
    }
}
