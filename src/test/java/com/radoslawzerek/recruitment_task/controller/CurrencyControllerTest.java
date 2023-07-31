package com.radoslawzerek.recruitment_task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.radoslawzerek.recruitment_task.entity.RequestInfo;
import com.radoslawzerek.recruitment_task.exception.CurrencyNotFoundException;
import com.radoslawzerek.recruitment_task.model.CurrencyRequest;
import com.radoslawzerek.recruitment_task.model.CurrencyResponse;
import com.radoslawzerek.recruitment_task.service.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class CurrencyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private CurrencyController currencyController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(currencyController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetCurrentCurrencyValue_Success() throws Exception {
        CurrencyRequest request = new CurrencyRequest("EUR", "John Doe");
        CurrencyResponse response = new CurrencyResponse(1.2345);

        when(currencyService.getCurrentCurrencyValue(any(CurrencyRequest.class))).thenReturn(response);

        mockMvc.perform(post("/currencies/get-current-currency-value-command")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(response.getValue()));
    }

    @Test
    public void testGetCurrentCurrencyValue_Exception() throws Exception {
        CurrencyRequest request = new CurrencyRequest("EUR", "John Doe");

        // Use doThrow for checked exceptions
        when(currencyService.getCurrentCurrencyValue(any(CurrencyRequest.class))).thenThrow(new CurrencyNotFoundException("Currency not found"));

        mockMvc.perform(post("/currencies/get-current-currency-value-command")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllRequests_Success() throws Exception {
        List<RequestInfo> requests = Arrays.asList(
                new RequestInfo(1L, "dolar amerykański", "John Doe", LocalDateTime.now(), 1.2345),
                new RequestInfo(2L, "euro", "Jane Doe", LocalDateTime.now(), 6.7890)
        );

        when(currencyService.getAllRequests()).thenReturn(requests);

        mockMvc.perform(get("/currencies/requests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(requests.size()));
    }
}
