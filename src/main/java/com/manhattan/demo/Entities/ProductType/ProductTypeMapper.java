package com.manhattan.demo.Entities.ProductType;

import com.manhattan.demo.Dtos.ProductType.ProductTypeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class ProductTypeMapper {
    public static ProductTypeResponseDto toDto(ProductTypeEntity productTypeEntity){
        return new ProductTypeResponseDto(productTypeEntity.getId(), productTypeEntity.getTipo(),
                productTypeEntity.getValor(), productTypeEntity.isAtivo());
    }

    public static List<ProductTypeResponseDto> toDtoList(Page<ProductTypeEntity> productTypeEntityList){
        return productTypeEntityList.getContent().stream().map(ProductTypeMapper::toDto).collect(Collectors.toList());
    }
}
