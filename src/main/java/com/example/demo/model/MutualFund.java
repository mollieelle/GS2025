package com.example.demo.model;

public class MutualFund {
    private String ticker;
    private String name;
    private String type;

    public MutualFund(String ticker, String name, String type) {
        this.ticker = ticker;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
