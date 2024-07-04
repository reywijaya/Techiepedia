package com.enigmacamp.tokonyadia.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductResponse {

    @NotBlank
    private String name;
    @NotBlank
    private Integer price, stock;
}