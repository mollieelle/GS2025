// Test for CalculationService
package com.example.demo.service;

import com.example.demo.model.FutureValueResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalculationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private CalculationService calculationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchMarketReturnRate() throws Exception {
        String marketReturnApiResponse = "{\"observations\":[{\"date\":\"2024-01-02\",\"value\":100},{\"date\":\"2024-12-31\",\"value\":110}]}";

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(marketReturnApiResponse);

        // Use actual ObjectMapper for parsing
        CalculationService calculationServiceWithRealMapper = new CalculationService(restTemplate, new ObjectMapper());

        double marketReturnRate = calculationServiceWithRealMapper.fetchMarketReturnRate();
        assertEquals(0.1, marketReturnRate);
    }

    @Test
    void testFetchBeta() throws Exception {
        String ticker = "AAPL";
        String betaApiResponse = "{\"data\": 1.2}";

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(betaApiResponse);

        // Use actual ObjectMapper for parsing
        CalculationService calculationServiceWithRealMapper = new CalculationService(restTemplate, new ObjectMapper());

        double beta = calculationServiceWithRealMapper.fetchBeta(ticker);
        assertEquals(1.2, beta);
    }

    @Test
    void testCalculateFutureValue() throws Exception {
        String ticker = "AAPL";
        double initialInvestment = 1000;
        int timeInYears = 5;

        // Adjusting mocked response to match expected structure
        String mockedApiResponse = "{\"data\": {\"observations\": [1.2]}}";

        // Mocking the API response correctly
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(mockedApiResponse);

        // Use actual ObjectMapper for parsing
        CalculationService calculationServiceWithRealMapper = new CalculationService(restTemplate, new ObjectMapper());

        FutureValueResponse response = calculationServiceWithRealMapper.calculateFutureValue(ticker, initialInvestment, timeInYears);

        assertNotNull(response);
        assertEquals(ticker, response.getTicker());
        assertEquals(initialInvestment, response.getInitialInvestment());
        assertEquals(timeInYears, response.getTimeHorizon());
    }


}