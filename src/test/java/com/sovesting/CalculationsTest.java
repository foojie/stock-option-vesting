package com.sovesting;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class CalculationsTest {

    @Test
    public void testApplyPerfMultiplier() throws Exception {

        // didn't get a chance to write this test
        // but I would test the scenario where one PERF is applied and another PERF is not applied because it's after the end date
    }

    @Test
    public void testComputeSale() throws Exception {

        // a test case where a SALE needs units from 2 different VESTs

        ArrayList<String> records = new ArrayList<String>();
        records.add("VEST,Jeff,20120101,50,0.45");
        records.add("VEST,Jeff,20130101,200,0.50");
        records.add("SALE,Jeff,20130201,150,1.00");
        String marketLine = "20140101,1.00";

        Database database = new Database();

        DataParser dataParser = new DataParser(database, records, marketLine);
        dataParser.parseRecords();
        dataParser.parseMarketLine();

        // get the 3rd transaction, the SALE
        Transaction transaction = database.getTransactions().get(2);

        // setup
        GainCalculator gainCalculator = new GainCalculator(database, dataParser.getEndDate(), dataParser.getMarketPrice());
        ArrayList<Transaction> employeeTransactions = database.getSortedTransactionsByEmployeeId("Jeff");
        ArrayList<Transaction> vests = database.getEmployeeVestsToDate(employeeTransactions, transaction.getDate());

        // test the computeSale() function with a single transaction
        BigDecimal salesGain = gainCalculator.computeSale(transaction, dataParser.getEndDate(), vests);
        BigDecimal salesGainRounded = salesGain.setScale(2, RoundingMode.HALF_UP);

        // sell 50 units from first VEST at sales gain = 1.00 - 0.45 = 0.55
        // 0.55 * 50 = 27.50
        // sell 100 units from second VEST at sales gain = 1.00 - 0.50 = 0.50
        // 0.50 * 100 = 50
        // 27.50 + 50 = 77.50
        Assert.assertEquals(salesGainRounded, new BigDecimal("77.50"));

        // check to see that it modified the VEST units

        Transaction vest1 = vests.get(0);
        Assert.assertEquals(vest1.getUnits(), 0);

        Transaction vest2 = vests.get(1);
        Assert.assertEquals(vest2.getUnits(), 100);
    }

}