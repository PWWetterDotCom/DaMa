package de.DaMa.DaMa;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class GameCommands implements Listener, CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)){
            sender.sendMessage("Du bist kein Spieler!");
        }else{
            if(label.equalsIgnoreCase("start")){

                player.getPlayer().sendMessage("Spiel wurde gestartet!");
            }else if(label.equalsIgnoreCase("pause")){
                player.getPlayer().sendMessage("Spiel wurde pausiert!");
            }else if(label.equalsIgnoreCase("stop")){
                player.getPlayer().sendMessage("Spiel wurde gestoppt (Reset)!");
            }
        }
        return false;
    }
}
