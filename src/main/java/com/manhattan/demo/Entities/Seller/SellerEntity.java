package com.manhattan.demo.Entities.Seller;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Entity(name = "vendedor")
@Table(name = "vendedor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    @Length(min = 11, max = 11)
    private String telefone;

    @Column(nullable = false)
    @Positive
    private Float recebimento;

    @Column(nullable = false)
    @Length(min = 11, max = 11)
    private String cpf;

    @Column(nullable = false)
    @Length(min = 3)
    private String nome;

    @Column(nullable = false)
    private Boolean ativo;

    public SellerEntity(String telefone, Float recebimento, String cpf, String nome) {
        this.telefone = telefone;
        this.recebimento = recebimento;
        this.cpf = cpf;
        this.nome = nome;
        this.ativo = true;
    }
}
