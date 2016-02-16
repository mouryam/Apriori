package com.company;

//import com.sun.xml.internal.bind.v2.TODO;
//import com.sun.xml.internal.bind.v2.schemagen.xmlschema.*;
//import com.sun.xml.internal.fastinfoset.sax.SystemIdResolver;

import java.util.*;
import java.util.List;

/**
 * Created by Mourya on 2/14/2016.
 */
public class Apriori{
    private final Database db;
    private final Set< Integer > itemset;
    private final HashMap< List< Integer > , Integer> frequent;
    private double minSup;

    public Apriori(Database db, double minSup){
        this.db = db;
        itemset = db.uniqEl;
        frequent = new HashMap < List< Integer > , Integer >();
        this.minSup = minSup;
    }

    public HashMap < List<Integer>,Integer > start() {
        double startTime = System.currentTimeMillis();

        List< List< Integer > > Ck = new ArrayList< List< Integer > >();
        List< List< Integer > > Lk = new ArrayList< List< Integer > >();
        List<Integer> CkMinSup = new ArrayList<>();
        HashMap< List< Integer>, Integer > seenK = new HashMap< List< Integer >, Integer >();

        int k = 1;
        int n = db.transactions.size();

        for(Integer item : itemset) {
            List< Integer > temp = new ArrayList< Integer >();
            temp.add(item);
            Lk.add(temp);
        }

        while(k <= n && !Lk.isEmpty()) {
            System.out.println("k level:  " + k);
            System.out.println("Lk: " + Lk);

            // Clear our the seenK and Ck for a new layer
            seenK.clear();
            Ck.clear();

            // FInd minimum support count of items in current Lk
            for(List< Integer > kth : Lk) {
                // finds the count of the list in the database
                int count = db.scanDatabase(kth);
                // if fails, dont add to Ck
                if(count < minSup ) {
                    continue;
                }
                // else add to frequent candidate list
                Ck.add(kth);
                CkMinSup.add(count);
            }

            System.out.println("Ck: " + Ck);
            // if Ck is empty after finding all candidates then no candidate available and we are done
            if(Ck.isEmpty()) break;

            // Add current candidates to frequent list
            for (int i = 0; i < Ck.size(); i++) {
                frequent.put(Ck.get(i),CkMinSup.get(i));
                // Store frequent sets to utilize in pruning in seenK
                seenK.put(Ck.get(i), k);
            }

            List< List<Integer> > temp;
            temp = join(Ck);
            System.out.println("Temporary: " + temp);
            Lk.clear();
            Lk = prune(temp, seenK, k);
            System.out.println("Pruned: " + Lk);
            k++;
        }

        double endTime = System.currentTimeMillis();
        System.out.println("Apriori completed in " + (endTime - startTime)/1000.0 + " seconds");


        return frequent;
    }

    private List<List<Integer>> join(List< List<Integer> > Ck) {
        List< List< Integer > > temp = new ArrayList< List< Integer > >();
        List< Integer > current = new ArrayList< Integer >();
        for (int i=0; i<Ck.size(); i++)
            for (int j = i + 1; j < Ck.size(); j++) {
                List<Integer> last = Ck.get(j);
                for (Integer x : last) {
                    if (!(Ck.get(i).contains(x))) {
                        current = new ArrayList<Integer>();
                        current.addAll(Ck.get(i));
                        current.add(x);
                        Collections.sort(current);
                    }
                }
                if (!temp.contains(current)) {
                    temp.add(current);
                }

            }
        return temp;
    }

    private List<List<Integer>> prune(List<List<Integer>> temp,
                                      HashMap<List<Integer>,Integer> seenK,
                                      int k) {
        List< List< Integer > > prunedCandidates = new ArrayList< List< Integer > >();

        // For each set (superset) in temp check to prune or not
        for(List< Integer > x : temp) {
            boolean isGood = true;
            // only need to prune if we are in >1 layer
            // otherwise no subsets to check with
            if(k > 1) {
                for(int i = 0; i < x.size(); i++) {
                    List< Integer > derived = new ArrayList< Integer >();
                    // derive all subsets(k-1) of the current superset to compare with seenK
                    for(int j = 0; j < x.size(); j++) {
                        if(i != j){
                            derived.add(x.get(j));
                        }
                    }
                    // if derived subset is NOT in seenK so,  NOT frequent
                    if(!seenK.containsKey(derived)) {
                        isGood = false;
                        // break and avoid adding
                        break;
                    }
                }
            }
            if(isGood) {
                prunedCandidates.add(x);
            }
        }
        return prunedCandidates;
    }

    public void printPatterns() {
        System.out.println("Frequent Itemsets");
        for(List< Integer > pattern : frequent.keySet()) {
            System.out.println(pattern);
        }
        System.out.println("Total " + frequent.size() + " itemsets");
    }
}