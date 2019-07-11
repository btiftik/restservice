package com.company.lendingbackend.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "LOAN")
@Data
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LOAN_ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @Column(name = "TERM", nullable = false)
    private int term;

    @Column(name = "IP_ADDRESS", nullable = false)
    private String ipAddress;
}