package ru.nsu.svirsky.graph;

import java.util.ArrayList;
import java.util.HashMap;

import ru.nsu.svirsky.uitls.CycleFoundException;
import ru.nsu.svirsky.uitls.VertexNotFoundException;

public class TopologicalSorter {
    private HashMap<Vertex, Boolean> used = new HashMap<>();
    private Graph currentGraph;
    private ArrayList<Vertex> result = new ArrayList<>();

    public ArrayList<Vertex> sort(Graph graph) throws CycleFoundException {
        currentGraph = graph;
        used.clear();
        result.clear();
        ArrayList<Vertex> vertecies = graph.getVertices();

        for (Vertex vertex : vertecies) {
            used.put(vertex, false);
        }

        for (Vertex vertex : vertecies) {
            if (!used.get(vertex)) {
                dfs(vertex);
            }
        }

        return new ArrayList<Vertex>(result.reversed());
    }

    private void dfs(Vertex vertex) throws CycleFoundException {
        used.put(vertex, true);

        try {
            for (Vertex neighbor : (ArrayList<Vertex>) currentGraph.getNeighbors(vertex)) {
                if (!used.get(neighbor)) {
                    dfs(neighbor);
                } else {
                    throw new CycleFoundException("\nCycle is founded in Toposort");
                }
            }
        } catch (VertexNotFoundException e) {
            System.err.println("Bad neighbor in Toposort.dfs()");
        }

        result.add(vertex);
    }
}
