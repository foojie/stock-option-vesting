package com.sovesting;

import java.util.Map;

public class SOVMain {

    public static void main(String[] args) {

        // call the input reader to process raw input
        SOVInputReader reader = new SOVInputReader();
        reader.readStnIn();
        String marketLine = reader.getMarketLine();

        // initialize database
        Database database = new Database();

        // pass raw input to data parser
        DataParser dataParser = new DataParser(database, reader.getRecords(), marketLine);
        dataParser.parseRecords();
        dataParser.parseMarketLine();

        // pass control to the gain calculator
        GainCalculator gainCalculator = new GainCalculator(database, dataParser.getEndDate(), dataParser.getMarketPrice());
        gainCalculator.calculateGains();

        // iterate employee tree map and render each
        for (Map.Entry<String,Employee> entry : database.getEmployees().entrySet()) {
            //String employeeId = entry.getKey();
            Employee employee = entry.getValue();
            System.out.println(employee.render());
        }
    }
}
