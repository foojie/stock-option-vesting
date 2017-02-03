package com.sovesting;

import java.lang.reflect.Array;
import java.util.*;

// this class simulates a database to store transactions and employees in data tables
// you can retrieve data by calling one of the getX functions
// queries to a real database in SQL would be much faster, but here we have to do iterations on our arrays
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

    // returns a list of employee transactions sorted by date, then by type (order: VEST, PERF, SALE)
    public ArrayList<Transaction> getSortedTransactionsByEmployeeId(String employeeId) {

        ArrayList<Transaction> transactions = this.getTransactionsByEmployeeId(employeeId);
        Collections.sort(transactions, new TransactionsComparator());
        return transactions;
    }

    // get Employee Vest transactions On Or Before the given date
    // this function expects to be passed a pre-sorted list
    public ArrayList<Transaction> getEmployeeVestsToDate(ArrayList<Transaction> employeeTransactions, Date toDate) {

        ArrayList<Transaction> vestsToDate = new ArrayList<Transaction>();

        for (Transaction transaction: employeeTransactions) {
            if(transaction.getType() == Type.VEST && transaction.getDate().before(toDate) && !transaction.getDate().after(toDate)) {
                vestsToDate.add(transaction);
            }
        }

        return vestsToDate;
    }
}
