package com.sovesting;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

public class DataParser {

    private Database database;

    // raw input
    private ArrayList<String> records;
    private String marketLine;

    // parsed data
    private Date endDate;
    private BigDecimal marketPrice;

    public DataParser(Database database, ArrayList<String> records, String marketLine) {
        this.database = database;
        this.records = records;
        this.marketLine = marketLine;
    }

    // parse the fields from each record
    // create Employee and Transaction objects in the Database
    public void parseRecords() {

        ArrayList<Transaction> transactions = this.database.getTransactions();
        TreeMap<String,Employee> employees = this.database.getEmployees();

        // when adding employee IDs to our TreeMap, we will obtain a unique list of employees sorted by employee ID
        for (String record : this.records) {
            String[] fields = record.split(",");

            String type = fields[0];
            Transaction transaction = new Transaction(fields);
            transactions.add(transaction);

            String employeeId = fields[1];
            employees.put(employeeId, new Employee(employeeId));
        }
    }

    public void parseMarketLine() {

        String[] tokens = this.marketLine.split(",");

        try {
            this.endDate = new SimpleDateFormat("yyyyMMdd").parse(tokens[0]);
        } catch(ParseException e) {
            e.printStackTrace();
        }

        this.marketPrice = new BigDecimal(tokens[1]);
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public BigDecimal getMarketPrice() {
        return this.marketPrice;
    }
}
