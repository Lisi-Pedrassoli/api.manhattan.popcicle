package com.manhattan.demo.Entities.ProductType;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Getter
@Setter
@Entity(name = "tipoProduto")
@Table(name = "tipoProduto")
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    @Length(min = 4, max = 50)
    private String tipo;

    @Column(nullable = false)
    @PositiveOrZero
    private BigDecimal valor;

    @Column(nullable = false)
    private boolean ativo;

    public ProductTypeEntity(String tipo, BigDecimal valor) {
        this.tipo = tipo;
        this.valor = valor;
        this.ativo = true;
    }
}
