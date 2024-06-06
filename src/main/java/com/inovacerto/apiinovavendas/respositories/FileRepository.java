package com.inovacerto.apiinovavendas.respositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inovacerto.apiinovavendas.models.FileModel;

@Repository
public interface FileRepository extends JpaRepository<FileModel, UUID> {

    boolean existsByUrl(String url);

}
