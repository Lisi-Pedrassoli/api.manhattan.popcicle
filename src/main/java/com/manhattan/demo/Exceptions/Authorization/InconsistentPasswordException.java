package com.manhattan.demo.Exceptions.Authorization;

public class InconsistentPasswordException extends RuntimeException {
    public InconsistentPasswordException(String message){
        super(message);
    }

    public InconsistentPasswordException(){
        super("As senhas informadas não são iguais");
    }
}
