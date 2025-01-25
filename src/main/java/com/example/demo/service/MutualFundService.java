package com.example.demo.service;

import com.example.demo.model.MutualFund;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MutualFundService {
    public List<MutualFund> getMutualFunds() {
        return Arrays.asList(
                new MutualFund("DYNF", "iShares US Equity Factor Rotation Active ETF"),
                new MutualFund("SPHB", "Invesco S&P 500®"),
                new MutualFund("SPY", "SPDR® S&P 500 ETF Trust"),
                new MutualFund("IWM", "iShares Russell 2000 ETF")
        );
    }
}
