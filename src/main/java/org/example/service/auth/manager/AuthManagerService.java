package org.example.service.auth.manager;

import org.example.dto.auth.UserDto;
import org.example.in.auth.AuthInputHandler;
import org.example.model.auth.User;
import org.example.out.OutputHandler;
import org.example.out.auth.AuthOutputHandler;
import org.example.service.auth.AuthService;
import org.example.service.auth.AuthServiceImpl;

public class AuthManagerService {


    private final AuthService authService;
    private final AuthInputHandler inputHandler;
    private final AuthOutputHandler outputHandler;

    public AuthManagerService() {
        authService = new AuthServiceImpl();
        inputHandler = new AuthInputHandler();
        outputHandler = new AuthOutputHandler();
    }

    public void register() {
        UserDto userDto = authService.register(inputHandler.getRegistrationDto());
        outputHandler.displaySuccessfulRegistration(userDto.getLogin());
    }

    public User login() {
        User user = authService.login(inputHandler.getAuthenticateDto());
        outputHandler.displaySuccessfulAuthenticate();
        return user;
    }

    public User getUserByLogin() {
        return authService.getUserByLogin(inputHandler.getLogin());
    }
}
