package org.example.in.auth;

import org.example.dto.auth.AuthenticateDto;
import org.example.dto.auth.RegistrationDto;
import org.example.dto.auth.RoleDto;
import org.example.exception.auth.AuthException;
import org.example.in.InputHandler;
import org.example.out.OutputHandler;
import org.example.out.auth.AuthOutputHandler;

import java.util.Scanner;

public class AuthInputHandler extends InputHandler {


    private final AuthOutputHandler outputHandler;
    private Scanner scanner;


    public AuthInputHandler() {
        outputHandler = new AuthOutputHandler();
        scanner = new Scanner(System.in);
    }


    public RegistrationDto getRegistrationDto() {
        RegistrationDto registrationDto = new RegistrationDto();
        outputHandler.displayMassage("Введите ваше имя:");
        registrationDto.setFirstName(checkEmpty(scanner.nextLine().trim()));

        outputHandler.displayMassage("Введите вашу фамилию:");
        registrationDto.setLastName(checkEmpty(scanner.nextLine().trim()));

        outputHandler.displayMassage("Придумайте логин:");
        registrationDto.setLogin(checkEmpty(scanner.nextLine().trim()));

        outputHandler.displayMassage("Придумайте пароль:");
        registrationDto.setPassword(checkEmpty(scanner.nextLine().trim()));

        outputHandler.displayMassage("Если вы простой пользователь нажмите - 1, если вы админ нажмите -2");
        String role = checkEmpty(scanner.nextLine().trim());
        if (role.equals("1")) {
            registrationDto.setRole(RoleDto.USER);
        } else if (role.equals("2")) {
            registrationDto.setRole(RoleDto.ADMIN);
        } else throw new AuthException("ОШИБКА РЕГИСТРАЦИИ: неправильно указана роль пользователя");
        return registrationDto;
    }

    public AuthenticateDto getAuthenticateDto() {
        AuthenticateDto authenticateDto = new AuthenticateDto();
        outputHandler.displayMassage("Введите логин:");
        authenticateDto.setLogin(checkEmpty(scanner.nextLine().trim()));

        outputHandler.displayMassage("Введите пароль:");
        authenticateDto.setPassword(checkEmpty(scanner.nextLine().trim()));

        return authenticateDto;
    }

    public String getLogin() {
        outputHandler.displayMassage("Введите логин пользователя:");
        return checkEmpty(scanner.nextLine().trim());

    }
}
