package com.cycleon.game.kalaha.commons;

public enum Pit {
    INIT_PLAYER_PIT_SIZE(6);

    private final Integer pitSize;
    private Pit(Integer pitSize) {
        this.pitSize = pitSize;
    }

    public Integer getPitSize() {
        return pitSize;
    }
}
