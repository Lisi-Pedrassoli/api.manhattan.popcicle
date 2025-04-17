package com.manhattan.demo.Dtos.User;


import com.manhattan.demo.Enums.User.UserRoles;

public record UserResponseDto(
        String id,
        String nome,
        String email,
        UserRoles cargo,
        boolean ativo
) {
}
