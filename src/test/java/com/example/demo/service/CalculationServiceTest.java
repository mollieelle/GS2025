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
    void testFetchMarketReturnRate_MissingDates() {
        // Mock API response without the required last date ("2024-12-31")
        String marketReturnApiResponse = "{\"observations\":[{\"date\":\"2024-01-02\",\"value\":\"1000.00\"}]}";

        // Mock the REST template to return the above response
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(marketReturnApiResponse);

        // Use actual ObjectMapper for parsing
        CalculationService calculationServiceWithRealMapper = new CalculationService(restTemplate, new ObjectMapper());

        // Verify that the method throws an exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            calculationServiceWithRealMapper.fetchMarketReturnRate();
        });

        // Verify the exception message
        assertTrue(exception.getMessage().contains("Failed to parse market return rate from API response"));
    }

}
