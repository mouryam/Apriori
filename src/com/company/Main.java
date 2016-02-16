package com.company;

import java.io.*;

public class Main
{
    /*
    // TODO: May not need
    public static void printFile(String input) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("output.txt"), "utf-8"))) {
            writer.write(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    public static void main(String[] args) {
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
        //Apriori test = new Apriori(args[3], db, Double.parseDouble(args[1]));
        //test.start();

        /* Create output file */
        File f = new File("output.txt");
        try
        {
            PrintWriter pw = new PrintWriter(f);

			/* TODO: Print Apriori output */
            //for each element in HashMap {
            //    pw.print(key + " ");
            //    pw.print( "(" + value + ")" );
            //    pw.println();

            pw.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }
}
