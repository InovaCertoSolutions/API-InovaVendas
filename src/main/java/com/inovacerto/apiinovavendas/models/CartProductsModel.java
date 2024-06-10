package com.inovacerto.apiinovavendas.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "cart_products")
@Getter @Setter @NoArgsConstructor
public class CartProductsModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cart_uuid")
    private CartModel cart;

    @ManyToOne
    @JoinColumn(name = "product_uuid")
    private ProductModel product;

    @Column
    private Integer quantity;

    @Column(nullable=false)
    private LocalDateTime creationDate;

    @Column(nullable=true)
    private LocalDateTime lastUpdateDate;

}
