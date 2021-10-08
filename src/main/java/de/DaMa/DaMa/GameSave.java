package de.DaMa.DaMa;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class GameSave {


    private static File file;
    private static FileConfiguration gameFile;

    GameTeam teams = new GameTeam();

    public GameSave(){

    }

    private void saveTeams(){

        //receive teams
        teams.getTeams();
        //receive teamsMember
        teams.getTeamsMember();

    }











    public void saveInv(Player player) {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("DaMa").getDataFolder(), "game.yml");

        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                //if Plugin Data Folder does not exists, this error appiers once
                //just ignoring :D
            }
        }
        gameFile = YamlConfiguration.loadConfiguration(file);

        /*
        //Modify
        String invArmor = player.getUniqueId() + ".Inventory.Armor";
        String invContent = player.getUniqueId() + ".Inventory.Content";
        for (int i = 0; i < player.getInventory().getArmorContents().length; i++) {
            gameFile.set(invArmor + "." + i, player.getInventory().getArmorContents()[i]);
        }
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            gameFile.set(invContent + "." + i, player.getInventory().getContents()[i]);
        }
         */

        try{
            gameFile.save(file);
        }catch (IOException e){
            System.out.println("Could not save game.yml!");
        }

    }

    public void loadInv(Player player) {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("DaMa").getDataFolder(), "game.yml");
        gameFile = YamlConfiguration.loadConfiguration(file);

        /*
        //Modify
        String configarmor = player.getUniqueId() + ".Inventory.Armor";
        String configcontent = player.getUniqueId() + ".Inventory.Content";
        player.getInventory().clear();
        ItemStack[] content = new ItemStack[4];
        for (int i = 0; i < 4; i++) {
            if(gameFile.getItemStack(configarmor + "." + i) != null) {
                content[i] = gameFile.getItemStack(configarmor + "." + i);
            }
        }
        player.getInventory().setArmorContents(content);
        content = new ItemStack[41];
        for (int i = 0; i < 41; i++) {
            content[i] = gameFile.getItemStack(configcontent + "." + i);
        }
        player.getInventory().setContents(content);
         */
    }














}
