package com.manhattan.demo.Repositories.Customer;

import com.manhattan.demo.Entities.Customer.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
    List<CustomerEntity> findAllByAtivo(Boolean ativo);
}
