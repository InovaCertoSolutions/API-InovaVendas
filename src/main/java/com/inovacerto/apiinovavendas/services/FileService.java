package com.inovacerto.apiinovavendas.services;

import org.springframework.stereotype.Service;

import com.inovacerto.apiinovavendas.respositories.FileRepository;

@Service
public class FileService {

    final FileRepository repository;

    public FileService(FileRepository repository) {
        this.repository = repository;
    }

}
