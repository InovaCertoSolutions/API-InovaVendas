package com.inovacerto.apiinovavendas.services;

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

    public Object validar(@Valid FileRequest request) {
        if (repository.existsByUrl(request.getUrl())) {
            return "URL já cadastrada";
        }

        return true;
    }

}
