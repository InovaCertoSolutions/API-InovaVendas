package com.inovacerto.apiinovavendas.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
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

import com.inovacerto.apiinovavendas.models.FileModel;
import com.inovacerto.apiinovavendas.requests.FileRequest;
import com.inovacerto.apiinovavendas.services.FileService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/files")
public class FileController {

    final FileService service;

    public FileController(FileService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<FileModel>> search () {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.search());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Object> searchById (@PathVariable(value = "uuid") UUID id) {
        Optional<FileModel> search = this.service.findById(id);
        if (!search.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arquivo não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(search.get());
    }

    @PostMapping
    public ResponseEntity<Object> store(@RequestBody @Valid FileRequest request) {
        var validation = this.service.validateStore(request);
        if (validation instanceof String) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(validation);
        }
        
        var model = new FileModel();
        BeanUtils.copyProperties(request, model);
        model.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(model));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Object> remove(@PathVariable(value = "uuid") UUID id) {
        Optional<FileModel> search = this.service.findById(id);
        if (!search.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arquivo não encontrado");
        }
        service.delete(search.get());
        return ResponseEntity.status(HttpStatus.OK).body("Removido com sucesso!");
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Object> update(@PathVariable(value = "uuid") UUID id, @RequestBody @Valid FileRequest request) {
        Optional<FileModel> search = this.service.findById(id);
        if (!search.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arquivo não encontrado");
        }

        var model = search.get();
        model.setName(request.getName());
        model.setUrl(request.getUrl());
        model.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.OK).body(this.service.save(model));
    }

}
