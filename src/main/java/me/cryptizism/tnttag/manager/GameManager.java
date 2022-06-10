package me.cryptizism.tnttag.manager;

import me.cryptizism.tnttag.TntTag;

public class GameManager {
    private final TntTag plugin;
    public ItController itController;
    public RoundManager roundManager;
    public ScoreboardManager scoreboardManager;
    public BlockManager blockManager;

    public GameState gameState = GameState.LOBBY;

    public GameManager(TntTag plugin) {
        this.plugin = plugin;
        this.roundManager = new RoundManager(this);
        this.scoreboardManager = new ScoreboardManager(this);
        this.itController = new ItController(this);
        this.blockManager = new BlockManager(this);

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
        this.scoreboardManager.createScoreboard();
    }
}
