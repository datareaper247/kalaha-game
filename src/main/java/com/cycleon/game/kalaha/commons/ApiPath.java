package com.cycleon.game.kalaha.commons;

public enum ApiPath {
    TURNS("/api/kalaha/turns"),
    GAME_INFO("/api/kalaha/game"),
    RESTART_GAME("/api/kalaha/game/restart");

    private final String apiPath;
    private ApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public String getApiPath() {
        return apiPath;
    }
}
