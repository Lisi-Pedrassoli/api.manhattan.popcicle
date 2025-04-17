package com.manhattan.demo.Exceptions.Authorization;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException() {
        super("Usuário ou senha incorretos");
    }
}
