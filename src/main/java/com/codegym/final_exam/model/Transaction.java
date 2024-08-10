package com.codegym.final_exam.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionCode;

    private LocalDate transactionDate;

    private String typeService;

    private double price;

    private double area;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
