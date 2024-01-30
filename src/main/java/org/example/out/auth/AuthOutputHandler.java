package org.example.out.auth;

import org.example.out.OutputHandler;

public class AuthOutputHandler extends OutputHandler {

    public void displaySuccessfulRegistration(String login) {
        String message = "Пользователь с логином \"".concat(login.concat("\" зарегестрирован!"));
        System.out.println(message);
    }

    public void displaySuccessfulAuthenticate() {
        System.out.println("Пользователь Авторизован");
    }
}


