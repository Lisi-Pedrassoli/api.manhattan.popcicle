package com.manhattan.demo.Entities.Seller;

import com.manhattan.demo.Dtos.Seller.SellerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class SellerMapper {
    public static SellerResponseDto toDto(SellerEntity seller){
        return new SellerResponseDto(seller.getId(), seller.getTelefone(), seller.getComissao(), seller.getNome(),
                seller.getAtivo(), seller.getCpf());// alterou aqui para aparecer o cpf
    }

    public static List<SellerResponseDto> toDtoList(Page<SellerEntity> sellersList){
        return sellersList.getContent().stream().map(SellerMapper::toDto).toList();
    }

    public static List<SellerResponseDto> toDtoList(List<SellerEntity> sellersList){
        return sellersList.stream().map(SellerMapper::toDto).toList();
    }
}
