package edu.java.controller.exception;

public class AddLinkException extends RuntimeException {
    public AddLinkException() {
        super("link has already been created");
    }
}
