package com.inovacerto.apiinovavendas.requests;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductRequest {
    @NotBlank
    @Size(max = 500)
    private String name;
    
    @NotNull
    private UUID category;

    @NotNull
    private Float salePrice;

    @NotNull
    private Float cost;
}
