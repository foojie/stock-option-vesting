package com.sovesting;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

    private String type;
    private String employeeId;
    private Date date; // could be: vest date, bonus date, sale date
    private BigDecimal units;
    private BigDecimal price;
    private BigDecimal multiplier;

    public Transaction(String[] fields) {

        this.type = fields[0];
        this.employeeId = fields[1];

        try {
            this.date = new SimpleDateFormat("yyyyMMdd").parse(fields[2]);
        } catch(ParseException e) {
            e.printStackTrace();
        }

        if(this.type.equals("VEST") || this.type.equals("SALE")) {
            this.units = new BigDecimal(fields[3]);
            this.price = new BigDecimal(fields[4]);

        } else if(this.type.equals("PERF")) {
            this.multiplier = new BigDecimal(fields[3]);
        } else {
            //throw new Exception("Invalid Transaction Type detected");
        }

    }

    public String getType() {
        return this.type;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public Date getDate() {
        return this.date;
    }

    public BigDecimal getUnits() {
        return this.units;
    }

    public void setUnits(BigDecimal units) {
        this.units = units;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public BigDecimal getMultiplier() {
        return this.multiplier;
    }

    @Override
    public String toString() {
        return "type=" + type + ", employeeId=" + employeeId + ", date=" + date + ", units=" + units + ", price=" + price + ", multiplier=" + multiplier;
    }

}
