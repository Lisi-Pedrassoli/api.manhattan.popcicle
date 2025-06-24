package com.manhattan.demo.Repositories.SaleProduct;

import com.manhattan.demo.Entities.SaleProduct.SaleProductEntity;
import com.manhattan.demo.Entities.Sale.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SaleProductRepository extends JpaRepository<SaleProductEntity, String> {

    @Transactional
    void deleteAllByVenda(SaleEntity venda);
}
