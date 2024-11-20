package ru.ilya.knowledge.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("User already exists");
    }
}
