package com.inovacerto.apiinovavendas.respositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovacerto.apiinovavendas.models.CartModel;

@Repository
public interface CartRepository extends JpaRepository<CartModel, UUID> {
}
