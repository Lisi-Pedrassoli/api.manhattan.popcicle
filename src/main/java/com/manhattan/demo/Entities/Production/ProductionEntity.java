package com.manhattan.demo.Entities.Production;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.manhattan.demo.Entities.ProductionRecipe.ProductionRecipeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Table(name = "producao")
@Entity(name = "producao")
@AllArgsConstructor
@NoArgsConstructor
public class ProductionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate dataAtual;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate vencimento;

    @Column(nullable = false)
    private boolean ativo;

    @OneToMany(mappedBy = "producao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProductionRecipeEntity> receitaProducaoModel;

    public ProductionEntity(LocalDate dataAtual, LocalDate vencimento) {
        this.dataAtual = dataAtual;
        this.vencimento = vencimento;
        this.ativo = true;
    }
}
