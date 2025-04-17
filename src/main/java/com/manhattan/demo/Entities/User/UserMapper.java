package com.manhattan.demo.Entities.User;

import com.manhattan.demo.Dtos.User.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class UserMapper {
    public static UserResponseDto toDto(UserEntity userEntity){
        return new UserResponseDto(userEntity.getId(), userEntity.getNome(), userEntity.getEmail(),
                userEntity.getCargo(), userEntity.isAtivo());
    }

    public static List<UserResponseDto> toDtoList(Page<UserEntity> userList){
        return userList.getContent().stream().map(UserMapper::toDto).collect(Collectors.toList());
    }
}
