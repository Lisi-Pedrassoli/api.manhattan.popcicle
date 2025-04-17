package com.manhattan.demo.Exceptions.User;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message){
        super(message);
    }

    public UserAlreadyExistsException(){
        super("O usuário informado já foi cadastrado");
    }
}
