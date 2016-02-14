package com.company;

import com.sun.xml.internal.bind.v2.TODO;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Mourya on 2/14/2016.
 */
public class Database {

    class Entry {
        public Integer first;
        public Integer second;
        Entry() {}
        Entry(Integer first, Integer second) {
            this.first = first;
            this.second = second;
        }
    }
    private final List< List< Integer > > transactions;
    private final List< Integer > elements;

    public Database(String dataFileName) throws Exception {

        transactions = new ArrayList< List< Integer > >();
        elements = new ArrayList< Integer >();

        FileInputStream fin = new FileInputStream(dataFileName);
        InputStreamReader istream = new InputStreamReader(fin);
        BufferedReader stdin = new BufferedReader(istream);

        String line;

        double startTime = System.currentTimeMillis();

        while((line = stdin.readLine()) != null) {
            List< Integer > transaction = new ArrayList< Integer >();
            String[] temp = line.split("\\s+");

            for(String num : temp) {
                transaction.add(Integer.parseInt(num));
            }

            if(transaction.isEmpty()) continue;

            Collections.sort(transaction);
            transactions.add(transaction);
        }

        fin.close();
        istream.close();
        stdin.close();

        int n = transactions.size();
        int[] header = new int[n];
        PriorityQueue< Entry > pQ = new PriorityQueue< Entry >(n, new Comparator< Entry >() {
            public int compare(Entry item1, Entry item2) {
                if(item1.first.equals(item2.first)) {
                    return item1.second.compareTo(item2.second);
                } else {
                    return item1.first.compareTo(item2.first);
                }
            }
        });

        for(int i = 0; i < n; i++) {
            header[i] = 0;
            pQ.add(new Entry(transactions.get(i).get(header[i]), i));
        }

        //TODO
        // Make a list of all unique elements and store it in items


        double endTime = System.currentTimeMillis();
        System.out.println("Database created in " + (endTime - startTime)/1000.0 + " seconds");
    }
}
