package com.enigmacamp.tokonyadia.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonResponse<T> {
    private Integer statusCode;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Optional<T> data;
}
