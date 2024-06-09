package com.inovacerto.apiinovavendas.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inovacerto.apiinovavendas.models.PaymentMethodModel;
import com.inovacerto.apiinovavendas.requests.PaymentMethodRequest;
import com.inovacerto.apiinovavendas.respositories.PaymentMethodRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class PaymentMethodService {

    final PaymentMethodRepository repository;

    public PaymentMethodService(PaymentMethodRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Object save(PaymentMethodModel model) {
        return repository.save(model);
    }

    public Object validateStore(@Valid PaymentMethodRequest request) {
        if (repository.existsByName(request.getName())) {
            return "Forma de Pagamento já cadastrada!";
        }

        return true;
    }

    public Page<PaymentMethodModel> search(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<PaymentMethodModel> findById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public void delete(PaymentMethodModel model) {
        repository.delete(model);
    }

}
