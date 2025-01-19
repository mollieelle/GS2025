package com.example.demo.service;

import com.example.demo.model.FutureValueResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class CalculationService {
    private static final double RISK_FREE_RATE = 4.61;
    private static final String BETA_API = "https://api.newtonanalytics.com/stock-beta/?" +
            "ticker=%s&index=%%5eGSPC&interval=1mo&observations=12";
    private static final String MARKET_RETURN_API = "https://api.stlouisfed.org/fred/series/observations?" +
            "series_id=SP500&api_key=d26079fc190512773ac705629a92f8ea&file_type=json";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public CalculationService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public FutureValueResponse calculateFutureValue(
            String ticker,
            double initialInvestment,
            int timeInYears) {
        // Fetch beta for the mutual fund
        double beta = fetchBeta(ticker);


        // Fetch market return rate
        double marketReturnRate = fetchMarketReturnRate();

        // Calculate rate
        double rate = (RISK_FREE_RATE / 100) + beta * (marketReturnRate  - RISK_FREE_RATE / 100);
        System.out.println(marketReturnRate);

        // Calculate future value
        double totalBalance = Math.round(initialInvestment * Math.exp(rate * timeInYears) * 100.0) / 100.0;

        // Calculate earnings
        double earnings = totalBalance - initialInvestment;

        // Populate the response object
        FutureValueResponse response = new FutureValueResponse();
        response.setInitialInvestment(initialInvestment);
        response.setTimeHorizon(timeInYears);
        response.setReturnRate(rate * 100); // Convert to percentage
        response.setRiskFreeRate(RISK_FREE_RATE);
        response.setMutualFundBeta(beta);
        response.setEarnings(earnings);
        response.setTotalBalance(totalBalance);

        return response;
    }


//    public double calculateFutureValue(
//            String ticker,
//            double initialInvestment,
//            int timeInYears) {
//        // Fetch beta for the mutual fund
//        double beta = fetchBeta(ticker);
//
//        // Fetch market return rate
//        double marketReturnRate = fetchMarketReturnRate();
//
//        // Calculate rate
//        double rate = (RISK_FREE_RATE / 100) + ((marketReturnRate / 100) - (RISK_FREE_RATE / 100)) * beta;
//
//        // Calculate future value
//        return Math.round(initialInvestment * Math.exp(rate * timeInYears) * 100.0) / 100.0;
//    }

    private double fetchBeta(String ticker) {
        String url = String.format(BETA_API, ticker);
        String response = restTemplate.getForObject(url, String.class);

        try {
            JsonNode rootNode = objectMapper.readTree(response);
            return rootNode.get("data").asDouble();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse beta from API response", e);
        }
    }

    private double fetchMarketReturnRate() {
        String response = restTemplate.getForObject(MARKET_RETURN_API, String.class);



        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode observations = rootNode.get("observations");

            double firstValue = 0, lastValue = 0;



            for (JsonNode observation : observations) {
                String date = observation.get("date").asText();
                double value = observation.get("value").asDouble(Double.NaN);


                double values = observation.has("value") && !observation.get("value").isNull()
                        ? observation.get("value").asDouble(0.0)
                        : 0.0;


                if ("2024-01-02".equals(date)) {
                    firstValue = values;
                } else if ("2024-12-31".equals(date)) {
                    lastValue = values;
                }
            }

//            System.out.println(firstValue);
//            System.out.println(lastValue);


            if (firstValue == 0 || lastValue == 0) {
                throw new RuntimeException("Failed to retrieve market return rate values.");
            }
//            System.out.println((lastValue - firstValue) / firstValue);


            return (lastValue - firstValue) / firstValue;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse market return rate from API response", e);
        }
    }


}
