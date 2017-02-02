package com.sovesting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Transaction {

    protected String type; // TODO: refactor to Enum
    protected String employeeId;
    protected Date date; // could be: vest date, bonus date, sale date

    // initialize the 3 common fields
    protected void init(String[] fields) {

        this.type = fields[0];
        this.employeeId = fields[1];

        try {
            this.date = new SimpleDateFormat("yyyyMMdd").parse(fields[2]);
        } catch(ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "type=" + type + ", employeeId=" + employeeId + ", date=" + date;
    }

    //public abstract void setFields(String[] fields);
}
