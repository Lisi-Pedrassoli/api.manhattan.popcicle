package com.manhattan.demo.Entities.RecipeRawMaterial;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.manhattan.demo.Entities.RawMaterial.RawMaterialEntity;
import com.manhattan.demo.Entities.Recipe.RecipeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "receitaMateriaPrima")
@Entity(name = "receitaMateriaPrima")
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRawMaterialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private float quantidadeMP;

    @ManyToOne
    @JoinColumn(name = "materiaPrimaId", nullable = false)
    private RawMaterialEntity materiaPrima;

    @ManyToOne
    @JoinColumn(name = "receita_id", nullable = false)
    @JsonBackReference
    private RecipeEntity receita;

    public RecipeRawMaterialEntity(float quantidadeMP, RawMaterialEntity rawMaterialEntity, RecipeEntity receita) {
        this.quantidadeMP = quantidadeMP;
        this.materiaPrima = rawMaterialEntity;
        this.receita = receita;
    }
}
