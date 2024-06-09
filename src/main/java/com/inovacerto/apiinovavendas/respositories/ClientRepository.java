package com.inovacerto.apiinovavendas.respositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovacerto.apiinovavendas.models.ClientModel;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, UUID> {
    boolean existsByPhone(String phone);
}
