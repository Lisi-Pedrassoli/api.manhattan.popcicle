package com.manhattan.demo.Entities.Sale;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.manhattan.demo.Entities.Customer.CustomerEntity;
import com.manhattan.demo.Entities.SaleProduct.SaleProductEntity;
import com.manhattan.demo.Entities.Seller.SellerEntity;
import com.manhattan.demo.Enums.Sale.SaleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity(name = "venda")
@Table(name = "venda")
@AllArgsConstructor
public class SaleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private Long dataCriacao;

    @Column(nullable = false)
    private Long codigo;

    @Column(nullable = false)
    private Float total;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SaleStatus status;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private CustomerEntity cliente;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", referencedColumnName = "id")
    private SellerEntity vendedor;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SaleProductEntity> produtoVenda;

    public SaleEntity(CustomerEntity cliente, SellerEntity vendedor) {
        this.dataCriacao = Instant.now().toEpochMilli();
        this.codigo = Instant.now().toEpochMilli();
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.status = SaleStatus.OPENED;
    }

    public SaleEntity() {
        this.dataCriacao = Instant.now().toEpochMilli();
        this.codigo = Instant.now().toEpochMilli();
        this.status = SaleStatus.OPENED;
    }
}
