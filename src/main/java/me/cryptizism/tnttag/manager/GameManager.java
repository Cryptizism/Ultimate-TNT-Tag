package me.cryptizism.tnttag.manager;

import me.cryptizism.tnttag.TntTag;
import me.cryptizism.tnttag.database.MySQLInit;
import me.cryptizism.tnttag.database.MySQLQueries;
import org.bukkit.Bukkit;

import java.sql.SQLException;

public class GameManager {
    private final TntTag plugin;
    public ItController itController;
    public RoundManager roundManager;
    public ScoreboardManager scoreboardManager;
    public BlockManager blockManager;
    public MySQLInit mySQLInit;
    public MySQLQueries mySQLQueries;

    public GameState gameState = GameState.LOBBY;

    public GameManager(TntTag plugin) {
        this.plugin = plugin;
        this.roundManager = new RoundManager(this);
        this.scoreboardManager = new ScoreboardManager(this);
        this.itController = new ItController(this);
        this.blockManager = new BlockManager(this);
        this.mySQLInit = new MySQLInit(plugin.getConfig());

        this.init();
    }

    public void setGameState(GameState state) {
        if(state == gameState) return;
        gameState = state;
        switch(state){
            case LOBBY:
                this.itController.addAllToPlayers();
                break;
            case ACTIVE:
                this.blockManager.addBlocksToPlayers();
                break;
        }
    }

    public void init(){
        //start the score board
        this.scoreboardManager.createScoreboard();

        //start the database
        try {
            this.mySQLInit.connect();
        } catch (ClassNotFoundException | SQLException e) {
            Bukkit.getLogger().info("Failed to connect to the database.");
            e.printStackTrace();
        }
        if(mySQLInit.isConnected()){
            this.mySQLQueries = new MySQLQueries(this);
            mySQLQueries.createTable();
        }
    }

    public void close(){
        mySQLInit.disconnect();
    }
}
