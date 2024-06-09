package com.inovacerto.apiinovavendas.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inventory_managements")
@Getter @Setter @NoArgsConstructor
public class InventoryManagementModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.EAGER)
    private ProductModel product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_uuid", nullable = true)
    private WarehouseModel warehouse;

    // ENTRADA, SAIDA, VENDA, RESERVA
    @Column(length = 20, nullable = false)
    private String managementType;

    @Column
    private Integer quantity;

    @Column
    private Float unitCost;

    @Column(nullable=false)
    private LocalDateTime creationDate;

}
