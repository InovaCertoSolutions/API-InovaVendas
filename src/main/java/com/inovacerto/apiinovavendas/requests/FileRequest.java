package com.inovacerto.apiinovavendas.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileRequest {
    @NotBlank
    @Size(max = 500)
    private String name;

    @NotBlank
    @Size(max = 500)
    private String url;
}
