package com.sovesting;

public class TransactionFactory {

    public static Transaction createTransaction(String[] fields) {

        String type = fields[0];

        if(type.equals("VEST")) {
            return new Vest(fields);

        } else if(type.equals("PERF")) {
            return new Perf(fields);

        } else if(type.equals("SALE")) {
            return new Sale(fields);
        }

        return null;
    }
}
