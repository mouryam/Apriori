package com.company;

import com.sun.xml.internal.bind.v2.TODO;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Mourya on 2/14/2016.
 */
public class Database
{
    /*class Entry
    {
        public Integer first;
        public Integer second;
        Entry() {}
        Entry(Integer first, Integer second) {
            this.first = first;
            this.second = second;
        }
    }*/

    private final List< List<Integer> > transactions;
    private final Set<Integer> uniqEl;

    public Database(String dataFileName) throws Exception
    {
        transactions = new ArrayList< List<Integer> >();

        FileInputStream fin = new FileInputStream(dataFileName);
        InputStreamReader istream = new InputStreamReader(fin);
        BufferedReader stdin = new BufferedReader(istream);

        String line;

        double startTime = System.currentTimeMillis();

        /* Populate 'transactions' List with each sorted transaction */
        while((line = stdin.readLine()) != null)
        {
            List<Integer> trans = new ArrayList<Integer>();
            String[] temp = line.split("\\s+");

            for(String num : temp) trans.add(Integer.parseInt(num));

            if(trans.isEmpty()) continue;

            Collections.sort(trans);
            transactions.add(trans);
        }

        /* Close input stream readers */
        fin.close();
        istream.close();
        stdin.close();


        /* Create a set of unique elements */
        uniqEl = new LinkedHashSet<>();

        for (List<Integer> t : transactions) {
            for (Integer n : t) {
                uniqEl.add(n);
            }
        }


        double endTime = System.currentTimeMillis();
        System.out.println("Database created in " + (endTime - startTime)/1000.0 + " seconds");
    }
}
