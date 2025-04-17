package com.manhattan.demo.Exceptions.Seller;

public class InvalidDocumentException extends RuntimeException {
    public InvalidDocumentException() {
        super("Não possível verificar o documento enviado");
    }

    public InvalidDocumentException(String message) {
        super(message);
    }
}
