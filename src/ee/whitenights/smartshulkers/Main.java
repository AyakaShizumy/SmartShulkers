package ee.whitenights.smartshulkers;

import ee.whitenights.smartshulkers.command.Command;
import ee.whitenights.smartshulkers.handler.PlayerInteractHandler;
import ee.whitenights.smartshulkers.handler.PlayerInventoryClick;
import ee.whitenights.smartshulkers.handler.PlayerInventoryClose;
import ee.whitenights.smartshulkers.handler.PlayerPickupItem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin {

    public static FileConfiguration config;
    public static Logger logger;

    @Override
    public void onEnable(){
        this.saveDefaultConfig();
        config = getConfig();
        logger = getLogger();
        getServer().getPluginManager().registerEvents(new PlayerPickupItem(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerInventoryClick(), this);
        getServer().getPluginManager().registerEvents(new PlayerInventoryClose(), this);
        this.getCommand("ss").setExecutor(new Command());
    }

    @Override
    public  void onDisable(){

    }
}
