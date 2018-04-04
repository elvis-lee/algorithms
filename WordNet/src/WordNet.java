import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class WordNet {

    private HashMap<String, List<Integer>> map;
    private HashMap<Integer, String > mapToSynset;
    private SAP sap;
    private int Nsynset;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){

        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }

        map = new HashMap<>();
        mapToSynset = new HashMap<>();

        Nsynset = 0;
        In inSyn = new In(synsets);
        In inHyper = new In(hypernyms);
        while (inSyn.hasNextLine()) {
            Nsynset++;
            String line = inSyn.readLine();
            String[] sp = line.split(",");
            int idx = Integer.valueOf(sp[0]);
            String[] words = sp[1].split(" ");
            mapToSynset.put(idx, sp[1]);
            for (String word : words) {
                List<Integer> list;
                if (!map.containsKey(word)) {
                    list = new ArrayList<>();
                    map.put(word,list);
                } else  {
                    list = map.get(word);
                }
                list.add(idx);
            }
        }

        Digraph G = new Digraph(Nsynset);
        while (inHyper.hasNextLine()) {
            String line = inHyper.readLine();
            String[] ids = line.split(",");
            int start = Integer.valueOf(ids[0]);
            for (int i = 1; i < ids.length; i++) {
                G.addEdge(start, Integer.valueOf(ids[i]));
            }
        }

        //Check cycle
        DirectedCycle dc = new DirectedCycle(G);
        if (dc.hasCycle()) {
            throw new IllegalArgumentException();
        }
        //Check whether only one root
        int Nroot = 0;
        for (int i = 0; i < G.V(); i++) {
            if (G.outdegree(i) == 0) {
                System.out.println(i);
                Nroot++;
            }
        }
        System.out.println(Nroot);
        if (Nroot > 1) {
            throw new IllegalArgumentException();
        }

        // Graph is good to go
        sap = new SAP(G);

    }

    // returns all WordNet nouns
    public Iterable<String> nouns(){
        return map.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return map.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        if (!map.containsKey(nounA) || !map.containsKey(nounB)) {
            throw new IllegalArgumentException();
        }
        return sap.length(map.get(nounA), map.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }
        if (!map.containsKey(nounA) || !map.containsKey(nounB)) {
            throw new IllegalArgumentException();
        }
        return mapToSynset.get(sap.ancestor(map.get(nounA), map.get(nounB)));
    }

    // do unit testing of this class
    public static void main(String[] args){
        String s1 = "/Users/ElvisLee/Dropbox/workspace/drjava_workspace/algorithms/WordNet/wordnet/synsets.txt";
        String s2 = "/Users/ElvisLee/Dropbox/workspace/drjava_workspace/algorithms/WordNet/wordnet/hypernyms.txt";
        WordNet wn = new WordNet(s1, s2);

        System.out.println(wn.distance("municipality", "region"));
        System.out.println(wn.sap("municipality", "region"));
    }
}