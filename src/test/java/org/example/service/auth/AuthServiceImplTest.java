package org.example.service.auth;

import org.example.dto.auth.AuthenticateDto;
import org.example.dto.auth.RegistrationDto;
import org.example.dto.auth.RoleDto;
import org.example.dto.auth.UserDto;
import org.example.exception.auth.AuthException;
import org.example.model.auth.Role;
import org.example.model.auth.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceImplTest {

    private User user1;
    private User user2;
    private User admin;
    private RegistrationDto registrationDto1;
    private RegistrationDto registrationDto2;
    private RegistrationDto registrationDto3;
    private AuthServiceImpl authService;

    @BeforeEach
    public void setUp() {
        authService = new AuthServiceImpl();

        user1 = User.builder()
                .firstName("Алекс")
                .lastName("Иванов")
                .login("Рокосовского 12")
                .password(Base64.getEncoder().encodeToString("123".getBytes()))
                .role(Role.USER)
                .build();

        user2 = User.builder()
                .firstName("Алексей")
                .lastName("сидоров")
                .login("Крылова 12")
                .password(Base64.getEncoder().encodeToString("123".getBytes()))
                .role(Role.USER)
                .build();

        admin = User.builder()
                .firstName("Антон")
                .lastName("Петров")
                .login("King")
                .password(Base64.getEncoder().encodeToString("123".getBytes()))
                .role(Role.ADMIN)
                .build();

        registrationDto1 = RegistrationDto.builder()
                .firstName("Алекс")
                .lastName("Иванов")
                .login("Рокосовского 12")
                .password("123")
                .role(RoleDto.USER)
                .build();

        registrationDto2 = RegistrationDto.builder()
                .firstName("Алексей")
                .lastName("сидоров")
                .login("Крылова 12")
                .password("123")
                .role(RoleDto.USER)
                .build();

        registrationDto3 = RegistrationDto.builder()
                .firstName("Антон")
                .lastName("Петров")
                .login("King")
                .password("123")
                .role(RoleDto.ADMIN)
                .build();

    }

    @Test
    @DisplayName("Test register one user")
    public void ifOneUser_register_thenRegistration() {
        UserDto userDto = UserDto.builder().firstName("Антон")
                .firstName("Алекс")
                .lastName("Иванов")
                .login("Рокосовского 12")
                .role(RoleDto.USER)
                .build();
        assertEquals(userDto, authService.register(registrationDto1));
    }

    @Test
    @DisplayName("Test register two users with the same login")
    public void ifTwoUserWithSameLogin_register_thenException() {
        authService.register(registrationDto1);
        registrationDto2.setLogin(registrationDto1.getLogin());
        assertThrows(AuthException.class, () -> authService.register(registrationDto2));
    }

    @Test
    @DisplayName("Test login with correct dto")
    public void ifOneUser_login_thenLogin() {
        AuthenticateDto authenticateDto = new AuthenticateDto();
        authenticateDto.setLogin(user1.getLogin());
        authenticateDto.setPassword("123");

        authService.register(registrationDto1);
        assertEquals(user1, authService.login(authenticateDto));
    }

    @Test
    @DisplayName("Test login with wrong login")
    public void ifWrongLogin_login_thenException() {
        AuthenticateDto authenticateDto = new AuthenticateDto();
        authenticateDto.setLogin(user2.getLogin());
        authenticateDto.setPassword("123");

        authService.register(registrationDto1);
        assertThrows(AuthException.class, () -> authService.login(authenticateDto));
    }

    @Test
    @DisplayName("Test login with wrong password")
    public void ifWrongPassword_login_thenException() {
        AuthenticateDto authenticateDto = new AuthenticateDto();
        authenticateDto.setLogin(user1.getLogin());
        authenticateDto.setPassword("124");

        authService.register(registrationDto1);
        assertThrows(AuthException.class, () -> authService.login(authenticateDto));
    }

    @Test
    @DisplayName("Test getUserByLogin with correct login")
    public void ifCorrectLogin_getUserByLogin_thenGetUser() {
        authService.register(registrationDto1);
        assertEquals(user1, authService.getUserByLogin(user1.getLogin()));
    }

    @Test
    @DisplayName("Test getUserByLogin with wrong login")
    public void ifWrongLogin_getUserByLogin_thenException() {
        authService.register(registrationDto1);
        assertThrows(AuthException.class, () -> authService.getUserByLogin(user2.getLogin()));
    }

    @Test
    @DisplayName("Test getUserByLogin with wrong login")
    public void ifNoRegisteredUser_getUserByLogin_thenException() {
        assertThrows(AuthException.class, () -> authService.getUserByLogin(user1.getLogin()));
    }

}
