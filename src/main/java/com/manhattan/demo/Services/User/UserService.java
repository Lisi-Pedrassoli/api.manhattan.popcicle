package com.manhattan.demo.Services.User;

import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Dtos.User.UserResponseDto;
import com.manhattan.demo.Dtos.User.UserUpdateDto;
import com.manhattan.demo.Entities.User.UserMapper;
import com.manhattan.demo.Exceptions.User.UserNotFoundException;
import com.manhattan.demo.Repositories.User.UserRepository;
import com.manhattan.demo.Entities.User.UserEntity;
import com.manhattan.demo.Services.Log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LogService logService;

    public UserEntity findByEmail(String email){
        return this.userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public UserEntity save(UserEntity user){
        return this.userRepository.save(user);
    }

    public List<UserResponseDto> findAll(int page, int items){
        Pageable pageable = PageRequest.of(page, items);
        return UserMapper.toDtoList(this.userRepository.findAll(pageable));
    }

    public CountResponseDto count(){
        return new CountResponseDto(this.userRepository.count());
    }

    public UserResponseDto findByIdMap(String userId){
        return UserMapper.toDto(this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new));
    }

    public UserEntity findById(String userId){
        return this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public UserResponseDto update(String id, UserUpdateDto body, String usuarioIdExecutor) {
        UserEntity userEntity = this.findById(id);

        userEntity.setNome(body.nome());
        userEntity.setEmail(body.email());
        userEntity.setAtivo(body.ativo());

        if (body.senha().isPresent()) {
            userEntity.setSenha(passwordEncoder.encode(body.senha().get()));
        }

        UserEntity updated = this.userRepository.save(userEntity);

        logService.registrar(
                usuarioIdExecutor,
                "Atualização de usuário",
                "ID: " + id + ", Nome: " + userEntity.getNome()
        );

        return UserMapper.toDto(updated);
    }


    public void delete(String userId, String usuarioIdExecutor){
        UserEntity userEntity = this.findById(userId);
        userEntity.setAtivo(false);
        this.save(userEntity);

        logService.registrar(usuarioIdExecutor, "Desativação de usuário", "ID: " + userId + ", Nome: " + userEntity.getNome());
    }
}
