package ee.whitenights.smartshulkers.shulker.util;

import ee.whitenights.smartshulkers.Main;
import ee.whitenights.smartshulkers.shulker.Shulker;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GUI {

    public static ItemStack ENABLE = getItem("enable");
    public static ItemStack DISABLE = getItem("disable");
    public static ItemStack WHITELIST = getItem("whitelist");
    public static ItemStack BLACKLIST = getItem("blacklist");
    public static ItemStack BACKGROUND = getItem("background");


    public static void openGUI(Player player, ItemStack shulker){
        FileConfiguration config = Main.config;
        if(!player.hasPermission("smartshulker.shulker.use")){
            player.sendMessage(Message.NO_PERMISSIONS);
            return;
        }
        List<String> matrix = config.getStringList("matrix");
        if(!matrixVerify(matrix)){
            player.sendMessage(Message.MATRIX_ERROR);
            return;
        }
        Inventory inventory = Bukkit.createInventory(player, matrix.size() * 9, Shulker.SETTINGS_NAME);
        int slot = 0;
        List<ItemStack> items = Shulker.getItemList(shulker);
        for (String s : matrix) {
            for (char c : s.toCharArray()) {
                String l = String.valueOf(c);
                ItemStack item = BACKGROUND;
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
                            item = ENABLE;
                        }else{
                            item = DISABLE;
                        }
                        break;
                    case "l":
                        if(NBTManager.getNBT(shulker, Shulker.TAG_MODE).equals("whitelist")){
                            item = WHITELIST;
                        }else{
                            item = BLACKLIST;
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

    public static ItemStack getItem(String patch){
        String material = Main.config.getString(Shulker.MENU_PATCH + "." + patch + ".type");
        int durability = Main.config.getInt(Shulker.MENU_PATCH + "." + patch + ".damage");
        String name = Main.config.getString(Shulker.MENU_PATCH + "." + patch + ".meta.display-name");
        ItemStack item = new ItemStack(Material.valueOf(material));
        item.setDurability((short) durability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

}
