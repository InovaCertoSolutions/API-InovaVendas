package com.inovacerto.apiinovavendas.respositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovacerto.apiinovavendas.models.InventoryManagementModel;

@Repository
public interface InventoryManagementRepository extends JpaRepository<InventoryManagementModel, UUID> {
    boolean existsByManagementType(String managementType);
}
