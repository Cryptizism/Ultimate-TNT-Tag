package me.cryptizism.tnttag.manager;

import me.cryptizism.tnttag.TntTag;

public class GameManager {
    private final TntTag plugin;
    public ItController itController;
    public RoundManager roundManager;
    public ScoreboardManager scoreboardManager;

    public GameState gameState = GameState.LOBBY;

    public GameManager(TntTag plugin) {
        this.plugin = plugin;
        this.roundManager = new RoundManager(this);
        this.scoreboardManager = new ScoreboardManager(this);
        this.itController = new ItController(this);

        this.init();
    }

    public void setGameState(GameState state) {
        if(state == gameState) return;
        gameState = state;
        if(state == GameState.LOBBY){
            this.itController.addAllToPlayers();
        }
    }

    public void init(){
        this.scoreboardManager.createScoreboard();
    }
}
