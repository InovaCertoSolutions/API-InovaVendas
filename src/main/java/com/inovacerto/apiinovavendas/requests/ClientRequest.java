package com.inovacerto.apiinovavendas.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClientRequest {
    
    @NotNull
    private String name;

    @NotNull
    private String phone;

    private String email;
    
}
