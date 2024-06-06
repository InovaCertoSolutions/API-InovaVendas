package com.inovacerto.apiinovavendas.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inovacerto.apiinovavendas.models.WarehouseModel;
import com.inovacerto.apiinovavendas.requests.WarehouseRequest;
import com.inovacerto.apiinovavendas.respositories.WarehouseRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class WarehouseService {

    final WarehouseRepository repository;

    public WarehouseService(WarehouseRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Object save(WarehouseModel model) {
        return repository.save(model);
    }

    public Object validateStore(@Valid WarehouseRequest request) {
        if (repository.existsByName(request.getName())) {
            return "Armazém já cadastrada!";
        }

        return true;
    }

    public Page<WarehouseModel> search(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<WarehouseModel> findById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public void delete(WarehouseModel model) {
        repository.delete(model);
    }

}
