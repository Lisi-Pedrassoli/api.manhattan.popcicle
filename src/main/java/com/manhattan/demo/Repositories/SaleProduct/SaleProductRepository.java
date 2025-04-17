package com.manhattan.demo.Repositories.SaleProduct;

import com.manhattan.demo.Entities.SaleProduct.SaleProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleProductRepository extends JpaRepository<SaleProductEntity, String> {
}
