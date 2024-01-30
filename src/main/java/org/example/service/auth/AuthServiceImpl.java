package org.example.service.auth;

import org.example.dto.auth.AuthenticateDto;
import org.example.dto.auth.RegistrationDto;
import org.example.dto.auth.UserDto;
import org.example.exception.auth.AuthException;
import org.example.mapper.auth.AuthMapper;
import org.example.model.auth.User;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class AuthServiceImpl implements AuthService {

    private final Map<String, User> userMap;
    private final AuthMapper mapper;


    public AuthServiceImpl() {
        userMap = new HashMap<>();
        mapper = new AuthMapper();
    }

    @Override
    public UserDto register(RegistrationDto dto) {
        if (userMap.containsKey(dto.getLogin())) {
            String login = dto.getLogin();
            String message = "ОШИБКА РЕГИСТРАЦИИ: Пользователь с логиноном \"".concat(login.concat("\" уже существует"));
            throw new AuthException(message);
        }

        User user = mapper.registrationDtoToUser(dto);
        userMap.put(user.getLogin(), user);
        return mapper.userToUserDto(user);
    }

    @Override
    public User login(AuthenticateDto dto) {
        String errorMessage = "ОШИБКА АВТОРИЗАЦИИ: введен неверный логин или пароль";
        if (!userMap.containsKey(dto.getLogin())) {
            throw new AuthException(errorMessage);
        }

        User user = userMap.get(dto.getLogin());

        if (!user.getPassword().equals(Base64.getEncoder().encodeToString(dto.getPassword().getBytes()))) {
            throw new AuthException(errorMessage);
        }

        return user;
    }

    @Override
    public User getUserByLogin(String login) {
        User user = userMap.get(login);
        if (user == null) {
            String errorMessage = "Пользователь с логиноном \"".concat(login.concat("\" не существует"));
            throw new AuthException(errorMessage);
        }

        return user;
    }

}
