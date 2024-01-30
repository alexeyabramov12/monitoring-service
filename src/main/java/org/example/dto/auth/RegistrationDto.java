package org.example.dto.auth;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private RoleDto role;
}
