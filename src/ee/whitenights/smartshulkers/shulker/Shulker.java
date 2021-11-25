package ee.whitenights.smartshulkers.shulker;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;


public class Shulker {

    public static String TAG = "smart";
    public static String TAG_ITEM = "item";
    public static String TAG_MODE = "mode";
    public static String TAG_ENABLE = "enable";
    public static String INVENTORY_NAME = "Shulker Settings";

    public static ItemStack createShulker(){
        ItemStack shulker = new ItemStack(Material.PINK_SHULKER_BOX);
        shulker = NBTManager.setNBTCopy(shulker, TAG, "1");
        NBTManager.setNBTCopy(shulker, TAG_ENABLE, "true");
        NBTManager.setNBTCopy(shulker, TAG_ITEM, "&");
        NBTManager.setNBTCopy(shulker, TAG_MODE, "whitelist");
        shulker.getItemMeta().addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        return shulker;
    }

    public static boolean isShulker(ItemStack item){
        if(item == null) return false;
        return item.getType().toString().endsWith("SHULKER_BOX");
    }

    public static boolean isShulkerFull(Inventory inventory, ItemStack item){
        for (ItemStack content : inventory.getContents()) {
            if(content == null) return true;
            if(content.getType().equals(item.getType())){
                if(content.getAmount() < 64 && content.getAmount() + item.getAmount() <= 64){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean canPickup(ItemStack shulker, ItemStack item){
        if(NBTManager.getNBT(shulker, TAG_ITEM) == null) return false;
        if(NBTManager.getNBT(shulker, TAG_ENABLE).equals("false")) return false;
        List<ItemStack> list = getItemList(shulker);
        if(list.size() < 1) return false;
        for (ItemStack itemStack : list) {
            if(itemStack.getType().equals(item.getType()) && itemStack.getDurability() == item.getDurability()){
                return NBTManager.getNBT(shulker, TAG_MODE).equals("whitelist");
            }
        }
        return !NBTManager.getNBT(shulker, TAG_MODE).equals("whitelist");
    }

    public static List<ItemStack> getItemList(ItemStack shulker){
        String[] temp = NBTManager.getNBT(shulker, TAG_ITEM).split("&");
        List<ItemStack> list = new ArrayList<>();
        for (String s : temp) {
            if(s.equals("")) continue;
            ItemStack item = new ItemStack(Material.valueOf(s.split(":")[0]));
            item.setDurability(Short.parseShort(s.split(":")[1]));
            list.add(item);
        }
        return list;
    }

    public static int getShulker(Inventory menu, Inventory inventory){
        int slot = -1;
        for (ItemStack content : menu.getContents()) {
            if(content == null) continue;
            slot = -1;
            if(isShulker(content)){
                for (ItemStack inventoryContent : inventory.getContents()) {
                    slot++;
                    if(inventoryContent == null) continue;
                    if(inventoryContent.equals(content)){
                        return slot;
                    }
                }
            }
        }
        return slot;
    }

    public static void addItem(ItemStack shulker, ItemStack item){
        String tag = NBTManager.getNBT(shulker, TAG_ITEM);
        String name = item.getType().toString() + ":" + item.getDurability() + "&";
        if(tag.contains(name)) return;
        tag += name;
        NBTManager.setNBT(shulker, TAG_ITEM, tag);
    }

    public static void removeItem(ItemStack shulker, ItemStack item){
        String tag = NBTManager.getNBT(shulker, TAG_ITEM);
        tag = tag.replace(item.getType().toString() + ":" + item.getDurability() + "&", "");
        NBTManager.setNBT(shulker, TAG_ITEM, tag);
    }
}
