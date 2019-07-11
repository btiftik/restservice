package com.company.lendingbackend.service;

import com.company.lendingbackend.dto.ApplicationDto;
import com.company.lendingbackend.entity.Application;
import com.company.lendingbackend.entity.ApplicationRepository;
import com.company.lendingbackend.entity.Loan;
import com.company.lendingbackend.entity.LoanRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoanServiceTest {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private LoanService loanService;

    private static final String VALID_IP_ADDRESS_1 = "34.22.3.7";

    @Test
    public void shouldCreateLoans() {
        ApplicationDto applicationDto = new ApplicationDto(new BigDecimal(456343), 2, LocalDateTime.now(), "0.22.0.0");
        Loan loan = loanService.saveLoan(applicationDto);
        List<Loan> loanList = loanRepository.findByIpAddress("0.22.0.0");

        Assert.assertEquals(loan.getAmount(), loanList.get(0).getAmount().setScale(0, RoundingMode.HALF_UP));
        Assert.assertEquals(loan.getTerm(), loanList.get(0).getTerm());
        Assert.assertEquals(loan.getIpAddress(), loanList.get(0).getIpAddress());
    }

    @Test
    public void shouldCreateApplication() {
        ApplicationDto applicationDto = new ApplicationDto(new BigDecimal(456343), 2, LocalDateTime.now(), VALID_IP_ADDRESS_1);
        Application application = loanService.saveApplication(applicationDto);
        List<Application> applicationList = applicationRepository.findByIpAddress(VALID_IP_ADDRESS_1);

        Assert.assertEquals(application.getApplicationSubmissionDate(), applicationList.get(0).getApplicationSubmissionDate());
        Assert.assertEquals(application.getIpAddress(), applicationList.get(0).getIpAddress());
    }
}