package com.inovacerto.apiinovavendas.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inovacerto.apiinovavendas.models.InventoryManagementModel;
import com.inovacerto.apiinovavendas.models.ProductModel;
import com.inovacerto.apiinovavendas.models.WarehouseModel;
import com.inovacerto.apiinovavendas.requests.InventoryManagementRequest;
import com.inovacerto.apiinovavendas.respositories.InventoryManagementRepository;
import com.inovacerto.apiinovavendas.respositories.ProductRepository;
import com.inovacerto.apiinovavendas.respositories.WarehouseRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class InventoryManagementService {

    final InventoryManagementRepository repository;
    final ProductRepository productRepository;
    final WarehouseRepository warehouseRepository;

    public InventoryManagementService(InventoryManagementRepository repository, ProductRepository productRepository, WarehouseRepository warehouseRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional
    public InventoryManagementModel movimentarEstoque(InventoryManagementRequest request) {
        
        InventoryManagementModel model = new InventoryManagementModel();

        ProductModel product = this.productRepository.findById(request.getProduct()).get();
        model.setProduct(product);

        WarehouseModel warehouse = this.warehouseRepository.findById(request.getWarehouse()).get();
        model.setWarehouse(warehouse);

        var type = request.getManagementType();

        model.setManagementType(type);
        model.setQuantity(request.getQuantity());
        model.setUnitCost(request.getUnitCost());
        
        Integer newStockQuantity = 0;
        if (type.equals("ENTRADA")) {
            product.setMediumCost((product.getCost() + request.getUnitCost()) / 2);
            if (product.getStockQuantity() == null) {
                newStockQuantity = request.getQuantity();
            } else {
                newStockQuantity = request.getQuantity() + product.getStockQuantity();
            }
        } else {
            newStockQuantity = product.getStockQuantity() - request.getQuantity();
        }

        product.setStockQuantity(newStockQuantity);
        product.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        this.productRepository.save(product);

        return model;
    }

    @Transactional
    public Object save(@Valid InventoryManagementRequest request) {
        InventoryManagementModel model = this.movimentarEstoque(request);
        model.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return repository.save(model);
    }

    public Object validateStore(@Valid InventoryManagementRequest request) {
        var type = request.getManagementType();
        if (!type.equals("ENTRADA")) {
            ProductModel product = this.productRepository.findById(request.getProduct()).get();
            if (product.getStockQuantity() == null) {
                return "Nao ha estoque nesse produto";
            }

            if (request.getQuantity() > product.getStockQuantity()) {
                return "Não há quantidade suficiente em estoque";
            }
        }

        return true;
    }

    public Page<InventoryManagementModel> search(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<InventoryManagementModel> findById(UUID id) {
        return repository.findById(id);
    }

}
