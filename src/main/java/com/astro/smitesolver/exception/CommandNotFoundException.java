package com.astro.smitesolver.exception;

public class CommandNotFoundException extends IllegalArgumentException{

    public CommandNotFoundException(String s) {
        super(s);
    }

    public CommandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
