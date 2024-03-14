package edu.java.controller.exception;

public class DeleteLinkException extends RuntimeException {
    public DeleteLinkException() {
        super("link has already been deleted");
    }
}
