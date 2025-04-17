package com.manhattan.demo.Exceptions.RawMaterial;

public class RawMaterialNotFoundException extends RuntimeException{
    public RawMaterialNotFoundException() {
        super("A matéria-prima informada não foi encontrada");
    }

    public RawMaterialNotFoundException(String message) {
        super(message);
    }
}
