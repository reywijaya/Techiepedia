package com.enigmacamp.tokonyadia.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name, email, phone, address;
    private Date birthDate;
    private Boolean deleted = Boolean.FALSE;
}