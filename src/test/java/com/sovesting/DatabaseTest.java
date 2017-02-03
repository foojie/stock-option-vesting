package com.sovesting;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;

public class DatabaseTest {

    // Simulate some transaction records that are out of order
    // they should be sorted by date, then by type (VEST, PERF, SALE)
    @Test
    public void testGetSortedTransactions() {

        ArrayList<String> records = new ArrayList<String>();

        records.add("PERF,Jeff,20130101,1.5");
        records.add("SALE,Jeff,20130101,500,1.00");
        records.add("VEST,Jeff,20130101,1500,0.50");

        records.add("SALE,Jeff,20120101,500,1.00");
        records.add("PERF,Jeff,20120101,1.5");
        records.add("VEST,Jeff,20120101,1000,0.45");

        Database database = new Database();

        DataParser dataParser = new DataParser(database, records, "");
        dataParser.parseRecords();

        ArrayList<Transaction> transactions = database.getSortedTransactionsByEmployeeId("Jeff");

        Date date1 = Transaction.parseDate("20120101");
        Date date2 = Transaction.parseDate("20130101");

        // first date 2012-01-01

        Transaction t1 = transactions.get(0);
        Assert.assertEquals(t1.getType(), Type.VEST);
        Assert.assertEquals(t1.getDate(), date1);

        Transaction t2 = transactions.get(1);
        Assert.assertEquals(t2.getType(), Type.PERF);
        Assert.assertEquals(t2.getDate(), date1);

        Transaction t3 = transactions.get(2);
        Assert.assertEquals(t3.getType(), Type.SALE);
        Assert.assertEquals(t3.getDate(), date1);

        // next date 2013-01-01

        Transaction t4 = transactions.get(3);
        Assert.assertEquals(t4.getType(), Type.VEST);
        Assert.assertEquals(t4.getDate(), date2);

        Transaction t5 = transactions.get(4);
        Assert.assertEquals(t5.getType(), Type.PERF);
        Assert.assertEquals(t5.getDate(), date2);

        Transaction t6 = transactions.get(5);
        Assert.assertEquals(t6.getType(), Type.SALE);
        Assert.assertEquals(t6.getDate(), date2);
    }

}