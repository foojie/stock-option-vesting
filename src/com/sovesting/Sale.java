package com.sovesting;

public class Sale extends Transaction {

    private int units;
    private double marketPrice; // TODO: use Price wrapper class with BigDecimal

    // 5 fields
    public Sale(String[] fields) {
        this.init(fields);
        this.units = Integer.valueOf(fields[3]);
        this.marketPrice = Double.valueOf(fields[4]); // TODO: parse BigDecimal into Price wrapper
    }
}
