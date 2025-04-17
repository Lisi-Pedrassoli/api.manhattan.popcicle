package com.manhattan.demo.Exceptions.User;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message){
        super(message);
    }

    public UserNotFoundException(){
        super("O usuário informado não foi encontrado");
    }
}
