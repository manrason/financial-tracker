package com.manrason.financialtracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {

    private String name;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;

    private Double amount;

    private LocalDate creationDate;

    private String comments;
}
