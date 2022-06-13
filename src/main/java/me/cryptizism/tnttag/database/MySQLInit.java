package me.cryptizism.tnttag.database;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLInit {
    private final String host;
    private final String port;
    private final String database;
    private final String username;
    private final String password;

    public MySQLInit(FileConfiguration config){
        host = config.get("database.host").toString();
        port = config.get("database.port").toString();
        database = config.get("database.database").toString();
        username = config.get("database.user").toString();
        if(config.get("database.password") == null){
            password = "";
        } else {
            password = config.get("database.password").toString();
        }
    }

    private Connection connection;

    public boolean isConnected() {
        return (connection != null);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if(isConnected()) return;
        connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);
    }

    public void disconnect() {
        if(!isConnected()) return;
        try{
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return connection;
    }
}
