package com.bzu.currencyexchange;

public class ConversionResult {
    private double rate;
    private double convertedAmount;

    public ConversionResult(double rate, double convertedAmount) {
        this.rate = rate;
        this.convertedAmount = convertedAmount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }
}