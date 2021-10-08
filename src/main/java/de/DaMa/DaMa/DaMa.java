package de.DaMa.DaMa;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class DaMa extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("DaMa aktiviert!");

        //Test :D
        //Bukkit.getPluginManager().registerEvents(new GameCommands(), this);
        Bukkit.getPluginManager().registerEvents(new InventorySave(), this);

        getServer().getPluginCommand("start").setExecutor(new GameCommands());
        getServer().getPluginCommand("pause").setExecutor(new GameCommands());
        getServer().getPluginCommand("stop").setExecutor(new GameCommands());

        getServer().getPluginCommand("loadInv").setExecutor(new InventorySave());
        getServer().getPluginCommand("saveInv").setExecutor(new InventorySave());

        getServer().getPluginCommand("team").setExecutor(new GameTeam());

    }
    @Override
    public void onDisable() {
        getLogger().info("DaMa deaktiviert!");

    }

}