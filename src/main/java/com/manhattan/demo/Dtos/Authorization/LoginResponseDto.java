package com.manhattan.demo.Dtos.Authorization;

import com.manhattan.demo.Dtos.User.UserResponseDto;

public record LoginResponseDto(
        String token,
        UserResponseDto usuario
) {
}
