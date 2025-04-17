package com.manhattan.demo.Entities.Recipe;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.manhattan.demo.Entities.Product.ProductEntity;
import com.manhattan.demo.Entities.RecipeRawMaterial.RecipeRawMaterialEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "receita")
@Table(name = "receita")
@NoArgsConstructor
@AllArgsConstructor
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private boolean ativo;

    @OneToOne
    @JoinColumn(name = "produto_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference
    private ProductEntity produto;

    @OneToMany(mappedBy = "receita", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RecipeRawMaterialEntity> receitaMateriaPrima;

    public RecipeEntity(ProductEntity product) {
        this.produto = product;
        this.ativo = true;
    }
}
