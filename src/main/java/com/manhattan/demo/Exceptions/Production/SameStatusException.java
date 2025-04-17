package com.manhattan.demo.Exceptions.Production;

public class SameStatusException extends RuntimeException {
    public SameStatusException() {
        super("A produção já possui o status informado");
    }

    public SameStatusException(String message) {
        super(message);
    }
}
