package com.manhattan.demo.Repositories.Sale;

import com.manhattan.demo.Entities.Sale.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<SaleEntity, String> {
    @Query(value = "SELECT * FROM venda v WHERE v.data_criacao >= :start AND v.data_criacao <= :end", nativeQuery = true)
    List<SaleEntity> findByDate(@Param("start") Long start, @Param("end") Long end);
}
