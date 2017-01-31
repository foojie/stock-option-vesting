package com.sovesting;

public class Employee {

    private String id;
    private double totalGains;
    private double totalSales;

    public Employee(String id) {
        this.id = id;
    }

    public double getTotalGains() {
        return this.totalGains;
    }

    public void setTotalGains(double totalGains) {
        this.totalGains = totalGains;
    }

    public double getTotalSales() {
        return this.totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    // TODO: check for negative gains, display 0.00 if negative (might need to check for negativies in GainCalculator)
    // TODO: modify for possible sales output
    public String render() {
        return this.id + "," + this.totalGains + "," + this.totalSales;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + '}';
    }
}
