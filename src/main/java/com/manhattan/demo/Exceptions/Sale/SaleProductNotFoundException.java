package com.manhattan.demo.Exceptions.Sale;

public class SaleProductNotFoundException extends RuntimeException {
    public SaleProductNotFoundException() {
        super("O produto informado não existe na venda");
    }

    public SaleProductNotFoundException(String message) {
        super(message);
    }
}
