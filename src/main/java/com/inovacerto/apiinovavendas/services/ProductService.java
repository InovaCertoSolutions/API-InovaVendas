package com.inovacerto.apiinovavendas.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inovacerto.apiinovavendas.models.ProductModel;
import com.inovacerto.apiinovavendas.requests.ProductRequest;
import com.inovacerto.apiinovavendas.respositories.ProductRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class ProductService {

    final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Object save(ProductModel model) {
        return repository.save(model);
    }

    public Object validateStore(@Valid ProductRequest request) {
        if (repository.existsByName(request.getName())) {
            return "Categoria já cadastrada!";
        }

        return true;
    }

    public Page<ProductModel> search(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<ProductModel> findById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public void delete(ProductModel model) {
        repository.delete(model);
    }

}
