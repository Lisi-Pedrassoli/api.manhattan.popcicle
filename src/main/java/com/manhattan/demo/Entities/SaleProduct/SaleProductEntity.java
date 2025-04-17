package com.manhattan.demo.Entities.SaleProduct;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.manhattan.demo.Entities.Production.ProductionEntity;
import com.manhattan.demo.Entities.Sale.SaleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Entity(name = "produto_venda")
@Table(name = "produto_venda")
@NoArgsConstructor
@AllArgsConstructor
public class SaleProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String referenciaProduto;

    @Column(nullable = false)
    @Positive
    private int quantidadeSaida;

    @PositiveOrZero
    private int quantidadeVolta;

    @Column(nullable = false)
    @Length(min = 4, max = 50)
    private String tipo;

    @PositiveOrZero
    private float valor;

    @ManyToOne
    @JoinColumn(name = "vendaId")
    @JsonBackReference
    private SaleEntity venda;

    public SaleProductEntity(SaleEntity venda, float valor, String tipo, int quantidadeSaida, String nome, String referenciaProduto) {
        this.venda = venda;
        this.valor = valor;
        this.tipo = tipo;
        this.quantidadeSaida = quantidadeSaida;
        this.referenciaProduto = referenciaProduto;
        this.nome = nome;
    }
}
