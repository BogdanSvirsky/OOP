package ru.nsu.svirsky;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import ru.nsu.svirsky.graph.Graph;
import ru.nsu.svirsky.graph.Vertex;
import ru.nsu.svirsky.uitls.exceptions.CycleFoundException;
import ru.nsu.svirsky.uitls.exceptions.VertexNotFoundException;

/**
 * Topological sorting class.
 *
 * @author Bogdan Svirsky
 */
public class TopologicalSorter {
    private HashMap<Vertex, Integer> vertexState = new HashMap<>();
    /*
     * 0 - isn't processed
     * 1 - in processing
     * 2 - is processed
     */
    private Graph currentGraph;
    private ArrayList<Vertex> result = new ArrayList<>();

    /**
     * Toposort method.
     *
     * @param graph graph for sorting
     * @return list of sorted vertices
     * @throws CycleFoundException if cycle was founded
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Vertex> sort(Graph graph) throws CycleFoundException {
        currentGraph = graph;
        vertexState.clear();
        result.clear();
        Set<Vertex> vertecies = graph.getVertices();

        for (Vertex vertex : vertecies) {
            vertexState.put(vertex, 0);
        }

        for (Vertex vertex : vertecies) {
            if (vertexState.get(vertex) == 0) {
                dfs(vertex);
            }
        }

        Collections.reverse(result);

        return new ArrayList<Vertex>(result);
    }

    @SuppressWarnings("unchecked")
    private void dfs(Vertex vertex) throws CycleFoundException {
        vertexState.put(vertex, 1);

        try {
            for (Vertex neighbor : (Set<Vertex>) currentGraph.getNeighbors(vertex)) {
                if (vertexState.get(neighbor) == 0) {
                    dfs(neighbor);
                } else if (vertexState.get(neighbor) == 1) {
                    throw new CycleFoundException("\nCycle was founded in Toposort");
                }
            }
        } catch (VertexNotFoundException e) {
            System.err.println("Bad neighbor in Toposort.dfs()");
        }

        vertexState.put(vertex, 2);
        result.add(vertex);
    }
}
