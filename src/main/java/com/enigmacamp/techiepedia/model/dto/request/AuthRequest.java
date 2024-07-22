package com.enigmacamp.techiepedia.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest<T> {
    private String username;
    private String password;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}