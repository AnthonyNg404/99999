package lab11.graphs;
import java.util.Random;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int[] start;
    private boolean foundCircle = false;

    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        start = new int[maze.V()];

        /* Set point where circle search starts */
        Random rand = new Random();
        int startX = rand.nextInt(maze.N());
        int startY = rand.nextInt(maze.N());
        int s = maze.xyTo1D(startX, startY);
        marked[s] = true;
        start[s] = s;
        dfs(s);
        announce();
    }

    private void dfs(int v) {
        for (int w : maze.adj(v)) {
            if (foundCircle) {
                return;
            }
            if (!marked[w]) {
                marked[w] = true;
                start[w] = v;
                dfs(w);
            } else if (w != start[v]) {
                int cur = v;
                edgeTo[cur] = start[cur];
                while (cur != w) {
                    cur = start[cur];
                    edgeTo[cur] = start[cur];
                }
                foundCircle = true;
                return;
            }
        }
    }

    // Helper methods go here
}

