package com.example.demo.model;

public class FutureValueRequest {
    private String ticker;
    private double initialInvestment;
    private int timeInYears;

    public FutureValueRequest(String s, double v, int i) {
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getInitialInvestment() {
        return initialInvestment;
    }

    public void setInitialInvestment(double initialInvestment) {
        this.initialInvestment = initialInvestment;
    }

    public int getTimeInYears() {
        return timeInYears;
    }

    public void setTimeInYears(int timeInYears) {
        this.timeInYears = timeInYears;
    }
}
