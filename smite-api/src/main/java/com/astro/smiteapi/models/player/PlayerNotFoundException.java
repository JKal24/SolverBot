package com.astro.smiteapi.models.player;

public class PlayerNotFoundException extends NoSuchFieldException {
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
