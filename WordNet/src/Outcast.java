import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    // constructor takes a WordNet object
    private int[] sum;
    private WordNet wn;
    public Outcast(WordNet wordnet){
        if (wordnet == null) {
            throw new IllegalArgumentException();
        }
        wn = wordnet;
    }
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns){
        if (nouns == null) {
            throw new IllegalArgumentException();
        }
        sum = new int[nouns.length];
        int maxIdx = -1;
        for (int i = 0; i < nouns.length; i++) {
            for (int j = 0; j < nouns.length; j++) {
                sum[i] += wn.distance(nouns[i], nouns[j]);
            }
            //System.out.println(sum[i]);
            maxIdx = maxIdx==-1? i : sum[maxIdx] < sum[i] ? i : maxIdx;
        }

        return nouns[maxIdx];
    }
    public static void main(String[] args) {
        //WordNet wordnet = new WordNet(args[0], args[1]);
        String s1 = "/Users/ElvisLee/Dropbox/workspace/drjava_workspace/algorithms/WordNet/wordnet/synsets.txt";
        String s2 = "/Users/ElvisLee/Dropbox/workspace/drjava_workspace/algorithms/WordNet/wordnet/hypernyms.txt";
        WordNet wordnet = new WordNet(s1, s2);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 0; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}