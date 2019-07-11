package com.company.lendingbackend.controller;

import com.company.lendingbackend.LendingBackendApplication;
import com.company.lendingbackend.testconfig.TestConfigTimeFail;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {LendingBackendApplication.class, TestConfigTimeFail.class})
public class LendingControllerITTestTimeFail extends LendingControllerITAbstract {

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void shouldPostAndFailWithTimeRiskError() throws Exception {

        mockMvc.perform(post("/")
                .with(LendingControllerITAbstract.remoteAddr(LendingControllerITAbstract.REMOTE_ADDRESS))
                .content(LendingControllerITAbstract.ACCEPTABLE_JSON)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Rejection: High Risk, Application submitted between 00:00 and 06:00"));
    }
}