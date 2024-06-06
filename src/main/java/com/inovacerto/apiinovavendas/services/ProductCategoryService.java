package com.inovacerto.apiinovavendas.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inovacerto.apiinovavendas.models.ProductCategoryModel;
import com.inovacerto.apiinovavendas.requests.ProductCategoryRequest;
import com.inovacerto.apiinovavendas.respositories.ProductCategoryRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class ProductCategoryService {

    final ProductCategoryRepository repository;

    public ProductCategoryService(ProductCategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Object save(ProductCategoryModel model) {
        return repository.save(model);
    }

    public Object validateStore(@Valid ProductCategoryRequest request) {
        if (repository.existsByName(request.getName())) {
            return "Categoria já cadastrada!";
        }

        return true;
    }

    public Page<ProductCategoryModel> search(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<ProductCategoryModel> findById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public void delete(ProductCategoryModel model) {
        repository.delete(model);
    }

}
