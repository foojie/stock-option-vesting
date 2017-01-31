package com.sovesting;

public class Vest extends Transaction {

    private int units;
    private double grantPrice; // TODO: use Price wrapper class with BigDecimal

    // 5 fields
    public Vest(String[] fields) {
        this.init(fields);
        this.units = Integer.valueOf(fields[3]);
        this.grantPrice = Double.valueOf(fields[4]); // TODO: parse BigDecimal into Price wrapper
    }

    public int getUnits() {
        return this.units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public double getGrantPrice() {
        return this.grantPrice;
    }

    @Override
    public String toString() {
        return super.toString() + ", units=" + units + ", grantPrice=" + grantPrice + "\n";
    }
}
