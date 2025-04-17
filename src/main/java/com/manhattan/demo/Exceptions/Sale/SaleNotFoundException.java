package com.manhattan.demo.Exceptions.Sale;

public class SaleNotFoundException extends RuntimeException {
    public SaleNotFoundException() {
        super("A venda informada não foi encontrada");
    }

    public SaleNotFoundException(String message) {
        super(message);
    }
}
