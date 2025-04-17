package com.manhattan.demo.Entities.RawMaterial;

import com.manhattan.demo.Enums.RawMaterial.MeasurementUnitEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "materiaPrima")
@Table(name = "materiaPrima")
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private float quantidadeEstoque;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // Salva o enum como texto no banco de dados
    private MeasurementUnitEnum unidadeMedida;

    @Column(nullable = false)
    private boolean ativo;

    public RawMaterialEntity(float quantidadeEstoque, String nome, MeasurementUnitEnum unidadeMedida) {
        this.quantidadeEstoque = quantidadeEstoque;
        this.nome = nome;
        this.unidadeMedida = unidadeMedida;
        this.ativo = true;
    }
}
