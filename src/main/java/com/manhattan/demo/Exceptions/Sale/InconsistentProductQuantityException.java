package com.manhattan.demo.Exceptions.Sale;

public class InconsistentProductQuantityException extends RuntimeException {
    public InconsistentProductQuantityException() {
        super("A quantidade de volta do produto informada é maior do que a de saida");
    }

    public InconsistentProductQuantityException(String message) {
        super(message);
    }
}
