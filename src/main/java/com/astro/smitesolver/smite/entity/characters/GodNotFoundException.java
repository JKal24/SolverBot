package com.astro.smitesolver.smite.entity.characters;

public class GodNotFoundException extends NoSuchFieldException {

    public GodNotFoundException() { super(); }

    public GodNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
