package com.inovacerto.apiinovavendas.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "carts")
@Getter @Setter @NoArgsConstructor
public class CartModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @OneToMany
    @JoinColumn(name = "cart_uuid")
    private List<CartProductsModel> products;

    @Column
    private Float totalPrice;

    @Column(nullable=false)
    private LocalDateTime creationDate;

    @Column(nullable=true)
    private LocalDateTime lastUpdateDate;

}
