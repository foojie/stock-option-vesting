package com.sovesting;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

    private Type type;
    private String employeeId;
    private Date date; // could be: vest date, bonus date, sale date
    private long units;
    private BigDecimal price;
    private BigDecimal multiplier;

    public Transaction(String[] fields) {

        String typeStr = fields[0];

        if(typeStr.equals("VEST")) {
            this.type = Type.VEST;
        } else if(typeStr.equals("PERF")) {
            this.type = Type.PERF;
        } else if(typeStr.equals("SALE")) {
            this.type = Type.SALE;
        }

        this.employeeId = fields[1];

        this.date = Transaction.parseDate(fields[2]);

        if(this.type == Type.VEST || this.type == Type.SALE) {
            this.units = new Long(fields[3]);
            this.price = new BigDecimal(fields[4]);

        } else if(this.type == Type.PERF) {
            this.multiplier = new BigDecimal(fields[3]);
        } else {
            //throw new Exception("Invalid Transaction Type detected");
        }

    }

    public static Date parseDate(String dateStr) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(dateStr);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Type getType() {
        return this.type;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public Date getDate() {
        return this.date;
    }

    public long getUnits() {
        return this.units;
    }

    // this is useful for constructing BigDecimals to perform calculations
    public String getUnitsString() {
        return Long.toString(this.units);
    }

    public void setUnits(long units) {
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
