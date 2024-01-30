package org.example.mapper.auth;

import org.example.dto.auth.RegistrationDto;
import org.example.dto.auth.RoleDto;
import org.example.dto.auth.UserDto;
import org.example.model.auth.Role;
import org.example.model.auth.User;

import java.util.Base64;

public class AuthMapper {

    public User registrationDtoToUser(RegistrationDto dto) {
        if (dto == null) {
            return null;
        }

        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .login(dto.getLogin())
                .password(Base64.getEncoder().encodeToString(dto.getPassword().getBytes()))
                .role(dto.getRole().equals(RoleDto.USER) ? Role.USER : Role.ADMIN)
                .build();
    }

    public UserDto userToUserDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().equals(Role.USER) ? RoleDto.USER : RoleDto.ADMIN)
                .login(user.getLogin())
                .build();
    }
}
