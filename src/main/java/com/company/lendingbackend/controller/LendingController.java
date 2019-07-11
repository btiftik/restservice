package com.company.lendingbackend.controller;


import com.company.lendingbackend.dto.ApplicationDto;
import com.company.lendingbackend.dto.LoanDto;
import com.company.lendingbackend.entity.Loan;
import com.company.lendingbackend.exception.HighRiskException;
import com.company.lendingbackend.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.URI;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class LendingController {

    @Autowired
    private Clock clock;

    @Autowired
    private LoanService loanService;

    @GetMapping("/history")
    public List<Loan> history(HttpServletRequest request) {
        return loanService.getHistory(request.getRemoteAddr());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> loan(@RequestBody LoanDto loanDto, HttpServletRequest request) {
        BigDecimal amount = loanDto.getAmount();
        int term = loanDto.getTerm();

        ApplicationDto applicationDto = new ApplicationDto(amount, term, LocalDateTime.now(getClock()), request.getRemoteAddr());
        Loan loan;
        Long id;
        try {
            loan = loanService.createLoan(applicationDto);
            id = loan.getId();
        } catch (HighRiskException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);

        }

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    private Clock getClock() {
        return clock;
    }
}