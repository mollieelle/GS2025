package com.example.demo.controller;

import com.example.demo.model.FutureValueRequest;
import com.example.demo.model.FutureValueResponse;
import com.example.demo.model.MutualFund;
import com.example.demo.service.CalculationService;
import com.example.demo.service.MutualFundService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mutualfunds")
public class MutualFundController {

    private final MutualFundService mutualFundService;
    private final CalculationService calculationService;

    public MutualFundController(MutualFundService mutualFundService, CalculationService calculationService) {
        this.mutualFundService = mutualFundService;
        this.calculationService = calculationService;
    }

    @GetMapping
    public List<MutualFund> getAllMutualFunds() {
        return mutualFundService.getMutualFunds();
    }

    @PostMapping("/future-value")
    public FutureValueResponse calculateFutureValue(@RequestBody FutureValueRequest request) {
        return calculationService.calculateFutureValue(
                request.getTicker(),
                request.getInitialInvestment(),
                request.getTimeInYears()
        );
    }
}
