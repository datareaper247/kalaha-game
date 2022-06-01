package com.cycleon.game.kalaha.commons;

public enum Seed {
    BLANK(0),
    INITIAL_PIT_SEED(4);

    private final Integer seed;
    private Seed(Integer seed) {
        this.seed = seed;
    }

    public Integer getSeeds() {
        return seed;
    }
}
