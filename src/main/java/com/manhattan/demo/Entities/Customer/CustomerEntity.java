package com.manhattan.demo.Entities.Customer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Entity(name = "cliente")
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    @Length(min = 11, max = 11)
    private String telefone;

    @Column(nullable = false)
    @Length(min = 11, max = 14)
    private String documento;

    @Column(nullable = false)
    @Length(min = 3)
    private String nome;

    @Column(nullable = false)
    private Boolean ativo;

    public CustomerEntity(String telefone, String documento, String nome) {
        this.telefone = telefone;
        this.documento = documento;
        this.nome = nome;
        this.ativo = true;
    }
}
