package org.example.service.auth;

import org.example.dto.auth.AuthenticateDto;
import org.example.dto.auth.RegistrationDto;
import org.example.dto.auth.UserDto;
import org.example.exception.auth.AuthException;
import org.example.mapper.auth.AuthMapper;
import org.example.model.auth.User;
import org.example.repository.auth.AuthRepository;

import java.util.Base64;


public class AuthServiceImpl implements AuthService {

    private final AuthMapper mapper;
    private final AuthRepository repository;


    public AuthServiceImpl() {
        repository = new AuthRepository();
        mapper = new AuthMapper();
    }

    @Override
    public UserDto register(RegistrationDto dto) {
        if (repository.exist(dto.getLogin())) {
            String login = dto.getLogin();
            String message = "ОШИБКА РЕГИСТРАЦИИ: Пользователь с логиноном \"".concat(login.concat("\" уже существует"));
            throw new AuthException(message);
        }

        User user = mapper.registrationDtoToUser(dto);
        repository.add(user);
        return mapper.userToUserDto(user);
    }

    @Override
    public User login(AuthenticateDto dto) {
        String errorMessage = "ОШИБКА АВТОРИЗАЦИИ: введен неверный логин или пароль";
        if (!repository.exist(dto.getLogin())) {
            throw new AuthException(errorMessage);
        }

        User user = repository.get(dto.getLogin());

        if (!user.getPassword().equals(Base64.getEncoder().encodeToString(dto.getPassword().getBytes()))) {
            throw new AuthException(errorMessage);
        }

        return user;
    }

    @Override
    public User getUserByLogin(String login) {
        User user = repository.get(login);
        if (user == null) {
            String errorMessage = "Пользователь с логином \"".concat(login.concat("\" не существует"));
            throw new AuthException(errorMessage);
        }

        return user;
    }

}
