package com.sovesting;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.TreeMap;

public class ExampleDataTest {

    // data from input01a.def: the very first basic example provided
    @Test
    public void testVestOnly() {

        ArrayList<String> records = new ArrayList<String>();
        records.add("VEST,001B,20120101,1000,0.45");
        records.add("VEST,002B,20120101,1500,0.45");
        records.add("VEST,002B,20130101,1000,0.50");
        records.add("VEST,001B,20130101,1500,0.50");
        records.add("VEST,003B,20130101,1000,0.50");
        String marketLine = "20140101,1.00";

        DataHandler dataHandler = new DataHandler(records, marketLine);
        dataHandler.parseRecords();

        GainCalculator gainCalculator = new GainCalculator(dataHandler);
        gainCalculator.calculateGains();

        TreeMap<String,Employee> employees = dataHandler.getEmployees();

        Employee e1 = employees.get("001B");
        Assert.assertEquals(e1.getId(),"001B");
        Assert.assertEquals(e1.getTotalGains(), new BigDecimal("1300.00"));
        Assert.assertEquals(e1.getTotalSales(), new BigDecimal("0.00"));

        Employee e2 = employees.get("002B");
        Assert.assertEquals(e2.getId(), "002B");
        Assert.assertEquals(e2.getTotalGains(), new BigDecimal("1325.00"));
        Assert.assertEquals(e2.getTotalSales(), new BigDecimal("0.00"));

        Employee e3 = employees.get("003B");
        Assert.assertEquals(e3.getId(), "003B");
        Assert.assertEquals(e3.getTotalGains(), new BigDecimal("500.00"));
        Assert.assertEquals(e3.getTotalSales(), new BigDecimal("0.00"));
    }

    // BONUS 1 Example with PERFs
    @Test
    public void testPerfBonus1a() {

        ArrayList<String> records = new ArrayList<String>();
        records.add("VEST,001B,20120102,1000,0.45");
        records.add("VEST,002B,20120102,1000,0.45");
        records.add("VEST,003B,20120102,1000,0.45");
        records.add("PERF,001B,20130102,1.5");
        records.add("PERF,002B,20130102,1.5");
        String marketLine = "20140101,1.00";

        DataHandler dataHandler = new DataHandler(records, marketLine);
        dataHandler.parseRecords();

        GainCalculator gainCalculator = new GainCalculator(dataHandler);
        gainCalculator.calculateGains();

        TreeMap<String,Employee> employees = dataHandler.getEmployees();

        Employee e1 = employees.get("001B");
        Assert.assertEquals(e1.getId(),"001B");
        Assert.assertEquals(e1.getTotalGains(), new BigDecimal("825.00"));
        Assert.assertEquals(e1.getTotalSales(), new BigDecimal("0.00"));

        Employee e2 = employees.get("002B");
        Assert.assertEquals(e2.getId(), "002B");
        Assert.assertEquals(e2.getTotalGains(), new BigDecimal("825.00"));
        Assert.assertEquals(e2.getTotalSales(), new BigDecimal("0.00"));

        Employee e3 = employees.get("003B");
        Assert.assertEquals(e3.getId(), "003B");
        Assert.assertEquals(e3.getTotalGains(), new BigDecimal("550.00"));
        Assert.assertEquals(e3.getTotalSales(), new BigDecimal("0.00"));
    }

    // Complex scenario: 2 VESTs, 2 PERFs, 2 SALEs for a single employee
    @Test
    public void testComplex01() {

        ArrayList<String> records = new ArrayList<String>();
        records.add("VEST,001B,20120101,1000,0.45");
        records.add("PERF,001B,20120201,1.5");
        records.add("SALE,001B,20120301,500,1.00");
        records.add("VEST,001B,20130101,1500,0.50");
        records.add("SALE,001B,20130201,500,1.00");
        records.add("PERF,001B,20130301,1.5");
        String marketLine = "20140101,1.00";

        DataHandler dataHandler = new DataHandler(records, marketLine);
        dataHandler.parseRecords();

        GainCalculator gainCalculator = new GainCalculator(dataHandler);
        gainCalculator.calculateGains();

        TreeMap<String,Employee> employees = dataHandler.getEmployees();

        Employee e1 = employees.get("001B");
        Assert.assertEquals(e1.getId(),"001B");
        Assert.assertEquals(e1.getTotalGains(), new BigDecimal("1537.50"));
        Assert.assertEquals(e1.getTotalSales(), new BigDecimal("550.00"));
    }

    // case where a SALE needs units from 2 different VESTs
    @Test
    public void testSaleSplit() {

        ArrayList<String> records = new ArrayList<String>();
        records.add("VEST,Jeff,20120101,600,0.45");
        records.add("SALE,Jeff,20120201,500,1.00");
        records.add("VEST,Jeff,20130101,100,0.50");
        records.add("SALE,Jeff,20130201,150,1.00");
        String marketLine = "20140101,1.00";

        DataHandler dataHandler = new DataHandler(records, marketLine);
        dataHandler.parseRecords();

        GainCalculator gainCalculator = new GainCalculator(dataHandler);
        gainCalculator.calculateGains();

        TreeMap<String,Employee> employees = dataHandler.getEmployees();

        Employee e1 = employees.get("Jeff");
        Assert.assertEquals(e1.getId(),"Jeff");
        Assert.assertEquals(e1.getTotalGains(), new BigDecimal("25.00"));
        Assert.assertEquals(e1.getTotalSales(), new BigDecimal("355.00"));
    }
}