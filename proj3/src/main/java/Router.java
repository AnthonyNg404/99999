import java.util.PriorityQueue;
import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Comparator;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */

    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {

        long st = g.closest(stlon, stlat);
        long dest = g.closest(destlon, destlat);
        NodeComparator cmp = new NodeComparator(dest, g);
        PriorityQueue<Node> pq = new PriorityQueue<>(cmp);
        Set<Long> marked = new HashSet<>();
        //Map<Long, Long> route = new LinkedHashMap<>();
        //route.put(st, null);
        pq.add(new Node(st, null, 0));
        marked.add(st);
        Node temp = pq.poll();
        while (temp.id != dest) {
            //System.out.println(temp.id + "  node + parent  " + temp.parent);
            //System.out.println(temp + "    temp");
            for (long n : g.adjacent(temp.id)) {
                if (temp.parent != null) {
                    if (temp.parent.id == n) {
                        continue;
                    } else if (marked.contains(n)) {
                        continue;
                    }
                }
                Node i = new Node(n, temp, temp.mileage + g.distance(temp.id, n));
                pq.add(i);
            }
            temp = pq.poll();
            marked.add(temp.id);
        }
        List<Long> result = new LinkedList<>();
        result = addResult(temp, result);
        return result; // FIXME
    }

    private static List<Long> addResult(Node n, List<Long> l) {
        if (n.parent != null) {
            l = addResult(n.parent, l);
        }
        l.add(n.id);
        return l;
    }

    private static class NodeComparator implements Comparator<Node> {
        //private double stlon;
        //private double stlat;
        private double destlon;
        private double destlat;
        private GraphDB g;

        NodeComparator(long dest, GraphDB g) {
            //this.stlon = g.lon(st);
            //this.stlat = g.lat(st);
            this.destlon = g.lon(dest);
            this.destlat = g.lat(dest);
            this.g = g;
        }
        public int compare(Node n1, Node n2) {
            double d1 = n1.mileage
                    + GraphDB.distance(g.lon(n1.id), g.lat(n1.id), destlon, destlat);
            double d2 = n2.mileage
                    + GraphDB.distance(g.lon(n2.id), g.lat(n2.id), destlon, destlat);
            return Double.compare(d1, d2);
        }
    }

    private static class Node {
        long id;
        double mileage;
        Node parent;
        Node(long id, Node parent, double mileage) {
            this.id = id;
            this.parent = parent;
            this.mileage = mileage;
        }
    }



    /**private static class NodeComparator implements Comparator<Long> {
        private double stlon;
        private double stlat;
        private double destlon;
        private double destlat;
        private GraphDB g;
        NodeComparator(long st, long dest, GraphDB g) {
            this.stlon = g.lon(st);
            this.stlat = g.lat(st);
            this.destlon = g.lon(dest);
            this.destlat = g.lat(dest);
            this.g = g;
        }
        public int compare(Long n1, Long n2) {
            double d1 = GraphDB.distance(g.lon(n1), g.lat(n1), stlon, stlat)
                    + GraphDB.distance(g.lon(n1), g.lat(n1), destlon, destlat);
            double d2 = GraphDB.distance(g.lon(n2), g.lat(n2), stlon, stlat)
                    + GraphDB.distance(g.lon(n2), g.lat(n2), destlon, destlat);
            return Double.compare(d1, d2);
        }
    }*/



    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        return null; // FIXME
    }

    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}
