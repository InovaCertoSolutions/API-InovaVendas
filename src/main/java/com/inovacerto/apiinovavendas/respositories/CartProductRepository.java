package com.inovacerto.apiinovavendas.respositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovacerto.apiinovavendas.models.CartProductsModel;

@Repository
public interface CartProductRepository extends JpaRepository<CartProductsModel, UUID> {
}
