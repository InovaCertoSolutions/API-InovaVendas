package com.inovacerto.apiinovavendas.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inovacerto.apiinovavendas.models.ClientModel;
import com.inovacerto.apiinovavendas.requests.ClientRequest;
import com.inovacerto.apiinovavendas.respositories.ClientRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class ClientService {

    final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Object save(ClientModel model) {
        return repository.save(model);
    }

    public Object validateStore(@Valid ClientRequest request) {
        if (repository.existsByPhone(request.getPhone())) {
            return "Cliente já cadastrado!";
        }

        return true;
    }

    public Page<ClientModel> search(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<ClientModel> findById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public void delete(ClientModel model) {
        repository.delete(model);
    }

}
