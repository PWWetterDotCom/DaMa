package de.DaMa.DaMa;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameTeam implements Listener, CommandExecutor {

    String teamName;
    Integer minPlayer;
    Integer maxPlayer;
    String teamOwner;
    String teamMember;

    //Use a Hashmap to store team (string) as key and config (string array) as value
    Map<String, String[]> teams = new HashMap<>();
    //UUID from player -> teamMember, teamName
    Map<String, String> teamsMember = new HashMap<>();

    //DD Edit Start 1

    //Key: UUID from player -> Coordinate x, y, z
    Map<String, Double[]> memberCoords = new HashMap<>();

    //Key: teamName -> Team Coordinate x, y, z
    Map<String, Double[]> teamCoords = new HashMap<>();

    public Map<String, Double[]> getMemberCoords(Player player) {
        return memberCoords;
    }

    public Map<String, Double[]> getTeamCoords(String teamName) {
        return teamCoords;
    }

    //public double getDistance(String teamName1, String teamName2) return distance

    //DD Edit Ende 1

    public Map<String, String[]> getTeams() {
        return teams;
    }

    public Map<String, String> getTeamsMember() {
        return teamsMember;
    }

    public GameTeam() {}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)){
            sender.sendMessage("Du kannst diesen Befehl nur als Spieler ausführen!");
            return true;
        } else {
            if(label.equalsIgnoreCase("team")){
                if(args[0].equalsIgnoreCase("create")){
                    if(args.length == 4){
                        team_create(player, args);
                        return true;
                    }else {
                        player.sendMessage("Usage: /team create teamName minPlayer maxPlayer");
                    }

                }else if(args[0].equalsIgnoreCase("delete")){
                    if (args.length == 1) {
                        team_delete(player);
                        return true;
                    }else{
                        player.sendMessage("Usage: /team delete");
                    }

                } else if(args[0].equalsIgnoreCase("join")){
                    if(args.length == 2){
                        team_join(player, args);
                        return true;
                    }else{
                        player.sendMessage("Usage: /team join teamName");
                    }

                } else if(args[0].equalsIgnoreCase("leave")) {
                    if(args.length == 1) {
                        team_leave(player);
                        return true;
                    }else{
                        player.sendMessage("Usage: /team leave");
                    }

                } else if(args[0].equalsIgnoreCase("list")){
                    if(args.length == 1){
                        team_list(player);
                        //Only for testing!
                        //team_member_cords(player);
                        return true;
                    }else{
                        player.sendMessage("Usage: /team list");
                    }

                }else {
                    player.sendMessage("Usage: /team [create | delete | join | leave]");
                }
                return true;
            }
        }
        return false;
    }

    private void team_create(Player player, String[] args) {

        if (teams.size() == 10) {
            player.sendMessage(ChatColor.RED + "Du kannst keine Teams mehr erstellen da bereits 10 Teams vorhanden sind!");
            return;
        }

        if (teams.containsKey(args[1])) {
            player.sendMessage(ChatColor.RED + "Diese Team existiert bereits!");
            return;
        } else if (args[2].length() < 1) {
            player.sendMessage(ChatColor.RED + "Bitte Teamname angeben!");
            return;
        } else {
            teamName = (args[1]);
        }

        try {
            minPlayer = Integer.valueOf((args[2]));
            maxPlayer = Integer.valueOf((args[3]));
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "minPlayer / maxPlayer muss eine Zahl sein!");
            return;
        }

        if (args[2].equals("0") || args[3].equals("0")) {
            player.sendMessage(ChatColor.RED + "Dein Team muss mindestens einen Spieler enthalten!");
            return;
        } else if (Integer.parseInt(args[2]) > Integer.parseInt(args[3])) {
            player.sendMessage(ChatColor.RED + "minPlayer darf nicht größer als maxPlayer sein!");
            return;
        } else if (Integer.parseInt(args[3]) < Integer.parseInt(args[2])) {
            player.sendMessage(ChatColor.RED + "maxPlayer darf nicht kleiner als minPlayer sein!");
            return;
        } else if (Integer.parseInt(args[2]) > 10) {
            player.sendMessage(ChatColor.RED + "minPlayer kann maximal 10 sein!");
            return;
        } else if (Integer.parseInt(args[3]) > 10) {
            player.sendMessage(ChatColor.RED + "maxPlayer kann maximal 10 sein!");
            return;
        }

        teamOwner = (player.getUniqueId().toString());

        String[] teamConfig = new String[]{String.valueOf(minPlayer), String.valueOf(maxPlayer), teamOwner};
        teams.put(teamName, teamConfig);

        player.sendMessage("Team " + ChatColor.BLUE + teamName + ChatColor.WHITE + " wurde erstellt!");
        //Let teamOwner join directly the team
        //Activate after debug
        team_join(player, args);

    }

    private void team_delete(Player player) {

        teamMember = player.getUniqueId().toString();
        if (teams.containsKey(teamName)) {
            //Hashmap teamMember | teamName -> key = teamMember, value = teamName
            teamName = teamsMember.get(teamMember);
            teamOwner = teams.get(teamName)[2];
            //check if teamMember is teamOwner (permission check)
            if (teams.get(teamName)[2].equals(teamMember)) {
                //check if teamsMember contains teamName, otherwise there is nothing to delete because team is not existing!
                if (teamsMember.containsValue(teamName)) {
                    //get every entry in teamsMember and save it in var member
                    for (Map.Entry member : teamsMember.entrySet()) {
                        //if teamName from saved teamMember in is equal then
                        if (teamName.equals(member.getValue().toString())) {
                            //if the team contains other teamMember, remove them and send a message
                            //not necessary to inform the owner that he was removed from the team, because he deleted it!
                            if (!teamMember.equals(teamOwner)) {
                                teamsMember.remove(member.getKey().toString());
                                //access current player, to send regarding player the message
                                //if you use the current player, then only he gets the message (keep in mind)!
                                Bukkit.getPlayer(UUID.fromString(member.getKey().toString())).sendMessage("Du wurdest aus dem Team " + ChatColor.BLUE + teamName + ChatColor.WHITE + " entfernt, " +
                                        "da dieses von " + ChatColor.RED + player.getDisplayName() + ChatColor.WHITE + " gelöscht wurde!");
                            }
                        }
                    }
                }
                teams.remove(teamName);
                player.sendMessage("Team " + ChatColor.BLUE + teamName + ChatColor.WHITE + " wurde gelöscht!");
            } else {
                player.sendMessage("Team " + ChatColor.BLUE + teamName + ChatColor.WHITE + " konnte nicht gelöscht werden, da du nicht der Besitzer bist!");
            }
        } else {
            player.sendMessage("Team " + ChatColor.BLUE + teamName + ChatColor.WHITE + " konnte nicht gelöscht werden, da es nicht existiert!");
        }
    }

    private int count_TeamMember(Player player, String teamName) {

        //added player parameter only to debug values, but we can leave it there

        int count = 0;

        for (Map.Entry member : teamsMember.entrySet()) {
            if (member.getValue().toString().equals(teamName)) {
                count++;
            }
        }
        return count;

    }

    private String[] get_TeamMembers(String teamName) {

        int counter = 0;
        //size of array starts counting by 1 and ends by 10, so it has 10 entrys!
        //setting size to 10, because maxPlayer cannot be more than 10
        String[] teamMembers = new String[10];

        //get every member from Hashmap teamsMember (teamMember | teamName)
        for (Map.Entry member : teamsMember.entrySet()) {
            //if given teamName is equal to saved teamName in Hashmap teamsMember then
            if (teamName.equals(member.getValue().toString())) {
                //because of hard coded size of array, we need to count the array up to add a new value
                //adding from current EntrySet the teamMember
                teamMembers[counter] = member.getKey().toString();
                //counting arraycounter +1
                counter++;
            }

        }

        return teamMembers;
    }

    private void team_join(Player player, String[] args) {

        teamMember = player.getUniqueId().toString();
        teamName = args[1];

        //Count Hashmap teams to check later if team is already full
        //replace with new method
        //Check, it seems to be wrong

        if (teams.containsKey(teamName)) {
            if (!teamsMember.containsKey(teamMember)) {
                if (count_TeamMember(player,teamName) != Integer.parseInt(teams.get(teamName)[1])) {
                    teamsMember.put(teamMember, teamName);
                    player.sendMessage("Du bist dem Team " + ChatColor.BLUE + teamName + ChatColor.WHITE + " beigetreten!");
                    player.sendMessage("Dein Team hat nun " + count_TeamMember(player,teamName) + " Spieler.");
                } else {
                    player.sendMessage("Das Team " + ChatColor.BLUE + teamName + ChatColor.WHITE + " ist bereits voll!");
                }

            } else {
                player.sendMessage("Du kannst dem Team " + ChatColor.BLUE + teamName + ChatColor.WHITE + " nicht beitreten, " +
                        "da du bereits im Team " + ChatColor.BLUE + teamsMember.get(teamMember) + ChatColor.WHITE + " bist!");
            }

        } else {
            player.sendMessage("Du kannst dem Team " + ChatColor.BLUE + teamName + ChatColor.WHITE + " nicht beitreten, da dieses nicht existiert!");
        }

    }

    private void team_leave(Player player) {

        teamMember = player.getUniqueId().toString();

        if (teamsMember.containsKey(teamMember)) {

            //teamName = teamsMember.get(teamMember);
            teamOwner = teams.get(teamName)[2];

            //leave code here, because if player is teamOwner -> team is first deleted, and then you cannot access it anymore!
            player.sendMessage("Du hast das Team " + ChatColor.BLUE + teamsMember.get(teamMember) + ChatColor.WHITE + " verlassen!");

            if (teamMember.equals(teamOwner)) {
                team_delete(player);
            }

            teamsMember.remove(teamMember);

        } else {
            player.sendMessage("Team " + ChatColor.BLUE + teamName + ChatColor.WHITE + " konnte nicht verlassen werden, da es nicht existiert!");
        }
    }

    private void team_list(Player player) {

        if (teams.size() < 1) {
            player.sendMessage(ChatColor.RED + "Es gibt noch keine Teams!");
            return;
        }

        for (Map.Entry team : teams.entrySet()) {

            teamName = team.getKey().toString();
            minPlayer = Integer.parseInt(teams.get(teamName)[0]);
            maxPlayer = Integer.parseInt(teams.get(teamName)[1]);
            teamOwner = teams.get(teamName)[2];

            //Spieler ermitteln und in Array speichern und dann ausgeben
            //Methode mit teamname als Parameter überladen und dann dazu Spieler ausgeben

            player.sendMessage("Team: " + ChatColor.BLUE + teamName + ChatColor.WHITE + " minPlayer: " + minPlayer + " maxPlayer: " + maxPlayer +
                    " Teamgröße: " + count_TeamMember(player,teamName) + " teamOwner: " + Bukkit.getPlayer(UUID.fromString(teamOwner)).getDisplayName());

            //Array in Schleife durchlaufen und Spielernamen zu UUID holen
            //add new Schleife to get DisplayName instead of uuid
            //String[] test = Bukkit.getPlayer(UUID.fromString(teamOwner)).getDisplayName());
            player.sendMessage("Es sind folgende Spieler in deinem Team: " + ChatColor.BLUE + Arrays.toString(get_TeamMembers(teamName)));

        }

    }

    //DD Edit Start 2
    private void team_member_cords(Player player) {
        //Get Coordinates form Player (UUID) and save in HashMap memberCoords
        //Double[0] = X; Double[1] = Y; Double[2] = Z;
        teamMember = player.getUniqueId().toString();
        Double[] memberCoords_local = new Double[3];

        memberCoords_local[0] = player.getLocation().getX();
        memberCoords_local[1] = player.getLocation().getY();
        memberCoords_local[2] = player.getLocation().getZ();

        //player.sendMessage("Test: X-Koordinate: " + memberCoords_local[0]);
        //player.sendMessage("Test: Y-Koordinate: " + memberCoords_local[1]);
        //player.sendMessage("Test: Z-Koordinate: " + memberCoords_local[2]);

        memberCoords.put(teamMember, memberCoords_local);

        player.sendMessage("Test: X-Koordinate: " + memberCoords.get(teamMember)[0]);
        player.sendMessage("Test: Y-Koordinate: " + memberCoords.get(teamMember)[1]);
        player.sendMessage("Test: Z-Koordinate: " + memberCoords.get(teamMember)[2]);
    }

    private void team_teamCoords(String teamName) {
        //Benötigt werden: Aktuelle anzahl an aktiven Spielern
        //Variablen von allen Spielern die im team sind holen und addieren
        //durch Anzahl teilen, und in Hashmap speichern

        Double[] memberCoords_local = new Double[3];

        teamCoords.put(teamName, memberCoords_local);
    }
    //DD Edit Ende 2

}