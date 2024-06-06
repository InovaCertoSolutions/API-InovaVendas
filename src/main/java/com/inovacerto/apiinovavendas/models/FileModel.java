package com.inovacerto.apiinovavendas.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "files")
@Getter @Setter @NoArgsConstructor
public class FileModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @Column(length = 500, nullable = false)
    private String name;

    @Column(length = 500, nullable = false)
    private String url;

    @Column(nullable=false)
    private LocalDateTime creationDate;

    @Column(nullable=true)
    private LocalDateTime lastUpdateDate;

}
