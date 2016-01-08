import java.util.List;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.Set;

/**
 * @author RILEY AUTEN
 * @version 1.0
 */
public class GraphSearch {

    /**
     * Searches the Graph passed in as an adjacency list(adjList) to find if a
     * path exists from the start node to the goal node using General Graph
     * Search.
     *
     * Assume the adjacency list contains adjacent nodes of each node in the
     * order they should be added to the Structure. If there are no adjacent
     * nodes, then an empty list is present.
     *
     * The structure(struct) passed in is an empty structure that may behave as
     * a Stack or Queue and this function should execute DFS or BFS on the
     * graph, respectively.
     *
     * DO NOT use {@code instanceof} to determine the type of the Structure!
     *
     * @param start the object representing the node you are starting at.
     * @param struct the Structure you should use to implement the search.
     * @param adjList the adjacency list that represents the graph we are
     *        searching.
     * @param goal the object representing the node we are trying to reach.
     * @param <T> the data type representing the nodes in the graph.
     * @return true if path exists, false otherwise.
     */
    private static <T> boolean generalGraphSearch(T start, Structure<T> struct,
            Map<T, List<T>> adjList, T goal) {
        if (start == null || struct == null
                || adjList == null || goal == null) {
            throw new IllegalArgumentException("One or more of "
                    + "your inputs are null. Please fix");
        }
        if (!adjList.containsKey(goal) || !adjList.containsKey(start)) {
            throw new IllegalArgumentException("One or more of the"
                    + " following is not in the graph: start node, goal node");
        }
        if (start == goal) {
            return true;
        }
        T current;
        Set<T> visited = new HashSet<>();
        struct.add(start);
        List<T> nodes;
        while (!struct.isEmpty()) {
            T newCurrent = struct.remove();
            visited.add(newCurrent);
            current = newCurrent;
            if (current == goal) {
                return true;
            }
            nodes = adjList.get(current);
            for (T node : nodes) {
                if (!visited.contains(node)) {
                    struct.add(node);
                }
            }
        }
        return false;
    }

    /**
     * Searches the Graph passed in as an adjacency list(adjList) to find if a
     * path exists from the start node to the goal node using Breadth First
     * Search.
     *
     * Assume the adjacency list contains adjacent nodes of each node in the
     * order they should be added to the Structure. If there are no adjacent
     * nodes, then an empty list is present.
     *
     * This method should be written in one line.
     *
     * @throws IllegalArgumentException if any input is null, or if
     * {@code start} or {@code goal} doesn't exist in the graph
     * @param start the object representing the node you are starting at.
     * @param adjList the adjacency list that represents the graph we are
     *        searching.
     * @param goal the object representing the node we are trying to reach.
     * @param <T> the data type representing the nodes in the graph.
     * @return true if path exists false otherwise
     */
    public static <T> boolean breadthFirstSearch(T start,
            Map<T, List<T>> adjList, T goal) {
        return generalGraphSearch(start,
                new StructureQueue<T>(), adjList, goal);
    }

    /**
     * Searches the Graph passed in as an adjacency list(adjList) to find if a
     * path exists from the start node to the goal node using Depth First
     * Search.
     *
     * Assume the adjacency list contains adjacent nodes of each node in the
     * order they should be added to the Structure. If there are no adjacent
     * nodes, then an empty list is present.
     *
     * This method should be written in one line.
     *
     * @throws IllegalArgumentException if any input is null, or if
     * {@code start} or {@code goal} doesn't exist in the graph
     * @param start the object representing the node you are starting at.
     * @param adjList the adjacency list that represents the graph we are
     *        searching.
     * @param goal the object representing the node we are trying to reach.
     * @param <T> the data type representing the nodes in the graph.
     * @return true if path exists false otherwise
     */
    public static <T> boolean depthFirstSearch(T start,
            Map<T, List<T>> adjList, T goal) {
        return generalGraphSearch(start,
                new StructureStack<>(), adjList, goal);
    }

    /**
     * Find the shortest distance between the start node and the goal node
     * given a weighted graph in the form of an adjacency list where the
     * edges only have positive weights. If there are no adjacent nodes for
     * a node, then an empty list is present.
     *
     * Return the aforementioned shortest distance if there exists a path
     * between the start and goal, -1 otherwise.
     *
     * There are guaranteed to be no negative edge weights in the graph.
     *
     * You may import/use {@code java.util.PriorityQueue}.
     *
     * @throws IllegalArgumentException if any input is null, or if
     * {@code start} or {@code goal} doesn't exist in the graph
     * @param start the object representing the node you are starting at.
     * @param adjList the adjacency list that represents the graph we are
     *        searching.
     * @param goal the object representing the node we are trying to reach.
     * @param <T> the data type representing the nodes in the graph.
     * @return the shortest distance between the start and the goal node
     */
    public static <T> int dijkstraShortestPathAlgorithm(T start,
            Map<T, List<VertexDistancePair<T>>> adjList, T goal) {
        if (start == null || adjList == null || goal == null) {
            throw new IllegalArgumentException("One or more"
                    + " of your inputs are null. Please fix");
        }
        if (!adjList.containsKey(goal) || !adjList.containsKey(start)) {
            throw new IllegalArgumentException("One or more of the following"
                    + " is not in the graph: start node, goal node");
        }
        if (start == goal) {
            return 0;
        }
        PriorityQueue<VertexDistancePair> struct = new PriorityQueue<>();
        struct.add(new VertexDistancePair(start, 0));
        Set<T> visited = new HashSet<>();
        List<VertexDistancePair<T>> nodes;
        int distance = 0;
        while (!struct.isEmpty()) {
            VertexDistancePair<T> newCurrent = struct.remove();
            distance = newCurrent.getDistance();
            visited.add(newCurrent.getVertex());
            if (newCurrent.getVertex() == goal) {
                return distance;
            }
            nodes = adjList.get(newCurrent.getVertex());
            if (nodes != null) {
                for (VertexDistancePair<T> node : nodes) {
                    if (!visited.contains(node.getVertex())) {
                        struct.add(new VertexDistancePair(
                                node.getVertex(),
                                distance + node.getDistance()));
                    }
                }
            }
        }
        return -1;
    }
}
