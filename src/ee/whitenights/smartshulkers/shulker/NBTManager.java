package ee.whitenights.smartshulkers.shulker;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

public class NBTManager {

    public static ItemStack setNBTCopy(ItemStack item, String tag, String text){
        net.minecraft.server.v1_12_R1.ItemStack nmsCopy = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsCopy.getTag() == null) ? new NBTTagCompound() : nmsCopy.getTag();
        compound.setString(tag, text);
        nmsCopy.setTag(compound);
        return CraftItemStack.asBukkitCopy(nmsCopy);
    }

    public static void setNBT(ItemStack item, String tag, String text){
        net.minecraft.server.v1_12_R1.ItemStack nmsCopy = getItem(item);
        assert nmsCopy != null;
        NBTTagCompound compound = (nmsCopy.getTag() == null) ? new NBTTagCompound() : nmsCopy.getTag();
        compound.setString(tag, text);
        nmsCopy.setTag(compound);
    }

    public static void removeNBT(ItemStack item, String tag){
        net.minecraft.server.v1_12_R1.ItemStack nmsCopy = getItem(item);
        assert nmsCopy != null;
        NBTTagCompound compound = (nmsCopy.getTag() == null) ? new NBTTagCompound() : nmsCopy.getTag();
        compound.remove(tag);
        nmsCopy.setTag(compound);
    }

    public static String getNBT(ItemStack item, String tag){
        net.minecraft.server.v1_12_R1.ItemStack nmsCopy = CraftItemStack.asNMSCopy(item);
        if(nmsCopy.getTag() == null) return null;
        NBTTagCompound compound = nmsCopy.getTag();
        return compound.getString(tag);
    }

    public static boolean checkNBT(ItemStack item){
        net.minecraft.server.v1_12_R1.ItemStack nmsCopy = CraftItemStack.asNMSCopy(item);
        if(nmsCopy.getTag() == null) return false;
        NBTTagCompound compound = nmsCopy.getTag();
        return (compound.getString(Shulker.TAG).equals("1"));
    }

    public static net.minecraft.server.v1_12_R1.ItemStack getItem(ItemStack item){
        Field handle;
        try {
            handle = CraftItemStack.class.getDeclaredField("handle");
            handle.setAccessible(true);
            return (net.minecraft.server.v1_12_R1.ItemStack)handle.get(item);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
