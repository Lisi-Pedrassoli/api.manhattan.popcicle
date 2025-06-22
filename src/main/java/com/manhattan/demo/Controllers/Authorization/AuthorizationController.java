package com.manhattan.demo.Controllers.Authorization;

import com.manhattan.demo.Dtos.Authorization.LoginRequestDto;
import com.manhattan.demo.Dtos.Authorization.LoginResponseDto;
import com.manhattan.demo.Dtos.Authorization.RegisterRequestDto;
import com.manhattan.demo.Dtos.User.UserResponseDto;
import com.manhattan.demo.Entities.User.UserEntity;
import com.manhattan.demo.Services.Authorization.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto body){
        return ResponseEntity.ok(authorizationService.login(body));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid RegisterRequestDto body,
                                                    @AuthenticationPrincipal UserEntity usuarioLogado){
        UserResponseDto response = authorizationService.register(body, usuarioLogado != null ? usuarioLogado.getId() : null);
        return ResponseEntity.ok(response);
    }
}
