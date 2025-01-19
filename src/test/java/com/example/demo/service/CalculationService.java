package com.example.demo.service;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CalculationService {
    private static final double RISK_FREE_RATE = 4.57; // Hardcoded risk-free rate
    private static final String BETA_API = "https://api.newtonanalytics.com/stock-beta/?" +
            "ticker=%s&index=%5eGSPC&interval=1mo&observations=12";
    private static final String MARKET_RETURN_API = "https://api.stlouisfed.org/fred/series/observations?" +
            "series_id=SP500&api_key=d26079fc190512773ac705629a92f8ea&file_type=json";

    @Autowired
    public CalculationService(RestTemplate restTemplate) {

    }
    private final RestTemplate restTemplate = new RestTemplate();

    public double calculateFutureValue(
            String ticker,
            double initialInvestment,
            int timeInYears) throws JSONException {
        // Fetch beta for the mutual fund
        double beta = fetchBeta(ticker);

        // Fetch market return rate
        double marketReturnRate = fetchMarketReturnRate();

        // Calculate rate
        double rate = (RISK_FREE_RATE / 100) + ((marketReturnRate / 100) - (RISK_FREE_RATE / 100)) * beta;

        // Calculate future value
        return Math.round(initialInvestment * Math.exp(rate * timeInYears) * 100.0) / 100.0;
    }

    private double fetchBeta(String ticker) throws JSONException {
        String url = String.format(BETA_API, ticker);
        String response = restTemplate.getForObject(url, String.class);
        // Parse JSON response
        return Double.parseDouble(new org.json.JSONObject(response).get("data").toString());
    }

    private double fetchMarketReturnRate() throws JSONException {
        String response = restTemplate.getForObject(MARKET_RETURN_API, String.class);
        org.json.JSONObject jsonResponse = new org.json.JSONObject(response);
        org.json.JSONArray observations = jsonResponse.getJSONArray("observations");

        double firstValue = 0, lastValue = 0;
        for (int i = 0; i < observations.length(); i++) {
            org.json.JSONObject obj = observations.getJSONObject(i);
            String date = obj.getString("date");
            double value = obj.optDouble("value", Double.NaN);
            if (date.equals("2023-01-02")) firstValue = value;
            if (date.equals("2023-12-31")) lastValue = value;
        }

        return (lastValue - firstValue) / firstValue;
    }
}
