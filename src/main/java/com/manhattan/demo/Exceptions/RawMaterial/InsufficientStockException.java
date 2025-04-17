package com.manhattan.demo.Exceptions.RawMaterial;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String nome) {
        super("Estoque insuficiente para a matéria-prima: " + nome);
    }
}
