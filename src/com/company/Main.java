package com.company;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Database db= null;
        try{
            db = new Database(args[0]);
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.print(Arrays.toString(args));

        Apriori test = new Apriori(db, Double.parseDouble(args[1]));

        // Hashmap contains key value pair
        // key is the set, and the value is its frequency count
        HashMap<List<Integer>, Integer> frequentSets = new HashMap<>();
        frequentSets = test.start();

    }



    // May not need
    public static void printFile(String input) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("input.txt"), "utf-8"))) {
            writer.write(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
