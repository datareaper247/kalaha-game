package com.cycleon.game.kalaha.commons;

public enum PlayerKey {
    PLAYER1_KEY("player1"),
    PLAYER2_KEY("player2");

    private final String playerKey;
    private PlayerKey(String playerKey) {
        this.playerKey = playerKey;
    }

    public String getPlayerKey() {
        return playerKey;
    }
}
