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

import com.inovacerto.apiinovavendas.models.PaymentMethodModel;
import com.inovacerto.apiinovavendas.requests.PaymentMethodRequest;
import com.inovacerto.apiinovavendas.services.PaymentMethodService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/payment_methods")
public class PaymentMethodController {

    final PaymentMethodService service;

    public PaymentMethodController(PaymentMethodService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<PaymentMethodModel>> search (@PageableDefault(page = 0, size = 10, sort = "uuid", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.search(pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Object> searchById (@PathVariable(value = "uuid") UUID id) {
        Optional<PaymentMethodModel> search = this.service.findById(id);
        if (!search.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Forma de Pagamento não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(search.get());
    }

    @PostMapping
    public ResponseEntity<Object> store(@RequestBody @Valid PaymentMethodRequest request) {
        var validation = this.service.validateStore(request);
        if (validation instanceof String) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(validation);
        }
        
        var model = new PaymentMethodModel();
        BeanUtils.copyProperties(request, model);
        model.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(model));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Object> remove(@PathVariable(value = "uuid") UUID id) {
        Optional<PaymentMethodModel> search = this.service.findById(id);
        if (!search.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Forma de Pagamento não encontrada");
        }
        service.delete(search.get());
        return ResponseEntity.status(HttpStatus.OK).body("Removido com sucesso!");
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Object> update(@PathVariable(value = "uuid") UUID id, @RequestBody @Valid PaymentMethodRequest request) {
        Optional<PaymentMethodModel> search = this.service.findById(id);
        if (!search.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Forma de Pagamento não encontrada");
        }

        var model = search.get();
        model.setName(request.getName());
        model.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        
        return ResponseEntity.status(HttpStatus.OK).body(this.service.save(model));
    }

}
