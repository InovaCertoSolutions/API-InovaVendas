package com.inovacerto.apiinovavendas.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inovacerto.apiinovavendas.models.FileModel;
import com.inovacerto.apiinovavendas.requests.FileRequest;
import com.inovacerto.apiinovavendas.respositories.FileRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class FileService {

    final FileRepository repository;

    public FileService(FileRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Object save(FileModel model) {
        return repository.save(model);
    }

    public Object validateStore(@Valid FileRequest request) {
        if (repository.existsByUrl(request.getUrl())) {
            return "URL já cadastrada!";
        }

        return true;
    }

    public Page<FileModel> search(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<FileModel> findById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public void delete(FileModel model) {
        repository.delete(model);
    }

}
