package com.manhattan.demo.Repositories.ProductType;

import com.manhattan.demo.Entities.ProductType.ProductTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductTypeEntity, String> {
}
