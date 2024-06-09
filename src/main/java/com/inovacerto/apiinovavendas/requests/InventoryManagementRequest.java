package com.inovacerto.apiinovavendas.requests;

import java.util.UUID;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InventoryManagementRequest {

    @NotNull
    private UUID product;
    
    @Nullable
    private UUID warehouse;
    
    @NotNull
    @Size(max = 20)
    private String managementType;
    
    @NotNull
    @PositiveOrZero
    private Integer quantity;

    @NotNull
    @PositiveOrZero
    private Float unitCost;
}
