package com.sovesting;

import java.math.BigDecimal;
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

        int salesCount = 0;

        for (Map.Entry<String,Employee> entry : this.dataHandler.getEmployees().entrySet()) {

            String employeeId = entry.getKey();
            Employee employee = entry.getValue();

            ArrayList<Transaction> employeeTransactions = this.dataHandler.getTransactionsByEmployeeId(employeeId);

            // 1st pass: apply PERF multipliers and SALE deductions to VEST units
            // iterate all employee transactions and process them in order
            // calculate total sales gains
            BigDecimal totalSales = BigDecimal.ZERO;
            for (Transaction transaction : employeeTransactions) {

                if(transaction.type.equals("PERF")) {
                    Perf perf = (Perf) transaction;
                    ArrayList<Vest> vests = this.dataHandler.getEmployeeVestsToDate(employeeId, perf.date);
                    this.applyPerfMultiplier(perf, this.dataHandler.getEndDate(), vests);

                } else if(transaction.type.equals("SALE")) {
                    Sale sale = (Sale) transaction;
                    ArrayList<Vest> vests = this.dataHandler.getEmployeeVestsToDate(employeeId, sale.date);
                    BigDecimal salesGain = this.computeSale(sale, this.dataHandler.getEndDate(), vests);
                    totalSales = totalSales.add(salesGain);
                    salesCount++;
                }
            }

            // 2nd pass: calculate gains from all vests (some have units modified)
            // iterate vests up to end date
            // note: some vests might have 0 zero units due to sale deductions
            // TODO: consider refactoring this to a function for testing
            BigDecimal totalGains = BigDecimal.ZERO;
            for (Vest vest : this.dataHandler.getEmployeeVestsToDate(employeeId, this.dataHandler.getEndDate())) {
                // formula: gain = (marketPrice - grantPrice) * units
                BigDecimal price = (this.dataHandler.getMarketPrice().subtract(vest.getGrantPrice()));
                BigDecimal gain = price.multiply(vest.getUnits());

                // only add positive gains to the total, ignore negative gains
                if(gain.compareTo(BigDecimal.ZERO) > 0) {
                    totalGains = totalGains.add(gain);
                }
            }

            // set Employee gain values for output later
            employee.setTotalGains(totalGains);
            employee.setTotalSales(totalSales);
            employee.setSalesCount(salesCount);
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
            // formula: units = VEST units * PERF multiplier;
            BigDecimal units = vest.getUnits().multiply(perf.getMultiplier());
            vest.setUnits(units);
        }
    }

    // algorithm to modify VEST units and calculate sales gain
    // will handle SALE splits if necessary i.e. the case where a SALE needs to be split across two or more VESTs
    // returns the gain from the given SALE
    // note: return value should be >= 0
    public BigDecimal computeSale(Sale sale, Date endDate, ArrayList<Vest> vests) {

        if(sale.date.after(endDate)) {
            return BigDecimal.ZERO;
        }

        BigDecimal subtotalSalesGain = BigDecimal.ZERO;
        BigDecimal remainingUnits = BigDecimal.ZERO;
        BigDecimal previousRemainingUnits = sale.getUnits(); // initialize previous to the sale's units

        for (Vest vest: vests) {

            // does the vest have units to sell
            // formula: vest units <= 0
            if(vest.getUnits().compareTo(BigDecimal.ZERO) < 0 || vest.getUnits().compareTo(BigDecimal.ZERO) == 0) {
                continue; // to next vest
            }

            // example: VEST 600 units - SALE 500 units = 100
            // example: VEST 100 units - SALE 150 units = -50
            remainingUnits = vest.getUnits().subtract(previousRemainingUnits); // may yield positive or negative remaining units

            // depending on the sign +/- of the remaining units, use the previous RU or vest's units to calculate the gain
            BigDecimal unitMultiplier = remainingUnits.compareTo(BigDecimal.ZERO) > 0 ? previousRemainingUnits : vest.getUnits();

            // formula: sales gain = (sale market price - vest grant price) * unit multiplier
            BigDecimal price = sale.getMarketPrice().subtract(vest.getGrantPrice());
            BigDecimal salesGain = price.multiply(unitMultiplier);

            // only add positive gains, ignore negative gains
            if(salesGain.compareTo(BigDecimal.ZERO) > 0) {
                subtotalSalesGain = subtotalSalesGain.add(salesGain);
            }

            // update the current vest's units after the SALE deduction
            if(remainingUnits.compareTo(BigDecimal.ZERO) < 0) {
                vest.setUnits(BigDecimal.ZERO);
            } else {
                vest.setUnits(remainingUnits);
            }

            previousRemainingUnits = remainingUnits.abs(); // update for next iteration

            // check if all units have been sold, if so then we are done
            // otherwise move onto the next VEST to sell the remaining units
            if(remainingUnits.compareTo(BigDecimal.ZERO) > 0) {
                break;
            }
        }

        return subtotalSalesGain;
    }
}
