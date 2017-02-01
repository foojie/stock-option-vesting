package com.sovesting;

import java.math.BigDecimal;

public class Perf extends Transaction {

    private BigDecimal multiplier;

    // 4 fields
    public Perf(String[] fields) {
        this.init(fields);
        this.multiplier = new BigDecimal(fields[3]);
    }

    public BigDecimal getMultiplier() {
        return this.multiplier;
    }
}
