package com.manhattan.demo.Repositories.Production;

import com.manhattan.demo.Entities.Production.ProductionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductionRepository extends JpaRepository<ProductionEntity, String> {
    @Query(value = "SELECT * FROM producao p WHERE p.data_atual >= :start AND p.vencimento <= :end", nativeQuery = true)
    List<ProductionEntity> findByDate(@Param("start") java.sql.Date start, @Param("end") java.sql.Date end);
}
