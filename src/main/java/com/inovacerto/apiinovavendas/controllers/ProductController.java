package com.inovacerto.apiinovavendas.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovacerto.apiinovavendas.models.ProductModel;
import com.inovacerto.apiinovavendas.requests.ProductRequest;
import com.inovacerto.apiinovavendas.services.ProductCategoryService;
import com.inovacerto.apiinovavendas.services.ProductService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/products")
public class ProductController {

    final ProductService service;
    final ProductCategoryService categoryService;

    public ProductController(ProductService service, ProductCategoryService categoryService) {
        this.service = service;
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductModel>> search (@PageableDefault(page = 0, size = 10, sort = "uuid", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.search(pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Object> searchById (@PathVariable(value = "uuid") UUID id) {
        Optional<ProductModel> search = this.service.findById(id);
        if (!search.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(search.get());
    }

    @PostMapping
    public ResponseEntity<Object> store(@RequestBody @Valid ProductRequest request) {
        var validation = this.service.validateStore(request);
        if (validation instanceof String) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(validation);
        }
        
        var model = new ProductModel();
        BeanUtils.copyProperties(request, model);
        model.setCategory(this.categoryService.findById(request.getCategory()).get());
        model.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(model));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Object> remove(@PathVariable(value = "uuid") UUID id) {
        Optional<ProductModel> search = this.service.findById(id);
        if (!search.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrada");
        }
        service.delete(search.get());
        return ResponseEntity.status(HttpStatus.OK).body("Removido com sucesso!");
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Object> update(@PathVariable(value = "uuid") UUID id, @RequestBody @Valid ProductRequest request) {
        Optional<ProductModel> search = this.service.findById(id);
        if (!search.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrada");
        }

        var model = search.get();
        model.setName(request.getName());
        model.setCost(request.getCost());
        model.setSalePrice(request.getSalePrice());
        model.setCategory(this.categoryService.findById(request.getCategory()).get());
        model.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        
        return ResponseEntity.status(HttpStatus.OK).body(this.service.save(model));
    }

}
