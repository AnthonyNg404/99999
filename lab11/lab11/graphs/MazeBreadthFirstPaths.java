package lab11.graphs;
import java.util.Queue;
import java.util.LinkedList;
/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    // Inherits public fields:
    private Queue<Integer> fringe;
    private int start;
    private int end;


    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        start = m.xyTo1D(sourceX, sourceY);
        end = m.xyTo1D(targetX, targetY);
        fringe = new LinkedList<>();
        fringe.add(start);
        marked[start] = true;
        distTo[start] = 0;
        edgeTo[start] = start;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        while (!fringe.isEmpty()) {
            int v = fringe.poll();
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    distTo[w] = distTo[v] + 1;
                    edgeTo[w] = v;
                    announce();
                    if (w == end) {
                        return;
                    } else {
                        fringe.add(w);
                    }
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

