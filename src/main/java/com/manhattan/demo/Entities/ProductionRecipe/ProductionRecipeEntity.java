package com.manhattan.demo.Entities.ProductionRecipe;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.manhattan.demo.Entities.Production.ProductionEntity;
import com.manhattan.demo.Entities.Recipe.RecipeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "ProducaoReceitaModel")
@Entity(name = "ProducaoReceitaModel")
@NoArgsConstructor
@AllArgsConstructor
public class ProductionRecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private int quantidadeProduzida;

    @ManyToOne
    @JoinColumn(name = "producaoId")
    @JsonBackReference
    private ProductionEntity producao;

    @ManyToOne
    @JoinColumn(name = "receitaId")
    private RecipeEntity receita;

    public ProductionRecipeEntity(int quantidadeProduzida, ProductionEntity producao, RecipeEntity receita) {
        this.quantidadeProduzida = quantidadeProduzida;
        this.producao = producao;
        this.receita = receita;
    }
}
