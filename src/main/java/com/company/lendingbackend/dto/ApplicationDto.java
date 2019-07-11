package com.company.lendingbackend.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ApplicationDto {
    private LocalDateTime applicationSubmissionDate;
    private String ipAddress;
    private BigDecimal amount;
    private int term;

    public ApplicationDto(BigDecimal amount, int term, LocalDateTime localDateTime, String ipAddress) {
        this.amount = amount;
        this.term = term;
        applicationSubmissionDate = localDateTime;
        this.ipAddress = ipAddress;
    }
}