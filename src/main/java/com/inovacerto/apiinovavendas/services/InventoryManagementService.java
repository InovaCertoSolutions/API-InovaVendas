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
    public InventoryManagementModel movimentarEstoque(InventoryManagementRequest movimentacao) {
        
        InventoryManagementModel model = new InventoryManagementModel();

        ProductModel product = this.productRepository.findById(movimentacao.getProduct()).get();
        model.setProduct(product);

        WarehouseModel warehouse = this.warehouseRepository.findById(movimentacao.getWarehouse()).get();
        model.setWarehouse(warehouse);

        // TODO: PRECISA SETAR:
        // managementType
        // quantity
        // unitCost
        // creationDate

        var newStockQuantity = product.getStockQuantity();
        var type = movimentacao.getManagementType();
        

        if (type.equals("ENTRADA")) {
            newStockQuantity += quantity;
        } else if (type.equals("SAIDA") || type.equals("VENDA")) {
            newStockQuantity -= quantity;
        }

        product.setStockQuantity(newStockQuantity);
        product.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        this.productRepository.save(product);

        return movimentacao;
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

    @Transactional
    public void delete(InventoryManagementModel model) {
        repository.delete(model);
    }

}
