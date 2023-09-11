package com.astro.smiteapi.exceptions;

public class EntityNotFoundException extends NoSuchFieldException {
    public EntityNotFoundException(String s) {
        super(s);
    }

    public EntityNotFoundException() {
    }
}
