package com.example.demo.service;

import com.example.demo.model.MutualFund;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MutualFundService {
    public List<MutualFund> getMutualFunds() {
        return Arrays.asList(
                new MutualFund("FXAIX", "Fidelity 500 Index Fund", "Tracks the S&P 500"),
                new MutualFund("VFIAX", "Vanguard 500 Index Fund", "Another S&P 500 tracker"),
                new MutualFund("SWPPX", "Schwab S&P 500 Index Fund", "Low-cost S&P 500 index fund")

        );
    }
}
