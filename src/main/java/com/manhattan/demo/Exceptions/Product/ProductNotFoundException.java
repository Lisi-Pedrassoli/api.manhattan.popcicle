package com.manhattan.demo.Exceptions.Product;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException() {
        super("O produto informado não foi encontrado");
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
