package com.inovacerto.apiinovavendas.requests;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddToCartRequest {
    @NotNull
    private UUID product;

    @NotNull
    @PositiveOrZero
    private Integer quantity;
}
