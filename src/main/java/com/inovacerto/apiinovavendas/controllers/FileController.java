package com.inovacerto.apiinovavendas.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovacerto.apiinovavendas.services.FileService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/files")
public class FileController {

    final FileService service;
    
    public FileController(FileService service) {
        this.service = service;
    }

}
