package com.manhattan.demo.Services.Customer;

import com.manhattan.demo.Dtos.Customer.CustomerRequestDto;
import com.manhattan.demo.Dtos.Customer.CustomerResponseDto;
import com.manhattan.demo.Dtos.Customer.CustomerUpdateDto;
import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Dtos.Seller.SellerResponseDto;
import com.manhattan.demo.Entities.Customer.CustomerEntity;
import com.manhattan.demo.Entities.Customer.CustomerMapper;
import com.manhattan.demo.Entities.Seller.SellerEntity;
import com.manhattan.demo.Entities.Seller.SellerMapper;
import com.manhattan.demo.Exceptions.Customer.CustomerNotFoundException;
import com.manhattan.demo.Exceptions.Seller.InvalidDocumentException;
import com.manhattan.demo.Exceptions.Seller.SellerNotFoundException;
import com.manhattan.demo.Repositories.Customer.CustomerRepository;
import com.manhattan.demo.Utils.DocumentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    public List<CustomerResponseDto> findAll(int page, int items){
        Pageable pageable = PageRequest.of(page, items);
        return CustomerMapper.toDtoList(this.repository.findAll(pageable));
    }

    public List<CustomerResponseDto> findActive(){
        return CustomerMapper.toDtoList(this.repository.findAllByAtivo(true));
    }

    public CustomerResponseDto findByIdMap(String id){
        return CustomerMapper.toDto(this.repository.findById(id).orElseThrow(CustomerNotFoundException::new));
    }

    public CustomerEntity findById(String id){
        return this.repository.findById(id).orElseThrow(CustomerNotFoundException::new);
    }

    public void delete(String id){
        CustomerEntity customer = this.findById(id);
        customer.setAtivo(false);
        this.repository.save(customer);
    }

    public CustomerResponseDto save(CustomerRequestDto body){
        if(!DocumentValidator.isValidDocument(body.documento())) throw new InvalidDocumentException();
        return CustomerMapper.toDto(
                this.repository.save(new CustomerEntity(body.telefone(), body.documento(), body.nome())));
    }

    public CustomerResponseDto update(String id, CustomerUpdateDto body){
        CustomerEntity customer = this.findById(id);
        customer.setAtivo(body.ativo());
        customer.setNome(body.nome());
        customer.setTelefone(body.telefone());
        return CustomerMapper.toDto(this.repository.save(customer));
    }

    public CountResponseDto count(){
        return new CountResponseDto(this.repository.count());
    }
}
