package edu.java.controller.exception;

public class DeleteChatException extends RuntimeException {
    public DeleteChatException() {
        super("chat has already been deleted");
    }
}
