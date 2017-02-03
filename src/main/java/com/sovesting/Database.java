package com.sovesting;

import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

public class Database {

    private ArrayList<Transaction> transactions; // master list of transactions
    private TreeMap<String,Employee> employees;

    public Database() {
        this.transactions = new ArrayList<Transaction>();
        this.employees = new TreeMap<String,Employee>();
    }

    public ArrayList<Transaction> getTransactions() {
        return this.transactions;
    }

    public TreeMap<String,Employee> getEmployees() {
        return this.employees;
    }

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
}
