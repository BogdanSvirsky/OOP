package ru.nsu.svirsky.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import ru.nsu.svirsky.uitls.exceptions.EdgeNotFoundException;
import ru.nsu.svirsky.uitls.exceptions.MultipleEdgesFoundException;
import ru.nsu.svirsky.uitls.exceptions.VertexNotFoundException;

/**
 * Incident matrix graph class.
 *
 * @author Bogdan Svirsky
 */
public class IncidentMatrixGraph<V, E extends Number>
        implements Graph<V, E> {
    private HashMap<Vertex<V>, ArrayList<Integer>> incidentMatrix;
    private ArrayList<Edge<V, E>> edges;

    public IncidentMatrixGraph() {
        incidentMatrix = new HashMap<>();
        edges = new ArrayList<>();
    }

    /**
     * Constructor.
     *
     * @param vertices vertices from graph construct
     */
    public IncidentMatrixGraph(Iterable<Vertex<V>> vertices) {
        this();

        if (vertices == null) {
            return;
        }

        for (Vertex<V> vertex : vertices) {
            addVertex(vertex);
        }
    }

    /**
     * Constructor.
     *
     * @param vertices vertices from graph construct
     * @param edges edges from graph construct
     * @throws VertexNotFoundException if vertex not found in graph
     * @throws MultipleEdgesFoundException if edge already exists in graph
     */
    public IncidentMatrixGraph(
            Iterable<Vertex<V>> vertices,
            Iterable<Edge<V, E>> edges)
            throws VertexNotFoundException, MultipleEdgesFoundException {
        this(vertices);

        if (edges == null) {
            return;
        }

        for (Edge<V, E> edge : edges) {
            addEdge(edge);
        }
    }

    @Override
    public void addVertex(Vertex<V> vertex) {
        int edgesCount = edges.size();
        ArrayList<Integer> newMatrixRow = new ArrayList<>();

        for (int i = 0; i < edgesCount; i++) {
            newMatrixRow.add(0);
        }

        incidentMatrix.put(vertex, newMatrixRow);
    }

    @Override
    public void deleteVertex(Vertex<V> deletedVertex) throws VertexNotFoundException {
        if (!incidentMatrix.containsKey(deletedVertex)) {
            throw new VertexNotFoundException();
        }

        ArrayList<Edge<V, E>> newEdges = new ArrayList<>();

        for (Edge<V, E> edge : edges) {
            if (!edge.getTo().equals(deletedVertex) && !edge.getFrom().equals(deletedVertex)) {
                newEdges.add(edge);
            }
        }

        edges = newEdges;

        for (Vertex<V> vertex : incidentMatrix.keySet()) {
            ArrayList<Integer> newRow = new ArrayList<>();

            for (Edge<V, E> edge : newEdges) {
                if (edge.getFrom().equals(vertex)) {
                    newRow.add(-1);
                } else if (edge.getTo().equals(vertex)) {
                    newRow.add(1);
                } else {
                    newRow.add(0);
                }
            }

            incidentMatrix.put(vertex, newRow);
        }
        incidentMatrix.remove(deletedVertex);
    }

    @Override
    public void addEdge(Edge<V, E> edge)
            throws VertexNotFoundException, MultipleEdgesFoundException {
        if (!incidentMatrix.containsKey(edge.getFrom())
                || !incidentMatrix.containsKey(edge.getTo())) {
            throw new VertexNotFoundException();
        }

        if (edges.contains(edge)) {
            throw new MultipleEdgesFoundException();
        }

        edges.add(edge);

        for (Vertex<V> vertex : incidentMatrix.keySet()) {
            if (edge.getFrom().equals(vertex)) {
                incidentMatrix.get(vertex).add(-1);
            } else if (edge.getTo().equals(vertex)) {
                incidentMatrix.get(vertex).add(1);
            } else {
                incidentMatrix.get(vertex).add(0);
            }
        }
    }

    @Override
    public void deleteEdge(Edge<V, E> edge)
            throws VertexNotFoundException, EdgeNotFoundException {
        if (!incidentMatrix.containsKey(edge.getFrom())
                || !incidentMatrix.containsKey(edge.getTo())) {
            throw new VertexNotFoundException();
        }

        if (!edges.contains(edge)) {
            throw new EdgeNotFoundException();
        }

        int edgeIndex = edges.indexOf(edge);

        for (Vertex<V> vertex : incidentMatrix.keySet()) {
            incidentMatrix.get(vertex).remove(edgeIndex);
        }

        edges.remove(edgeIndex);
    }

    @Override
    public Set<Edge<V, E>> getEdges() {
        return new HashSet<>(edges);
    }

    @Override
    public void clear() {
        incidentMatrix.clear();
        edges.clear();
    }

    @Override
    public Set<Vertex<V>> getVertices() {
        return new HashSet<>(incidentMatrix.keySet());
    }

    @Override
    public Set<Vertex<V>> getNeighbors(Vertex<V> vertex)
            throws VertexNotFoundException {
        if (!incidentMatrix.containsKey(vertex)) {
            throw new VertexNotFoundException();
        }

        HashSet<Vertex<V>> result = new HashSet<>();

        for (Edge<V, E> edge : edges) {
            if (edge.getFrom().equals(vertex)) {
                result.add(edge.getTo());
            }
        }

        return result;
    }

    @Override
    public String toString() {
        String result = "";
        Set<Vertex<V>> vertices = getVertices();
        int verteciesCount = vertices.size();

        if (verteciesCount > 0) {
            result += "vertices: ";
            for (Vertex<V> vertex : vertices) {
                result += vertex;
                if (--verteciesCount > 0) {
                    result += ", ";
                }
            }

            result += "\n";
            int edgesCount = edges.size();

            if (edgesCount > 0) {

                for (Vertex<V> vertex : vertices) {
                    for (Integer element : incidentMatrix.get(vertex)) {
                        result += String.format("%2d ", element.intValue());
                    }

                    result += "\n";
                }

            } else {
                result += "no edges";
            }
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof Graph other) {
            return this.getVertices().equals(other.getVertices())
                    && this.getEdges().equals(other.getEdges());
        } else {
            return false;
        }
    }
}
