package com.inovacerto.apiinovavendas.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovacerto.apiinovavendas.models.InventoryManagementModel;
import com.inovacerto.apiinovavendas.requests.InventoryManagementRequest;
import com.inovacerto.apiinovavendas.services.InventoryManagementService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/inventory_managements")
public class InventoryManagementController {

    final InventoryManagementService service;

    public InventoryManagementController(InventoryManagementService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<InventoryManagementModel>> search (@PageableDefault(page = 0, size = 10, sort = "uuid", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.search(pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Object> searchById (@PathVariable(value = "uuid") UUID id) {
        Optional<InventoryManagementModel> search = this.service.findById(id);
        if (!search.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movimentação de Estoque não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(search.get());
    }

    @PostMapping
    public ResponseEntity<Object> store(@RequestBody @Valid InventoryManagementRequest request) {
        var validation = this.service.validateStore(request);
        if (validation instanceof String) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(validation);
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(request));
    }

}
