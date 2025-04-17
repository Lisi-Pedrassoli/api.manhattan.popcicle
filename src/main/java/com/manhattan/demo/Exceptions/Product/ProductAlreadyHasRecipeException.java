package com.manhattan.demo.Exceptions.Product;

public class ProductAlreadyHasRecipeException extends RuntimeException {
    public ProductAlreadyHasRecipeException() {
        super("O produto informado já possúí uma receita definida");
    }

    public ProductAlreadyHasRecipeException(String message) {
        super(message);
    }
}
