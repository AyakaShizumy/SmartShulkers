package ee.whitenights.smartshulkers;

import ee.whitenights.smartshulkers.shulker.command.Command;
import ee.whitenights.smartshulkers.shulker.handler.PlayerInteractHandler;
import ee.whitenights.smartshulkers.shulker.handler.PlayerInventoryClick;
import ee.whitenights.smartshulkers.shulker.handler.PlayerInventoryClose;
import ee.whitenights.smartshulkers.shulker.handler.PlayerPickupItem;
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

        int pluginId = 13421;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> String.valueOf(1)));

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
