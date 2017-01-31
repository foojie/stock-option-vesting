package com.sovesting;

public class Perf extends Transaction {

    private double multiplier; // may have to change to BigDecimal

    // 4 fields
    public Perf(String[] fields) {
        this.init(fields);
        this.multiplier = Double.valueOf(fields[3]);
    }

    public double getMultiplier() {
        return this.multiplier;
    }
}
