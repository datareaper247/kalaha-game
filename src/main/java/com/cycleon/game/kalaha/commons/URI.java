package com.cycleon.game.kalaha.commons;

public enum URI {
    LOGIN("/login"),
    LOGOUT("/logout"),
    GAME("/game"),
    ACCESS_DENIED("/403"),
    HOME_TEMPLATE("home");

    private final String uriPath;
    private URI(String uriPath) {
        this.uriPath = uriPath;
    }

    public String getUriPath() {
        return uriPath;
    }
}
