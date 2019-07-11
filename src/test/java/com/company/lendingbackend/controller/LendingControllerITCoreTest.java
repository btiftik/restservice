package com.company.lendingbackend.controller;

import com.company.lendingbackend.LendingBackendApplication;
import com.company.lendingbackend.testconfig.TestConfigTimeSuccess;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = {LendingBackendApplication.class, TestConfigTimeSuccess.class})
public class LendingControllerITCoreTest extends LendingControllerITAbstract {

    @WithMockUser(username = "user", password = "password", roles = "USER")
    @Test
    public void shouldPostWithSuccess() throws Exception {

        mockMvc.perform(post("/")
                .with(LendingControllerITAbstract.remoteAddr(LendingControllerITAbstract.REMOTE_ADDRESS))
                .content(LendingControllerITAbstract.ACCEPTABLE_JSON)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/2"));
    }

    @WithMockUser(username = "user", password = "password", roles = "USER")
    @Test
    public void shouldFailWithBadRequest() throws Exception {

        String json = "{\"amount\":\"65344363\",\"term\":\"d7a3}";

        mockMvc.perform(post("/")
                .with(LendingControllerITAbstract.remoteAddr(LendingControllerITAbstract.REMOTE_ADDRESS))
                .content(json)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "user", password = "password", roles = "USER")
    @Test
    public void shouldPostFourTimesAndFail() throws Exception {

        mockMvc.perform(post("/")
                .with(LendingControllerITAbstract.remoteAddr(LendingControllerITAbstract.REMOTE_ADDRESS))
                .content(LendingControllerITAbstract.ACCEPTABLE_JSON)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/2"));

        mockMvc.perform(post("/")
                .with(LendingControllerITAbstract.remoteAddr(LendingControllerITAbstract.REMOTE_ADDRESS))
                .content(LendingControllerITAbstract.ACCEPTABLE_JSON)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/4"));

        mockMvc.perform(post("/")
                .with(LendingControllerITAbstract.remoteAddr(LendingControllerITAbstract.REMOTE_ADDRESS))
                .content(LendingControllerITAbstract.ACCEPTABLE_JSON)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/6"));

        mockMvc.perform(post("/")
                .with(LendingControllerITAbstract.remoteAddr(LendingControllerITAbstract.REMOTE_ADDRESS))
                .content(LendingControllerITAbstract.ACCEPTABLE_JSON)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Rejection: High Risk, Maximum Number of Applications Exceeded"));
    }

    @Test
    public void should_CreateNewMainLoan_ReturnJsonAndHttpStatus200() throws Exception {

        mockMvc.perform(get("/history")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("[]"));

    }
}