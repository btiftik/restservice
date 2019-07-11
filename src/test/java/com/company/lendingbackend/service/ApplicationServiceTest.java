package com.company.lendingbackend.service;

import com.company.lendingbackend.dto.ApplicationDto;
import com.company.lendingbackend.entity.Application;
import com.company.lendingbackend.entity.ApplicationRepository;
import com.company.lendingbackend.exception.HighRiskException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ApplicationServiceTest {

    @MockBean
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationService applicationService;

    private ApplicationDto applicationDto;

    private static final String VALID_IP_ADDRESS_1 = "34.22.3.0";
    private static final String VALID_IP_ADDRESS_2 = "142.22.6.0";


    @Test
    public void shouldAcceptNumberOfIpAddress() throws HighRiskException {
        applicationDto = new ApplicationDto(new BigDecimal(100), 2, LocalDateTime.now(), VALID_IP_ADDRESS_1);

        List<Application> applicationList = new ArrayList<>();

        Application application1 = new Application();
        application1.setApplicationSubmissionDate(LocalDateTime.now());
        application1.setIpAddress(VALID_IP_ADDRESS_1);

        for (int i = 0; i < 3; i++) {
            applicationList.add(application1);

        }

        when(applicationRepository.findByIpAddress(anyString())).thenReturn(applicationList);

        applicationService.checkMaximumNumberOfApplicationsPerIp(applicationDto);
    }

    @Test(expected = HighRiskException.class)
    public void shouldRejectNumberOfIpAddress() throws HighRiskException {
        applicationDto = new ApplicationDto(new BigDecimal(100), 2, LocalDateTime.now(), VALID_IP_ADDRESS_2);

        List<Application> applicationList = new ArrayList<>();

        Application application1 = new Application();
        application1.setApplicationSubmissionDate(LocalDateTime.now());
        application1.setIpAddress(VALID_IP_ADDRESS_1);

        for (int i = 0; i < 4; i++) {
            applicationList.add(application1);

        }
        when(applicationRepository.findByIpAddress(anyString())).thenReturn(applicationList);

        applicationService.checkMaximumNumberOfApplicationsPerIp(applicationDto);

    }


    @Test
    public void shouldNotThrowTimeException() throws HighRiskException {
        applicationDto = new ApplicationDto(new BigDecimal(100), 2, LocalDateTime.now(), VALID_IP_ADDRESS_1);

        LocalDateTime localDatePass1 = LocalDateTime.of(2019, Month.JUNE, 15, 6, 0);
        LocalDateTime localDatePass2 = LocalDateTime.of(2019, Month.MAY, 11, 23, 15);
        applicationDto.setApplicationSubmissionDate(localDatePass1);
        ApplicationService.checkApplicationTime(applicationDto);
        applicationDto.setApplicationSubmissionDate(localDatePass2);
        ApplicationService.checkApplicationTime(applicationDto);
    }

    @Test(expected = HighRiskException.class)
    public void shouldThrowTimeException() throws HighRiskException {
        applicationDto = new ApplicationDto(new BigDecimal(100), 2, LocalDateTime.now(), VALID_IP_ADDRESS_1);

        LocalDateTime localDateFail1 = LocalDateTime.of(2011, Month.APRIL, 13, 0, 15);
        LocalDateTime localDateFail2 = LocalDateTime.of(2011, Month.APRIL, 13, 5, 59);
        applicationDto.setApplicationSubmissionDate(localDateFail1);
        ApplicationService.checkApplicationTime(applicationDto);
        applicationDto.setApplicationSubmissionDate(localDateFail2);
        ApplicationService.checkApplicationTime(applicationDto);
    }
}