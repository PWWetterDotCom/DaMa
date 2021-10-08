package de.DaMa.DaMa;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class InventorySave implements Listener, CommandExecutor {

    private static File file;
    private static FileConfiguration playerInvFile;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)){
            sender.sendMessage("Du bist kein Spieler!");
        }else{
            if(label.equalsIgnoreCase("saveInv")){
                saveInv(player);
                player.sendMessage("Inventar gespeichert!");

            }else if(label.equalsIgnoreCase("loadInv")){
                loadInv(player);
                player.sendMessage("Inventar geladen!");
            }
        }
        return false;
    }

    public void saveInv(Player player) {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("DaMa").getDataFolder(), "playerInventory.yml");

        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                //if Plugin Data Folder does not exists, this error appiers once
                //just ignoring :D
            }
        }
        playerInvFile = YamlConfiguration.loadConfiguration(file);

        String invArmor = player.getUniqueId() + ".Inventory.Armor";
        String invContent = player.getUniqueId() + ".Inventory.Content";
        for (int i = 0; i < player.getInventory().getArmorContents().length; i++) {
            playerInvFile.set(invArmor + "." + i, player.getInventory().getArmorContents()[i]);
        }
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            playerInvFile.set(invContent + "." + i, player.getInventory().getContents()[i]);
        }

        try{
            playerInvFile.save(file);
        }catch (IOException e){
            System.out.println("Could not save playerInv.yml!");
        }

    }

    public void loadInv(Player player) {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("DaMa").getDataFolder(), "playerInv.yml");
        playerInvFile = YamlConfiguration.loadConfiguration(file);

        String configarmor = player.getUniqueId() + ".Inventory.Armor";
        String configcontent = player.getUniqueId() + ".Inventory.Content";
        player.getInventory().clear();
        ItemStack[] content = new ItemStack[4];
        for (int i = 0; i < 4; i++) {
            if(playerInvFile.getItemStack(configarmor + "." + i) != null) {
                content[i] = playerInvFile.getItemStack(configarmor + "." + i);
            }
        }
        player.getInventory().setArmorContents(content);
        content = new ItemStack[41];
        for (int i = 0; i < 41; i++) {
            content[i] = playerInvFile.getItemStack(configcontent + "." + i);
        }
        player.getInventory().setContents(content);
    }

}

