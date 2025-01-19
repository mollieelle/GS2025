package com.example.demo.model;

public class MutualFund {
    private String ticker;
    private String name;
    private String description;

    public MutualFund(String ticker, String name, String description) {
        this.ticker = ticker;
        this.name = name;
        this.description = description;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
