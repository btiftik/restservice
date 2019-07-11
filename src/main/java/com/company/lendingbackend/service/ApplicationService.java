package com.company.lendingbackend.service;

import com.company.lendingbackend.dto.ApplicationDto;
import com.company.lendingbackend.entity.Application;
import com.company.lendingbackend.entity.ApplicationRepository;
import com.company.lendingbackend.exception.HighRiskException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    void checkMaximumNumberOfApplicationsPerIp(ApplicationDto applicationDto) throws HighRiskException {
        List<Application> applicationList = applicationRepository.findByIpAddress(applicationDto.getIpAddress());
        if (applicationList.size() > 3) {
            throw new HighRiskException("Rejection: High Risk, Maximum Number of Applications Exceeded");
        }
    }

    static void checkApplicationTime(ApplicationDto applicationDto) throws HighRiskException {
        if (applicationDto.getApplicationSubmissionDate().getHour() < 6) {
            throw new HighRiskException("Rejection: High Risk, Application submitted between 00:00 and 06:00");
        }
    }

}