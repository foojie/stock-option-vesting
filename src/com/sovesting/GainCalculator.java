package com.sovesting;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class GainCalculator {

    private DataHandler dataHandler;

    public GainCalculator(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    // algorithm to calculate gains for each employee
    public void calculateGains() {

        for (Map.Entry<String,Employee> entry : this.dataHandler.getEmployees().entrySet()) {

            String employeeId = entry.getKey();
            Employee employee = entry.getValue();

            ArrayList<Transaction> employeeTransactions = this.dataHandler.getTransactionsByEmployeeId(employeeId);

            // 1st pass: apply PERF multipliers and SALE deductions to VEST units
            // iterate all employee transactions and process them in order
            // calculate total sales gains
            for (Transaction transaction : employeeTransactions) {

                if(transaction.type.equals("PERF")) {
                    Perf perf = (Perf) transaction;
                    ArrayList<Vest> vests = this.dataHandler.getEmployeeVestsToDate(employeeId, perf.date);
                    this.applyPerfMultiplier(perf, this.dataHandler.getEndDate(), vests);

                } else if(transaction.type.equals("SALE")) {
                    // TODO
                    // modify vest units: applies to one or more vests
                    // calculate single sale gain
                    // add to totalSales
                }
            }

            // 2nd pass: calculate gains from all vests (some have units modified)
            // iterate vests up to end date
            // note: some vests might have 0 zero units due to sale deductions
            // TODO: consider refactoring this to a function for testing
            double totalGains = 0;
            for (Vest vest : this.dataHandler.getEmployeeVestsToDate(employeeId, this.dataHandler.getEndDate())) {
                // formula: gain = (marketPrice - grantPrice) * units
                double gain = (this.dataHandler.getMarketPrice() - vest.getGrantPrice()) * vest.getUnits();
                totalGains += gain;
            }

            // set Employee gain values for output later
            employee.setTotalGains(totalGains);
        }
    }

    // apply a single PERF multiplier to a list of Vest references
    // only if the PERF date is On Or Before the End date
    // TODO: consider moving this method to Perf class or elsewhere
    public void applyPerfMultiplier(Perf perf, Date endDate, ArrayList<Vest> vests) {

        if(perf.date.after(endDate)) {
            return;
        }

        for (Vest vest : vests) {
            double unitsDbl = vest.getUnits() * perf.getMultiplier();
            int unitsInt = (int) unitsDbl;
            vest.setUnits(unitsInt);
        }
    }
}
