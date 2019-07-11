package com.company.lendingbackend.testconfig;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@TestConfiguration
public class TestConfigTimeSuccess {

    @Bean
    @Primary
    public Clock successClock() {
        return Clock.fixed((Instant.parse("2018-08-19T16:45:42.00Z")), ZoneId.of("GMT"));
    }

}