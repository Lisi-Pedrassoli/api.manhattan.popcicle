package com.manhattan.demo.Services.Authorization;

import com.manhattan.demo.Dtos.Authorization.LoginRequestDto;
import com.manhattan.demo.Dtos.Authorization.LoginResponseDto;
import com.manhattan.demo.Dtos.Authorization.RegisterRequestDto;
import com.manhattan.demo.Dtos.User.UserResponseDto;
import com.manhattan.demo.Entities.User.UserMapper;
import com.manhattan.demo.Entities.User.UserEntity;
import com.manhattan.demo.Exceptions.Authorization.InconsistentPasswordException;
import com.manhattan.demo.Exceptions.Authorization.LoginFailedException;
import com.manhattan.demo.Exceptions.User.UserAlreadyExistsException;
import com.manhattan.demo.Infrastructure.Security.TokenService;
import com.manhattan.demo.Services.Log.LogService;
import com.manhattan.demo.Services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LogService logService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = this.userService.findByEmail(username);
        return new org.springframework.security.core.userdetails.User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getAuthorities()
        );
    }

    public LoginResponseDto login(LoginRequestDto body){
        UserEntity userEntity = this.userService.findByEmail(body.email());

        if(!userEntity.isEnabled()){
            throw new LoginFailedException();
        }

        if(!passwordEncoder.matches(body.senha(), userEntity.getPassword())){
            throw new LoginFailedException();
        }

        try {
            String token = this.tokenService.generateToken(userEntity);

            logService.registrar(
                    userEntity.getId(),
                    "Login",
                    "Usuário efetuou login com sucesso."
            );

            UserResponseDto userDto = UserMapper.toDto(userEntity);
            return new LoginResponseDto(token, userDto);
        } catch (Exception e) {
            throw new LoginFailedException();
        }
    }

    public UserResponseDto register(RegisterRequestDto body, String idUsuarioCriador){
        if(!body.senha().equals(body.confirmaSenha())){
            throw new InconsistentPasswordException();
        }

        try {
            this.userService.findByEmail(body.email());
            throw new UserAlreadyExistsException();
        } catch (UsernameNotFoundException | com.manhattan.demo.Exceptions.User.UserNotFoundException e) {
            UserEntity userEntity = new UserEntity(
                    body.nome(),
                    body.email(),
                    passwordEncoder.encode(body.senha())
            );
            UserEntity savedUser = this.userService.save(userEntity);

            logService.registrar(
                    idUsuarioCriador,
                    "Criação de usuário",
                    "Usuário criado com email: " + savedUser.getEmail()
            );

            return UserMapper.toDto(savedUser);
        }
    }
}
