package com.inovacerto.apiinovavendas.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inovacerto.apiinovavendas.models.FileModel;
import com.inovacerto.apiinovavendas.requests.FileRequest;
import com.inovacerto.apiinovavendas.services.FileService;
import com.inovacerto.apiinovavendas.validations.FileValidation;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/files")
public class FileController {

    final FileService service;

    public FileController(FileService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> store(@RequestBody @Valid FileRequest request) {
        var validation = this.service.validar(request);
        if (validation instanceof String) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(validation);
        }
        
        var model = new FileModel();
        BeanUtils.copyProperties(request, model);
        model.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(model));
    }

}
