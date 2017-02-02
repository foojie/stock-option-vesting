package com.sovesting;

import java.util.Map;

public class SOVMain {

    public static void main(String[] args) {

        // TODO: create a class to run through the steps

        // call the input reader to process raw input
        SOVInputReader reader = new SOVInputReader();
        reader.readStnIn();
        String marketLine = reader.getMarketLine();

        // pass raw input to data handler
        DataHandler dataHandler = new DataHandler(reader.getRecords(), marketLine);
        dataHandler.parseRecords();

        // pass control to the gain calculator
        GainCalculator gainCalculator = new GainCalculator(dataHandler);
        gainCalculator.calculateGains();

        // iterate employee tree map and render each
        for (Map.Entry<String,Employee> entry : dataHandler.getEmployees().entrySet()) {
            //String employeeId = entry.getKey();
            Employee employee = entry.getValue();
            System.out.println(employee.render());
        }
    }
}
