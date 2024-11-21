package ru.ilya.knowledge.exception;

import lombok.Data;

@Data
public class Exception {
    private String message;

    public Exception(String message) {
        this.message = message;
    }

    public static Exception create(Throwable e) {
        return new Exception(e.getMessage());
    }

    public static Exception create(String message) {
        return new Exception(message);
    }
}
