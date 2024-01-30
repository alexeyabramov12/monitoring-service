package org.example.dto.auth;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String firstName;
    private String lastName;
    private String login;
    private RoleDto role;
}
