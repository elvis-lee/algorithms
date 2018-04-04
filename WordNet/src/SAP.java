import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;


public class SAP {

    private Digraph G;
    private int[] sum;
    private int[] Nvisit;
    private boolean[] visited;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        if (G == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this.G = new Digraph(G);

        sum = new int[G.V()];
        Nvisit = new int[G.V()];
        visited = new boolean[G.V()];
    }

    private void init(){
        Arrays.fill(sum,0);
        Arrays.fill(visited, false);
        Arrays.fill(Nvisit, 0);
    }

    private void validVertex(int v) {
        if (v < 0 || v >= G.V()) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    private void validVertex(Iterable<Integer> iter) {
        for (int v : iter) {
            if (v < 0 || v >= G.V()) {
                throw new java.lang.IllegalArgumentException();
            }
        }
    }

    private void bfs(int source){
        int level = 0;
        Queue<Integer> q = new LinkedList<>();
        q.offer(source);
        visited[source] = true;

        while (!q.isEmpty()){
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int node = q.poll();
                //visited[node] = true;
                Nvisit[node]++;
                sum[node] += level;
                for (int j: G.adj(node)){
                    if (!visited[j]) {
                        q.offer(j);
                        visited[j] = true;
                    }
                }
            }
            level++;
        }
    }

    private void bfs(Iterable<Integer> iter) {
        int level = 0;
        Queue<Integer> q = new LinkedList<>();
        for (int i : iter) {
            q.offer(i);
            visited[i] = true;
        }
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int node = q.poll();
                //visited[node] = true;
                Nvisit[node]++;
                sum[node] += level;
                for (int j : G.adj(node)) {
                    if (!visited[j]) {
                        q.offer(j);
                        visited[j] = true;
                    }
                }
            }
            level++;
        }
    }

    private int findMinIdx(int minVisit, int[] Nvisit, int[] sum){
        int minIdx = -1;
        for (int i = 0; i < sum.length; i++){
            if (Nvisit[i] >= minVisit){
                minIdx = minIdx==-1? i : sum[i]<sum[minIdx]? i:minIdx;
            }
        }
        return minIdx;
    }
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        validVertex(v);
        validVertex(w);
        init();
        bfs(v);
        Arrays.fill(visited, false);
        bfs(w);
        int res = findMinIdx(2, Nvisit, sum);
        return res == -1 ? -1 : sum[res];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        validVertex(v);
        validVertex(w);
        init();
        bfs(v);
        Arrays.fill(visited, false);
        bfs(w);
        int res = findMinIdx(2, Nvisit, sum);
        return res == -1 ? -1 : res;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        validVertex(v);
        validVertex(w);
        init();
        bfs(v);
        Arrays.fill(visited, false);
        bfs(w);
        int res = findMinIdx(2, Nvisit, sum);
        return res == -1 ? -1 : sum[res];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        validVertex(v);
        validVertex(w);
        init();
        bfs(v);
        Arrays.fill(visited, false);
        bfs(w);
        int res = findMinIdx(2, Nvisit, sum);
        return res == -1 ? -1 : res;
    }

    // do unit testing of this class
    public static void main(String[] args){
        In in = new In("/Users/ElvisLee/Dropbox/workspace/drjava_workspace/algorithms/WordNet/wordnet/digraph3.txt");
        Digraph G = new Digraph(in);
        SAP s1 = new SAP(G);
        //System.out.println(s1.length(64451,25327));
        System.out.println(s1.length(9,12));
        //Iterable<Integer> in0 = new ArrayList<>(Arrays.asList(3, 7, 8));
        //Iterable<Integer> in1 = new ArrayList<>(Arrays.asList(1, 11, 12));
        //System.out.println(s1.length(in0, in1));
        //System.out.println(s1.ancestor(in0, in1));
    }
}