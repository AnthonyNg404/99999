package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;

public class Solver {
    private int move;
    private ArrayList<WorldState> solution = new ArrayList<>();

    public Solver(WorldState initial) {
        Comparator<SearchNode> nodeComparator = new NodeComparator();
        MinPQ<SearchNode> statusSet = new MinPQ(nodeComparator);
        statusSet.insert(new SearchNode(initial, null, move));
        SearchNode temp = statusSet.delMin();
        while (!temp.current.isGoal()) {
            move = count(temp) + 1;
            /**if (statusSet.isEmpty()
             * || temp.current.estimatedDistanceToGoal()
             * + temp.moves < statusSet.min().current.estimatedDistanceToGoal()
             * + statusSet.min().moves) {
                solution.add(temp.current);
                move += 1;
            }*/
            //MinPQ<SearchNode> statusSetTemp = new MinPQ(nodeComparator);
            for (WorldState n : temp.current.neighbors()) {
                SearchNode i = new SearchNode(n, temp, move);
                if (temp.reference != null) {
                    if (temp.reference.current.equals(n)) {
                        continue;
                    }
                }
                statusSet.insert(i);
            }
            //statusSet.insert(statusSetTemp.min());
            temp = statusSet.delMin();
        }
        addResult(temp);
        solution.add(temp.current);
        //System.out.println(solution);
        //System.out.println(solution.size());
    }



    private class SearchNode {
        private WorldState current;
        private int moves;
        private SearchNode reference;
        SearchNode(WorldState w, SearchNode p, int m) {
            current = w;
            reference = p;
            moves = m;
        }
    }

    private class NodeComparator implements Comparator<SearchNode> {
        public int compare(SearchNode sn1, SearchNode sn2) {
            int h1 = sn1.current.estimatedDistanceToGoal() + sn1.moves;
            int h2 = sn2.current.estimatedDistanceToGoal() + sn2.moves;
            return Integer.compare(h1, h2);
        }
    }


    public int moves() {
        return solution.size() - 1;
    }

    public Iterable<WorldState> solution() {
        return solution;
    }

    private SearchNode addResult(SearchNode p) {
        if (p.reference == null) {
            return p;
        }
        SearchNode x = addResult(p.reference);
        solution.add(p.reference.current);
        return x;
    }

    private int count(SearchNode p) {
        SearchNode a = p;
        int count = 0;
        while (a.reference != null) {
            a = a.reference;
            count++;
        }
        return count;
    }
}

