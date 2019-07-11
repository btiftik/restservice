package com.company.lendingbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LoanDto {

    private BigDecimal amount;
    private int term;

}