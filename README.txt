Our implementation of Apriori uses 3 classes:

1) DATABASE: Takes dataset file and creates a list of transactions (ArrayList< List<Integer> >) and a set of unique elements (LinkedHashSet<>);

2) APRIORI: Takes data structures created by Database class and performs the Apriori algorithm, returning a HashMap of the frequent itemsets;

3) MAIN: Reads in command line arguments, runs the Apriori algorithm, and outputs the frequent item sets and their respective support values to a text file.


To run the program, first compile the 3 classes, then run Main like so when in the src directory,
Make sure the input files are in src directory:

        $ javac com/company/Main.java
        $ javac com/company/Apriori.java
        $ javac com/company/Database.java
        $ java com/company/Main [input dataset filename] [min_sup value] [output filename]
