package com.manhattan.demo.Exceptions.Sale;

public class CantCancelSaleException extends RuntimeException {
    public CantCancelSaleException() {
        super("O status atual da venda não permite a alteração de status");
    }

    public CantCancelSaleException(String message) {
        super(message);
    }
}
