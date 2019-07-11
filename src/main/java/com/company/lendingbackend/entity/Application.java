package com.company.lendingbackend.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "APPLICATION")
@Data
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "APPLICATION_ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "APPLICATION_DATE", nullable = false)
    private LocalDateTime applicationSubmissionDate;

    @Column(name = "IP_ADDRESS", nullable = false)
    private String ipAddress;
}