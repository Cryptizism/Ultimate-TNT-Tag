package me.cryptizism.tnttag.database;

import me.cryptizism.tnttag.manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class MySQLQueries {

    private GameManager gameManager;
    private final Connection connection;

    public MySQLQueries(GameManager gameManager){
        this.gameManager = gameManager;
        this.connection = gameManager.mySQLInit.getConnection();
    }

    public void createTable(){
        if(!gameManager.mySQLInit.isConnected()) return;
        PreparedStatement ps;
        try{
            ps = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS gamescores " +
                    "(NAME VARCHAR(16),UUID VARCHAR(36),POINTS INT, PRIMARY KEY (UUID))");
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addPlayerToTable(Player player){
        if(!gameManager.mySQLInit.isConnected()) return;
        try{
            PreparedStatement ps;
            String uuid = player.getUniqueId().toString();
            String name = player.getName();
            if(exists(uuid)){ return; }
            ps = connection.prepareStatement(
                    "INSERT IGNORE INTO gamescores " +
                            "(NAME,UUID) VALUES (?,?)"
            );
            ps.setString(1, name);
            ps.setString(2, uuid);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean exists(String uuid){
        if(!gameManager.mySQLInit.isConnected()) return false;
        try{
            PreparedStatement ps;
            ps = connection.prepareStatement(
                    "SELECT * FROM gamescores WHERE UUID=?"
            );
            ps.setString(1, uuid);
            ResultSet results = ps.executeQuery();
            return results.next();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void addPointsToPlayer(Player player, int increase){
        if(!gameManager.mySQLInit.isConnected()) return;
        String uuid = player.getUniqueId().toString();
        int points;
        if(!exists(uuid)){
            Bukkit.getLogger().warning("Player " + player.getName() + " has not been added to the database and has called the addPointsToPlayer function.");
            return;
        }
        try{
            points = getPlayerPoints(player);
        } catch (Exception e){
            Bukkit.getLogger().warning("Failed to fetch player: " + player.getName() + "'s points.");
            return;
        }
        try{
            PreparedStatement ps;
            ps = connection.prepareStatement(
                    "UPDATE gamescores " +
                            "SET POINTS =? " +
                            "WHERE UUID =?"
            );
            ps.setString(1, String.valueOf((points + increase)));
            ps.setString(2, uuid);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getPlayerPoints(Player player) throws Exception{
        if(!gameManager.mySQLInit.isConnected()) throw new Exception();
        String uuid = player.getUniqueId().toString();
        try{
            PreparedStatement ps;
            ps = connection.prepareStatement(
                    "SELECT * FROM gamescores WHERE UUID=?"
            );
            ps.setString(1, uuid);
            ResultSet results = ps.executeQuery();
            results.next();
            return results.getInt("POINTS");
        } catch (SQLException e){
            e.printStackTrace();
        }
        throw new Exception();
    }

    public String[] getTopPlayers(int playerCount){
        try{
            String[] scoreboardText = new String[playerCount];
            PreparedStatement ps;
            ps = connection.prepareStatement(
                    "SELECT * FROM gamescores " +
                            "ORDER BY POINTS DESC " +
                            "LIMIT ?"
            );
            ps.setInt(1, playerCount);
            ResultSet results = ps.executeQuery();
            int i = 0;
            while(results.next()){
                scoreboardText[i] = (results.getString("NAME") + " - " + results.getString("POINTS") + " points");
                i++;
            }
            return scoreboardText;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
