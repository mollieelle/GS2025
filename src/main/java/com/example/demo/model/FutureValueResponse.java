package com.example.demo.model;

public class FutureValueResponse {
    private String ticker;
    private double initialInvestment;
    private int timeHorizon;
    private double returnRate;
    private double riskFreeRate;
    private double mutualFundBeta;
    private double earnings;
    private double totalBalance;

    public FutureValueResponse() {}

    public String getTicker() { return ticker; }

    public void setTicker(String ticker) { this.ticker = ticker; }

    public double getInitialInvestment() {
        return initialInvestment;
    }

    public void setInitialInvestment(double initialInvestment) {
        this.initialInvestment = initialInvestment;
    }

    public int getTimeHorizon() {
        return timeHorizon;
    }

    public void setTimeHorizon(int timeHorizon) {
        this.timeHorizon = timeHorizon;
    }

    public double getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(double returnRate) {
        this.returnRate = returnRate;
    }

    public double getRiskFreeRate() {
        return riskFreeRate;
    }

    public void setRiskFreeRate(double riskFreeRate) {
        this.riskFreeRate = riskFreeRate;
    }

    public double getMutualFundBeta() {
        return mutualFundBeta;
    }

    public void setMutualFundBeta(double mutualFundBeta) {
        this.mutualFundBeta = mutualFundBeta;
    }

    public double getEarnings() {
        return earnings;
    }

    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }
}
