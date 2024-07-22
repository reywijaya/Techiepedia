package com.enigmacamp.techiepedia.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "m_seller")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name, phone, email, address;
    private Boolean deleted;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}