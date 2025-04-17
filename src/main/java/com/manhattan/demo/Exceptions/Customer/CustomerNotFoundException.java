package com.manhattan.demo.Exceptions.Customer;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException() {
        super("Não foi possível encontrar o cliente informado");
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
