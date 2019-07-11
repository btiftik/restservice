package com.company.lendingbackend.service;

import com.company.lendingbackend.dto.ApplicationDto;
import com.company.lendingbackend.entity.Application;
import com.company.lendingbackend.entity.ApplicationRepository;
import com.company.lendingbackend.entity.Loan;
import com.company.lendingbackend.entity.LoanRepository;
import com.company.lendingbackend.exception.HighRiskException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Loan createLoan(ApplicationDto applicationDto) throws HighRiskException {
        Application application = saveApplication(applicationDto);
        riskAssessment(applicationDto);
        applicationRepository.save(application);
        return saveLoan(applicationDto);
    }

    Loan saveLoan(ApplicationDto applicationDto) {
        return loanRepository.save(convertApplicationDtoToLoan(applicationDto));
    }

    private void riskAssessment(ApplicationDto applicationDto) throws HighRiskException {
        ApplicationService.checkApplicationTime(applicationDto);
        applicationService.checkMaximumNumberOfApplicationsPerIp(applicationDto);
    }


    Application saveApplication(ApplicationDto applicationDto) {
        return applicationRepository.save(convertApplicationDtoToEntity(applicationDto));
    }

    private Loan convertApplicationDtoToLoan(ApplicationDto applicationDto) {
        Loan loan = modelMapper.map(applicationDto, Loan.class);
        loan.setAmount(applicationDto.getAmount());
        loan.setTerm(applicationDto.getTerm());

        loan.setIpAddress(applicationDto.getIpAddress());
        return loan;
    }

    private Application convertApplicationDtoToEntity(ApplicationDto applicationDto) {
        Application application = modelMapper.map(applicationDto, Application.class);
        application.setApplicationSubmissionDate(applicationDto.getApplicationSubmissionDate());

        application.setIpAddress(applicationDto.getIpAddress());
        return application;
    }

    public List<Loan> getHistory(String ipAddress) {
        return loanRepository.findByIpAddress(ipAddress);
    }
}