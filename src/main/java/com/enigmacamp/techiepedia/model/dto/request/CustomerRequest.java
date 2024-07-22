package com.enigmacamp.techiepedia.model.dto.request;

import com.enigmacamp.techiepedia.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {
    private String id, name, email, phone, address;
    private Date birthDate;
    private User user;
}