package com.example.demo.controller;

//import com.example.demo.model.FutureValueRequest;
import com.example.demo.model.FutureValueRequest;
import com.example.demo.model.FutureValueResponse;
import com.example.demo.model.MutualFund;
import com.example.demo.service.CalculationService;
import com.example.demo.service.MutualFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mutualfunds")
public class MutualFundController {
    @Autowired
    private MutualFundService mutualFundService;

    @Autowired
    private CalculationService calculationService;

    @GetMapping
    public List<MutualFund> getAllMutualFunds() {
        return mutualFundService.getMutualFunds();
    }

    @PostMapping("/future-value")
    public FutureValueResponse calculateFutureValue(@RequestBody FutureValueRequest request) {
        FutureValueResponse futureValue = calculationService.calculateFutureValue(
                request.getTicker(),
                request.getInitialInvestment(),
                request.getTimeInYears()
        );
        return futureValue;

        // Return additional details if needed
//        return new FutureValueResponse(
//                request.getInitialInvestment(),
//                request.getTimeInYears(),
//                calculationService.getRiskFreeRate(),
//                request.getTicker(),
//                calculationService.getBeta(request.getTicker()),
//                calculationService.getMarketReturnRate(),
//                futureValue
//        );
//    @GetMapping("/future-value")
//    public double calculateFutureValue(
//            @RequestParam String ticker,
//            @RequestParam double initialInvestment,
//            @RequestParam int timeInYears
//    ) {
//        return calculationService.calculateFutureValue(ticker, initialInvestment, timeInYears);
//    }


//    @PostMapping("/future-value")
//    public FutureValueResponse calculateFutureValue(
//            @RequestParam String ticker,
//            @RequestParam double initialInvestment,
//            @RequestParam int timeInYears) {
//        return calculationService.calculateFutureValue(ticker, initialInvestment, timeInYears);
    }

//    public double calculateFutureValue(@RequestBody FutureValueRequest futureValueRequest) {
//        return calculationService.calculateFutureValue(
//                futureValueRequest.getTicker(),
//                futureValueRequest.getInitialInvestment(),
//                futureValueRequest.getTimeInYears()
//        );
//    }
}
