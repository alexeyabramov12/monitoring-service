package org.example.service.auth;

import org.example.dto.auth.AuthenticateDto;
import org.example.dto.auth.RegistrationDto;
import org.example.dto.auth.UserDto;
import org.example.model.auth.User;

public interface AuthService {

    UserDto register(RegistrationDto registrationDto);
    User login(AuthenticateDto authenticateDto);
    User getUserByLogin(String login);
}
