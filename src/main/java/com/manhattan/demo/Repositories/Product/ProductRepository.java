package com.manhattan.demo.Repositories.Product;

import com.manhattan.demo.Entities.Product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    @Query(
        value = """
                SELECT
                    *
                FROM produto p
                WHERE
                    p.estoque BETWEEN :start AND :end
        """, nativeQuery = true)
    List<ProductEntity> findByQuantity(@Param("start") int start, @Param("end") int end);

    List<ProductEntity> findAllByRecipeIsNull();
}
