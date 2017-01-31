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
            double totalSales = 0;
            for (Transaction transaction : employeeTransactions) {

                if(transaction.type.equals("PERF")) {
                    Perf perf = (Perf) transaction;
                    ArrayList<Vest> vests = this.dataHandler.getEmployeeVestsToDate(employeeId, perf.date);
                    this.applyPerfMultiplier(perf, this.dataHandler.getEndDate(), vests);

                } else if(transaction.type.equals("SALE")) {
                    Sale sale = (Sale) transaction;
                    ArrayList<Vest> vests = this.dataHandler.getEmployeeVestsToDate(employeeId, sale.date);
                    double salesGain = this.computeSale(sale, this.dataHandler.getEndDate(), vests);
                    totalSales += salesGain;
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
            employee.setTotalSales(totalSales);
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

    // algorithm to modify VEST units and calculate sales gain
    // returns the gain from the given SALE
    public double computeSale(Sale sale, Date endDate, ArrayList<Vest> vests) {

        if(sale.date.after(endDate)) {
            return 0;
        }

        double subtotalSalesGain = 0;
        int remainingUnits = 0;
        int previousRemainingUnits = sale.getUnits(); // initialize previous to the sale's units

        for (Vest vest: vests) {

            // does the vest have units to sell
            if(vest.getUnits() <= 0) {
                continue; // to next vest
            }

            // example: VEST 600 units - SALE 500 units = 100
            // example: VEST 100 units - SALE 150 units = -50
            remainingUnits = vest.getUnits() - previousRemainingUnits; // may yield positive or negative remaining units

            // depending on the sign +/- of the remaining units, use the previous RU or vest's units to calculate the gain
             int unitMultiplier = remainingUnits > 0 ? previousRemainingUnits : vest.getUnits();

            double salesGain = (sale.getMarketPrice() - vest.getGrantPrice()) * unitMultiplier;
            subtotalSalesGain += salesGain;

            // update the current vest's units after the SALE deduction
            if(remainingUnits < 0) {
                vest.setUnits(0);
            } else {
                vest.setUnits(remainingUnits);
            }

            previousRemainingUnits = Math.abs(remainingUnits); // update for next iteration

            // check if all units have been sold, if so then we are done
            // otherwise move onto the next VEST to sell the remaining units
            if(remainingUnits > 0) {
                break;
            }
        }

        return subtotalSalesGain;
    }
}
