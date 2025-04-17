package com.manhattan.demo.Exceptions.Production;

public class ProductionNotFoundException extends RuntimeException {
    public ProductionNotFoundException() {
        super("A produção informada não foi encontrada");
    }

    public ProductionNotFoundException(String message) {
        super(message);
    }
}
