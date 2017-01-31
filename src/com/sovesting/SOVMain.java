package com.sovesting;

import java.util.ArrayList;
import java.util.Map;

public class SOVMain {

    public static void main(String[] args) {

        //System.out.println("Run!");

        // TODO: create a class to run through the steps

        // call the input reader to process raw input
        SOVInputReader reader = new SOVInputReader();
        reader.readStnIn();
        ArrayList<String> records = reader.getRecords();
        String marketLine = reader.getMarketLine();

        // pass raw input to data handler
        DataHandler dataHandler = new DataHandler(records, marketLine);
        dataHandler.parseRecords();

        // debug
        //System.out.println(dataHandler.getTransactionsByEmployeeId("001B"));

        // pass control to the gain calculator
        GainCalculator gainCalculator = new GainCalculator(dataHandler);
        gainCalculator.calculateGains();

        // debug
        //System.out.println(dataHandler.getTransactions());

        // iterate employee tree map and render each
        for (Map.Entry<String,Employee> entry : dataHandler.getEmployees().entrySet()) {
            //String employeeId = entry.getKey();
            Employee employee = entry.getValue();
            System.out.println(employee.render());
        }
    }
}
