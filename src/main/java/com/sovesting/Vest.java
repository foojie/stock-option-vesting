package com.sovesting;

import java.math.BigDecimal;

public class Vest extends Transaction {

    private BigDecimal units;
    private BigDecimal grantPrice; // TODO: use Price wrapper class?

    // 5 fields
    public Vest(String[] fields) {
        this.init(fields);
        this.units = new BigDecimal(fields[3]);
        this.grantPrice = new BigDecimal(fields[4]); // TODO: move to Price wrapper?
    }

    public BigDecimal getUnits() {
        return this.units;
    }

    public void setUnits(BigDecimal units) {
        this.units = units;
    }

    public BigDecimal getGrantPrice() {
        return this.grantPrice;
    }

    @Override
    public String toString() {
        return super.toString() + ", units=" + units + ", grantPrice=" + grantPrice + "\n";
    }
}
