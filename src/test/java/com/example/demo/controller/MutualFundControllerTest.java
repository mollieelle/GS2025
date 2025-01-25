// Test for MutualFundController
package com.example.demo.controller;

import com.example.demo.model.FutureValueRequest;
import com.example.demo.model.FutureValueResponse;
import com.example.demo.model.MutualFund;
import com.example.demo.service.CalculationService;
import com.example.demo.service.MutualFundService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class MutualFundControllerTest {

    @Mock
    private MutualFundService mutualFundService;

    @Mock
    private CalculationService calculationService;

    @InjectMocks
    private MutualFundController mutualFundController;

    private MockMvc mockMvc;

    public MutualFundControllerTest() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mutualFundController).build();
    }

    @Test
    void testGetAllMutualFunds() throws Exception {
        List<MutualFund> mockFunds = Arrays.asList(
                new MutualFund("DYNF", "iShares US Equity Factor Rotation Active ETF"),
                new MutualFund("SPHB", "Invesco S&P 500®")
        );

        when(mutualFundService.getMutualFunds()).thenReturn(mockFunds);

        mockMvc.perform(get("/api/mutualfunds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ticker").value("FXAIX"))
                .andExpect(jsonPath("$[1].ticker").value("VFIAX"))
                .andDo(print());
    }

    @Test
    void testCalculateFutureValue() throws Exception {
        FutureValueRequest request = new FutureValueRequest("AAPL", 1000, 5);
        FutureValueResponse response = new FutureValueResponse();
        response.setTotalBalance(1500);

        when(calculationService.calculateFutureValue("AAPL", 1000, 5)).thenReturn(response);

        mockMvc.perform(post("/api/mutualfunds/future-value")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ticker\":\"AAPL\",\"initialInvestment\":1000,\"timeInYears\":5}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalBalance").value(1500))
                .andDo(print());
    }
}