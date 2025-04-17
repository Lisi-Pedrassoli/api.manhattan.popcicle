package com.manhattan.demo.Services.Authorization;

import com.manhattan.demo.Dtos.Authorization.LoginRequestDto;
import com.manhattan.demo.Dtos.Authorization.LoginResponseDto;
import com.manhattan.demo.Dtos.Authorization.RegisterRequestDto;
import com.manhattan.demo.Dtos.User.UserResponseDto;
import com.manhattan.demo.Entities.User.UserMapper;
import com.manhattan.demo.Exceptions.Authorization.InconsistentPasswordException;
import com.manhattan.demo.Exceptions.Authorization.LoginFailedException;
import com.manhattan.demo.Exceptions.User.UserAlreadyExistsException;
import com.manhattan.demo.Infrastructure.Security.TokenService;
import com.manhattan.demo.Entities.User.UserEntity;
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
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = this.userService.findByEmail(username);
        return new org.springframework.security.core.userdetails.User(userEntity.getEmail(),
                userEntity.getPassword(), userEntity.getAuthorities());

    }

    public LoginResponseDto login(LoginRequestDto body){
        UserEntity userEntity = this.userService.findByEmail(body.email());

        if(!userEntity.isEnabled()){
            throw new LoginFailedException();
        }

        if(!passwordEncoder.matches(body.senha(), userEntity.getPassword())){
            throw new LoginFailedException();// pega a senha que eu informei e compara com a criptografia do banco
        }

        try {
            return new LoginResponseDto(this.tokenService.generateToken(userEntity), UserMapper.toDto(userEntity));
        } catch (Exception e) {
            throw new LoginFailedException();
        }
    }


    public UserResponseDto register(RegisterRequestDto body){
        if(!body.senha().equals(body.confirmaSenha())){
            throw new InconsistentPasswordException();
        }
        try {
            UserEntity userEntity = this.userService.findByEmail(body.email());
            throw new UserAlreadyExistsException();
        } catch (Exception e){
            return UserMapper.toDto(this.userService.save(new UserEntity(body.nome(), body.email(), passwordEncoder.encode(body.senha()))));
        }
    }
}
