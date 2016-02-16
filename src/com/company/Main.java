package com.company;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        Database db= null;
        try{
            db = new Database(args[1]);
        } catch (Exception e){
            e.printStackTrace();
        }

        Apriori test = new Apriori(args[3], db, Double.parseDouble(args[2]));
        test.start();

    }



    // May not need
    public static void printFile(String input) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("output.txt"), "utf-8"))) {
            writer.write(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
