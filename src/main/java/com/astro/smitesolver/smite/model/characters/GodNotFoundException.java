package com.astro.smitesolver.smite.model.characters;

public class GodNotFoundException extends NoSuchFieldException {

    public GodNotFoundException() { super(); }

    public GodNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
