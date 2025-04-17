package com.manhattan.demo.Exceptions.ProductType;

public class ProductTypeNotFoundException extends RuntimeException {
    public ProductTypeNotFoundException() {
        super("Não foi possível encontrar o tipo de produto informado");
    }

    public ProductTypeNotFoundException(String message) {
        super(message);
    }
}
