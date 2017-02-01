package com.sovesting;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Employee {

    private String id;
    private BigDecimal totalGains;
    private BigDecimal totalSales;

    public Employee(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public BigDecimal getTotalGains() {
        return this.totalGains.setScale(2, RoundingMode.HALF_UP);
    }

    public void setTotalGains(BigDecimal totalGains) {
        this.totalGains = totalGains;
    }

    public BigDecimal getTotalSales() {
        return this.totalSales.setScale(2, RoundingMode.HALF_UP);
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    // TODO: check for negative gains, display 0.00 if negative (need to check for negativies in GainCalculator)
    // TODO: modify for possible sales output
    public String render() {
        return this.id + "," + this.getTotalGains() + "," + this.getTotalSales();
    }

    @Override
    public String toString() {
        return "Employee{" + this.render() + '}';
    }
}
