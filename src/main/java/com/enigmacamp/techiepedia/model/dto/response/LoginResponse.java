package com.enigmacamp.techiepedia.model.dto.response;

import com.enigmacamp.techiepedia.model.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    private Role.ERole role;
}
