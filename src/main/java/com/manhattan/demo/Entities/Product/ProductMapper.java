package com.manhattan.demo.Entities.Product;

import com.manhattan.demo.Dtos.Product.ProductResponseDto;
import com.manhattan.demo.Entities.ProductType.ProductTypeMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class ProductMapper {
    public static ProductResponseDto toDto(ProductEntity product){
        return new ProductResponseDto(product.getId(), product.getNome(), product.getEstoque(), product.isAtivo(),
                ProductTypeMapper.toDto(product.getTipoProduto()));
    }

    public static List<ProductResponseDto> toDtoList(Page<ProductEntity> productsList){
        return productsList.getContent().stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }

    public static List<ProductResponseDto> toDtoList(List<ProductEntity> productsList){
        return productsList.stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }
}
