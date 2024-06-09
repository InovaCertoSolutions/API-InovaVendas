package com.inovacerto.apiinovavendas.respositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovacerto.apiinovavendas.models.PaymentMethodModel;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethodModel, UUID> {
    boolean existsByName(String name);
}
