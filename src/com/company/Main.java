package com.company;

import java.io.*;
import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        /* Incorrect execution command */
        if (args.length != 3) {
            System.out.println("Err: Incorrect command line arguments");
            System.out.println("Proper command: $ java Main [input dataset filename] [min_sup value] [output filename]");
            System.exit(0);
        }

        /* Initialize Database object with input dataset */
        Database db = null;
        try {
            db = new Database(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Run Apriori algorithm */
        Apriori test = new Apriori(db, Double.parseDouble(args[1]));
        Map< List<Integer>, Integer > frequentMap = new HashMap<>();

        frequentMap = test.start();
        //System.out.println(frequentMap);


        /* Print frequentMap's data to output file */
        File f = new File("output.txt");
        try {
            PrintWriter pw = new PrintWriter(f);

            for (Map.Entry< List<Integer>, Integer > entry : frequentMap.entrySet())
            {
                for (Integer n : entry.getKey()) pw.print(n + " ");

                String value = entry.getValue().toString();
                pw.print("(" + value + ")");
                pw.println();
            }

            pw.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }
}