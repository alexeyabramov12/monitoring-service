package org.example.service.auth;

import org.example.dto.auth.AuthenticateDto;
import org.example.dto.auth.RegistrationDto;
import org.example.dto.auth.UserDto;
import org.example.exception.auth.AuthException;
import org.example.model.auth.User;

public interface AuthService {

    UserDto register(RegistrationDto registrationDto) throws AuthException;

    User login(AuthenticateDto authenticateDto) throws AuthException;

    User getUserByLogin(String login) throws AuthException;
}
