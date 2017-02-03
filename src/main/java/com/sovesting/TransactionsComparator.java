package com.sovesting;

import java.util.Comparator;

public class TransactionsComparator implements Comparator<Transaction> {

    // Sort Order By Date, then by Type
    @Override
    public int compare(Transaction t1, Transaction t2) {

        int dateCompare = t1.getDate().compareTo(t2.getDate());

        if(dateCompare == 0) {
            int typeCompare = t1.getType().compareTo(t2.getType());
            return typeCompare;
        }

        return dateCompare;
    }
}
