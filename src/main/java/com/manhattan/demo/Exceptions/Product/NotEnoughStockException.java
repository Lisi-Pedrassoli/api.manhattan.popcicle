package com.manhattan.demo.Exceptions.Product;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException() {
        super("O produto informado não possuí estoque o suficiente");
    }

    public NotEnoughStockException(String message) {
        super(message);
    }
}
