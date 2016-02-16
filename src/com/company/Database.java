package com.company;

//import com.sun.xml.internal.bind.v2.TODO;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Danny on 2/14/2016.
 */
public class Database {
    public final List<List<Integer>> transactions;
    public final Set<Integer> uniqEl;

    public Database(String dataFileName) throws Exception {
        transactions = new ArrayList<List<Integer>>();
        /* Create a set of unique elements */
        uniqEl = new LinkedHashSet<>();

        FileInputStream fin = new FileInputStream(dataFileName);
        InputStreamReader istream = new InputStreamReader(fin);
        BufferedReader stdin = new BufferedReader(istream);

        String line;

        double startTime = System.currentTimeMillis();

        /* Populate 'transactions' List with each sorted transaction */
        while ((line = stdin.readLine()) != null) {
            List<Integer> trans = new ArrayList<Integer>();
            String[] temp = line.split("\\s+");

            for (String num : temp) trans.add(Integer.parseInt(num));

            if (trans.isEmpty()) continue;

            Collections.sort(trans);
            transactions.add(trans);
        }

        /* Close input stream readers */
        fin.close();
        istream.close();
        stdin.close();

        for (List<Integer> t : transactions) {
            for (Integer n : t) {
                uniqEl.add(n);
            }
        }


        double endTime = System.currentTimeMillis();
        System.out.println("Database created in " + (endTime - startTime) / 1000.0 + " seconds");
    }


    public int scanDatabase(List<Integer> transaction) {
        int count = 0;
        for (List<Integer> row : transactions) {
            if (row.containsAll(transaction)){
                //System.out.println("tra: "+ transaction);
                //System.out.println("row: "+ row);
                count++;
            }
        }
        //System.out.println(count);
        return  count;
    }

}