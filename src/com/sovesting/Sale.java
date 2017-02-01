package com.sovesting;

import java.math.BigDecimal;

public class Sale extends Transaction {

    private BigDecimal units;
    private BigDecimal marketPrice; // TODO: use Price wrapper?

    // 5 fields
    public Sale(String[] fields) {
        this.init(fields);
        this.units = new BigDecimal(fields[3]);
        this.marketPrice = new BigDecimal(fields[4]); // TODO: parse BigDecimal into Price wrapper?
    }

    public BigDecimal getUnits() {
        return this.units;
    }

    public BigDecimal getMarketPrice() {
        return this.marketPrice;
    }
}
