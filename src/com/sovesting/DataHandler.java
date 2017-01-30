package com.sovesting;

import java.util.ArrayList;
import java.util.TreeMap;

public class DataHandler {

    private ArrayList<String> records;
    private String marketLine;
    private ArrayList<Transaction> transactions; // TODO: consider moving this to Singleton DataProvider

    public DataHandler(ArrayList<String> records, String marketLine) {
        this.records = records;
        this.marketLine = marketLine;
        this.transactions = new ArrayList<Transaction>();
    }

    // parse the fields from each record
    // create Employee and Transaction data objects
    public void parseRecords() {

        // when adding employee IDs to our TreeMap, we will obtain a unique list of employees sorted by employee ID
        TreeMap<String,Integer> employees = new TreeMap<String,Integer>();

        for (String record : this.records) {
            String[] fields = record.split(",");

            String type = fields[0];
            Transaction transaction = TransactionFactory.createTransaction(fields);
            transactions.add(transaction);

            String employeeId = fields[1];
            employees.put(employeeId, 0);
        }

        //System.out.println(employees);
        //System.out.println(transactions);

        // TODO: create Employee objects from TreeMap

        // TODO: parse market line
    }

    // TODO: consider moving this method to a Singleton class DataProvider
    public ArrayList<Transaction> getTransactionsByEmployeeId(String employeeId) {

        ArrayList<Transaction> employeeTransactions = new ArrayList<Transaction>();

        for (Transaction transaction : this.transactions) {
            if(transaction.employeeId.equals(employeeId)) {
                employeeTransactions.add(transaction);
            }
        }

        return employeeTransactions;
    }

    /*public ArrayList<Vest> getVestsByEmployeeId() {
        //
    }*/

    // store the data in this class for now
    // consider creating EmployeeDAO and TransactionDAO classes for storage
}
