package com.manhattan.demo.Repositories.Seller;

import com.manhattan.demo.Entities.Seller.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<SellerEntity, String> {
    List<SellerEntity> findAllByAtivo(Boolean ativo);
}
