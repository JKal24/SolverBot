package com.astro.smiteapi.models.items;

public class ItemNotFoundException extends NoSuchFieldException {
    public ItemNotFoundException() {
    }

    public ItemNotFoundException(String s) {
        super(s);
    }
}
