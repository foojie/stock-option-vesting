package com.sovesting;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

public class DataHandler {

    // raw input
    private ArrayList<String> records;
    private String marketLine;

    // parsed data
    private ArrayList<Transaction> transactions; // master list of transactions TODO: consider moving this to Singleton DataProvider
    private TreeMap<String,Employee> employees;
    private Date endDate;
    private BigDecimal marketPrice;

    public DataHandler(ArrayList<String> records, String marketLine) {
        this.records = records;
        this.marketLine = marketLine;
        this.transactions = new ArrayList<Transaction>();
        this.employees = new TreeMap<String,Employee>();
    }

    // parse the fields from each record
    // create Employee and Transaction data objects
    public void parseRecords() {

        // when adding employee IDs to our TreeMap, we will obtain a unique list of employees sorted by employee ID
        for (String record : this.records) {
            String[] fields = record.split(",");

            String type = fields[0];
            Transaction transaction = new Transaction(fields);
            this.transactions.add(transaction);

            String employeeId = fields[1];
            this.employees.put(employeeId, new Employee(employeeId));
        }

        // parse market line
        String[] tokens = this.marketLine.split(",");

        try {
            this.endDate = new SimpleDateFormat("yyyyMMdd").parse(tokens[0]);
        } catch(ParseException e) {
            e.printStackTrace();
        }

        this.marketPrice = new BigDecimal(tokens[1]);
    }

    public ArrayList<Transaction> getTransactions() {
        return this.transactions;
    }

    public TreeMap<String,Employee> getEmployees() {
        return this.employees;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public BigDecimal getMarketPrice() {
        return this.marketPrice;
    }

    // TODO: consider moving this method to a Singleton class DataProvider
    public ArrayList<Transaction> getTransactionsByEmployeeId(String employeeId) {

        ArrayList<Transaction> employeeTransactions = new ArrayList<Transaction>();

        for (Transaction transaction : this.transactions) {
            if(transaction.getEmployeeId().equals(employeeId)) {
                employeeTransactions.add(transaction);
            }
        }

        return employeeTransactions;
    }

    // get Employee Vest transactions On Or Before the given date
    public ArrayList<Transaction> getEmployeeVestsToDate(String employeeId, Date toDate) {

        ArrayList<Transaction> vestsToDate = new ArrayList<Transaction>();

        for (Transaction transaction: this.transactions) {
            if(transaction.getType().equals("VEST") && transaction.getEmployeeId().equals(employeeId) && transaction.getDate().before(toDate) && !transaction.getDate().after(toDate)) {
                vestsToDate.add(transaction);
            }
        }

        return vestsToDate;
    }

    // store the data in this class for now
    // consider creating EmployeeDAO and TransactionDAO classes for storage
}
