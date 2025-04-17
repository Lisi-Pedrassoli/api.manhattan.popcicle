package com.manhattan.demo.Exceptions.Seller;

public class SellerNotFoundException extends RuntimeException {
    public SellerNotFoundException() {
        super("O vendedor informado não foi encontrado");
    }

    public SellerNotFoundException(String message) {
        super(message);
    }
}
