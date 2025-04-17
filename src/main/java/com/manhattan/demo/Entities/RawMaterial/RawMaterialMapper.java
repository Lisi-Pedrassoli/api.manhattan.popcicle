package com.manhattan.demo.Entities.RawMaterial;

import com.manhattan.demo.Dtos.RawMaterial.RawMaterialResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class RawMaterialMapper {
    public static RawMaterialResponseDto toDto(RawMaterialEntity rawMaterial){
        return new RawMaterialResponseDto(rawMaterial.getId(), rawMaterial.getQuantidadeEstoque(), rawMaterial.getNome(),
                rawMaterial.getUnidadeMedida(), rawMaterial.isAtivo());
    }

    public static List<RawMaterialResponseDto> toDtoList(Page<RawMaterialEntity> userList){
        return userList.getContent().stream().map(RawMaterialMapper::toDto).collect(Collectors.toList());
    }
}
