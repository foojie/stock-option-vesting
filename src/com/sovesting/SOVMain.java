package com.sovesting;

//import java.util.ArrayList;
import java.util.Map;

public class SOVMain {

    public static void main(String[] args) {

        // TODO: create a class to run through the steps

        // call the input reader to process raw input
        SOVInputReader reader = new SOVInputReader();
        reader.readStnIn();
        String marketLine = reader.getMarketLine();

        // simulate raw input strings
        /*ArrayList<String> records = new ArrayList<String>();
        records.add("VEST,Jeff,20120101,600,0.45");
        records.add("SALE,Jeff,20120201,500,1.00");
        records.add("VEST,Jeff,20130101,100,0.50");
        records.add("SALE,Jeff,20130201,150,1.00");*/

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
