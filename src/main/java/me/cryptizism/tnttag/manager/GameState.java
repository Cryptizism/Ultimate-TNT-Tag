package me.cryptizism.tnttag.manager;

public enum GameState {
    LOBBY/*Game is not initatied wating for players*/,
    PREGAME /*Countdown is running, get everything prepared*/,
    ACTIVE /*The game is running, call functions to start the game*/,
    ENDED /*The game has ended and a player has won*/,
    RESTARTING /*Cleanup has been called*/;
}
