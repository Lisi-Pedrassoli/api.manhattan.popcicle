package com.manhattan.demo.Entities.Product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.manhattan.demo.Entities.ProductType.ProductTypeEntity;
import com.manhattan.demo.Entities.Recipe.RecipeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "produto")
@Table(name = "produto")
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private int estoque;

    @Column(nullable = false)
    private boolean ativo;

    @OneToOne(mappedBy = "produto")
    @JsonBackReference
    private RecipeEntity recipe;

    @ManyToOne
    @JoinColumn(name = "tipo_produto_id", referencedColumnName = "id", nullable = false)
    private ProductTypeEntity tipoProduto;

    public ProductEntity(String nome, int estoque, ProductTypeEntity tipoProduto) {
        this.nome = nome;
        this.estoque = estoque;
        this.tipoProduto = tipoProduto;
        this.ativo = true;
    }
}
