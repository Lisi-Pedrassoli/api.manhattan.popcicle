package com.manhattan.demo.Repositories.RawMaterial;


import com.manhattan.demo.Entities.RawMaterial.RawMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RawMaterialRepository extends JpaRepository<RawMaterialEntity, String> {

    @Query(
            value = """
                        SELECT
                            *
                        FROM materia_prima mp 
                        WHERE 
                            mp.quantidade_estoque BETWEEN :start AND :end
                    """, nativeQuery = true)
    List<RawMaterialEntity> findByQuantity(@Param("start") int start, @Param("end") int end);
}
