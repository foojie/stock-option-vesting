package com.sovesting;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Employee {

    private String id;
    private BigDecimal totalGains;
    private BigDecimal totalSales;
    private int salesCount;

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

    public int getSalesCount() {
        return this.salesCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
    }

    public String render() {
        String sales = this.salesCount > 0 ? "," + this.getTotalSales() : "";
        return this.id + "," + this.getTotalGains() + sales;
    }

    @Override
    public String toString() {
        return "Employee{" + this.id + "," + this.getTotalGains() + "," + this.getTotalSales() + '}';
    }
}
