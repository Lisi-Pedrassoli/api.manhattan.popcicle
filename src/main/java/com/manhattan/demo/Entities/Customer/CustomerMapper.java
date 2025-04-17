package com.manhattan.demo.Entities.Customer;

import com.manhattan.demo.Dtos.Customer.CustomerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class CustomerMapper {
    public static CustomerResponseDto toDto(CustomerEntity customer){
        return new CustomerResponseDto(customer.getId(), customer.getTelefone(), customer.getNome(), customer.getAtivo());
    }

    public static List<CustomerResponseDto> toDtoList(Page<CustomerEntity> customersList){
        return customersList.getContent().stream().map(CustomerMapper::toDto).toList();
    }

    public static List<CustomerResponseDto> toDtoList(List<CustomerEntity> customersList){
        return customersList.stream().map(CustomerMapper::toDto).toList();
    }
}
