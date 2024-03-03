package edu.java.controller.exception;

public class AddChatException extends RuntimeException {
    public AddChatException() {
        super("Chat has already been created");
    }
}
