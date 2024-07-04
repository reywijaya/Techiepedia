package com.enigmacamp.tokonyadia.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Integer price, stock;
    private Boolean deleted = Boolean.FALSE;
}