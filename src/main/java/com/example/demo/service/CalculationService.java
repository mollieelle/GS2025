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
            double beta = fetchBeta(ticker);
            double marketReturnRate = fetchMarketReturnRate();


        // Calculate rate
        double rate = (RISK_FREE_RATE / 100) + beta * (marketReturnRate - RISK_FREE_RATE / 100);

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

    double fetchBeta(String ticker) {
        String url = String.format(BETA_API, ticker);
        String response = restTemplate.getForObject(url, String.class);

        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode betaNode = rootNode.get("data");

            if (betaNode == null || betaNode.isNull()) {
                throw new RuntimeException("Beta value is missing in the API response");
            }

            return betaNode.asDouble();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse beta from API response", e);
        }
    }

    double fetchMarketReturnRate() {
        String response = restTemplate.getForObject(MARKET_RETURN_API, String.class);

        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode observations = rootNode.get("observations");

            if (observations == null || !observations.isArray()) {
                throw new RuntimeException("Observations node is missing or invalid in the API response.");
            }

            double firstValue = 0;
            double lastValue = 0;

            for (JsonNode observation : observations) {
                if (observation.has("date") && observation.has("value") && !observation.get("value").isNull()) {
                    String date = observation.get("date").asText();
                    double value = observation.get("value").asDouble(0.0);

                    if ("2024-01-02".equals(date)) {
                        firstValue = value;
                    } else if ("2024-12-31".equals(date)) {
                        lastValue = value;
                    }
                }
            }

            if (firstValue == 0 || lastValue == 0) {
                throw new RuntimeException("Failed to retrieve market return rate values.");
            }

            return (lastValue - firstValue) / firstValue;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse market return rate from API response", e);
        }
    }
}
