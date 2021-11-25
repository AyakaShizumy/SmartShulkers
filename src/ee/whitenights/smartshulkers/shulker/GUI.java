package ee.whitenights.smartshulkers.shulker;

import ee.whitenights.smartshulkers.Main;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GUI {

    public static void openGUI(Player player, ItemStack shulker){
        FileConfiguration config = Main.config;
        List<String> matrix = config.getStringList("matrix");
        if(!matrixVerify(matrix)){
            Main.logger.warning("Error with matrix!");
            return;
        }
        Inventory inventory = Bukkit.createInventory(player, matrix.size() * 9, Shulker.INVENTORY_NAME);
        int slot = 0;
        List<ItemStack> items = Shulker.getItemList(shulker);
        for (String s : matrix) {
            for (char c : s.toCharArray()) {
                String l = String.valueOf(c);
                ItemStack item = backgroundPanel();
                switch (l){
                    case "i":
                        if(items.size() <1){
                            item = new ItemStack(Material.AIR);
                        }else{
                            item = items.get(0);
                            items.remove(0);
                        }
                        break;
                    case "s":
                        item = shulker;
                        break;
                    case "b":
                        if(NBTManager.getNBT(shulker, Shulker.TAG_ENABLE).equals("true")){
                            item = enableButton();
                        }else{
                            item = disableButton();
                        }
                        break;
                    case "l":
                        if(NBTManager.getNBT(shulker, Shulker.TAG_MODE).equals("whitelist")){
                            item = whitelistButton();
                        }else{
                            item = blacklistButton();
                        }
                        break;
                }
                inventory.setItem(slot, item);
                slot++;
            }
        }
        player.openInventory(inventory);
    }

    public static boolean matrixVerify(List<String> matrix){
        int items = 0;
        int parts = 0;
        boolean shulker = false;
        boolean button = false;
        boolean list = false;
        for (String s : matrix) {
            for (char c : s.toCharArray()) {
                String l = String.valueOf(c);
                switch (l){
                    case "i":
                        items++;
                        break;
                    case "s":
                        if(shulker) return false;
                        shulker = true;
                        break;
                    case "b":
                        if(button) return false;
                        button = true;
                        break;
                    case "l":
                        if(list) return false;
                        list = true;
                        break;
                }
                parts++;
            }
        }
        return shulker && button && list && items >= 1 && parts == matrix.size() * 9;
    }

    public static ItemStack enableButton(){
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
        item.setDurability((short) 5);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§aEnable");
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack disableButton(){
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
        item.setDurability((short) 14);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§cDisable");
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack whitelistButton(){
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
        item.setDurability((short) 8);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§aMod §a[§fWhitelist§a]");
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack blacklistButton(){
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
        item.setDurability((short) 15);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§aMod §a[§8Blacklist§a]");
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack backgroundPanel(){
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(" ");
        item.setItemMeta(itemMeta);
        return item;
    }

}
