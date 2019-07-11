package com.company.lendingbackend.entity;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApplicationRepository extends CrudRepository<Application, Long> {
    List<Application> findByIpAddress(String ipAddress);

}