package com.example.customer_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String name;
  
    @Column(unique = true, nullable = false)
    private String email;
  
    @Column(nullable = false)
    private String phone;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}