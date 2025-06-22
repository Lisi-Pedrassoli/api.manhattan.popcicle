package com.manhattan.demo.Exceptions.Production;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(String message) {
        super(message);
    }

    public InvalidDateException() {
        super("A data de vencimento não pode ser anterior à data de fabricação.");
    }
}
