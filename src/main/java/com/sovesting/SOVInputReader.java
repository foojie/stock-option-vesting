package com.sovesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SOVInputReader {

    private ArrayList<String> records;
    private String marketLine;

    public SOVInputReader() {
        this.records = new ArrayList<String>();
    }

    public ArrayList<String> getRecords() {
        return this.records;
    }

    public String getMarketLine() {
        return this.marketLine;
    }

    // read the raw input from stdin
    // TODO: refactor to pass in different type of input (i.e. file)
    public void readStnIn() {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;

        try {
            // read the first line N the number of records to follow
            line = br.readLine();
            int n = Integer.valueOf(line);

            for (int i = 0; i < n; i++) {
                line = br.readLine();
                records.add(line);
            }

            // read the last line end date and market price
            this.marketLine = br.readLine();

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(records);
    }
}
