package com.enigmacamp.tokonyadia.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private String name, email, phone, address;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private Boolean deleted;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}