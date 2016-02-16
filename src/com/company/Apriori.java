package com.company;
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
        HashMap< List< Integer>, Integer > seen = new HashMap< List< Integer >, Integer >();

        int k = 1;
        int n = db.transactions.size();

        for(Integer item : itemset) {
            List< Integer > temp = new ArrayList< Integer >();
            temp.add(item);
            Lk.add(temp);
        }

        while(k <= n && !Lk.isEmpty()) {
            //System.out.println("k level:  " + k);
            //System.out.println("Lk: " + Lk);

            // Clear our the seen and Ck for a new layer
            seen.clear();
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
                // update frequent and seen sets
                frequent.put(kth, count);
                seen.put(kth, k);
            }

            //System.out.println("Ck: " + Ck);
            // if Ck is empty after finding all candidates then no candidate available and we are done
            if(Ck.isEmpty()) break;

            List< List<Integer> > temp;
            temp = join(Ck);
            //System.out.println("Temporary: " + temp);
            Lk.clear();
            Lk = prune(temp, seen, k);
            //System.out.println("Pruned: " + Lk);
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
                                      HashMap<List<Integer>,Integer> seen,
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
                    // derive all subsets(k-1) of the current superset to compare with seen
                    for(int j = 0; j < x.size(); j++) {
                        if(i != j){
                            derived.add(x.get(j));
                        }
                    }
                    // if derived subset is NOT in seen so,  NOT frequent
                    if(!seen.containsKey(derived)) {
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
}
