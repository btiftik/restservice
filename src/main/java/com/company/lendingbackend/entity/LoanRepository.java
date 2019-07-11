package com.company.lendingbackend.entity;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Long> {
    List<Loan> findByIpAddress(String ipAddress);

}