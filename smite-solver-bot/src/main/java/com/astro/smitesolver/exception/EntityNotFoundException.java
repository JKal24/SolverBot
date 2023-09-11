package com.astro.smitesolver.exception;

public class EntityNotFoundException extends NoSuchFieldException {
    public EntityNotFoundException(String s) {
        super(s);
    }

    public EntityNotFoundException() {
    }
}
