package com.enigmacamp.tokonyadia.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequest {

    @NotBlank
    private String name;
    @NotBlank
    private Integer price, stock;
}