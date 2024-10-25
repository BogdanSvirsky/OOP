package ru.nsu.svirsky.graph;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

import ru.nsu.svirsky.uitls.exceptions.EdgeNotFoundException;
import ru.nsu.svirsky.uitls.exceptions.MultipleEdgesFoundException;
import ru.nsu.svirsky.uitls.exceptions.VertexNotFoundException;

public class IncidentMatrixGraph<VertexNameType, EdgeWeightType extends Number>
        implements Graph<VertexNameType, EdgeWeightType> {
    private HashMap<Vertex<VertexNameType>, ArrayList<Integer>> incidentMatrix;
    private ArrayList<Edge<VertexNameType, EdgeWeightType>> edges;

    public IncidentMatrixGraph() {
        incidentMatrix = new HashMap<>();
        edges = new ArrayList<>();
    }

    public IncidentMatrixGraph(Iterable<Vertex<VertexNameType>> vertices) {
        this();

        if (vertices == null) {
            return;
        }

        for (Vertex<VertexNameType> vertex : vertices) {
            addVertex(vertex);
        }
    }

    public IncidentMatrixGraph(
            Iterable<Vertex<VertexNameType>> vertices,
            Iterable<Edge<VertexNameType, EdgeWeightType>> edges)
            throws VertexNotFoundException, MultipleEdgesFoundException {
        this(vertices);

        if (edges == null) {
            return;
        }

        for (Edge<VertexNameType, EdgeWeightType> edge : edges) {
            addEdge(edge);
        }
    }

    @Override
    public void addVertex(Vertex<VertexNameType> vertex) {
        int edgesCount = edges.size();
        ArrayList<Integer> newMatrixRow = new ArrayList<>();

        for (int i = 0; i < edgesCount; i++) {
            newMatrixRow.add(0);
        }

        incidentMatrix.put(vertex, newMatrixRow);
    }

    @Override
    public void deleteVertex(Vertex<VertexNameType> deletedVertex) throws VertexNotFoundException {
        if (!incidentMatrix.containsKey(deletedVertex)) {
            throw new VertexNotFoundException();
        }

        ArrayList<Edge<VertexNameType, EdgeWeightType>> newEdges = new ArrayList<>();

        for (Edge<VertexNameType, EdgeWeightType> edge : edges) {
            if (!edge.getTo().equals(deletedVertex) && !edge.getFrom().equals(deletedVertex)) {
                newEdges.add(edge);
            }
        }

        edges = newEdges;

        for (Vertex<VertexNameType> vertex : incidentMatrix.keySet()) {
            ArrayList<Integer> newRow = new ArrayList<>();

            for (Edge<VertexNameType, EdgeWeightType> edge : newEdges) {
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
    public void addEdge(Edge<VertexNameType, EdgeWeightType> edge)
            throws VertexNotFoundException, MultipleEdgesFoundException {
        if (!incidentMatrix.containsKey(edge.getFrom())
                || !incidentMatrix.containsKey(edge.getTo())) {
            throw new VertexNotFoundException();
        }

        if (edges.contains(edge)) {
            throw new MultipleEdgesFoundException();
        }

        edges.add(edge);

        for (Vertex<VertexNameType> vertex : incidentMatrix.keySet()) {
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
    public void deleteEdge(Edge<VertexNameType, EdgeWeightType> edge)
            throws VertexNotFoundException, EdgeNotFoundException {
        if (!incidentMatrix.containsKey(edge.getFrom())
                || !incidentMatrix.containsKey(edge.getTo())) {
            throw new VertexNotFoundException();
        }

        if (!edges.contains(edge)) {
            throw new EdgeNotFoundException();
        }

        int edgeIndex = edges.indexOf(edge);

        for (Vertex<VertexNameType> vertex : incidentMatrix.keySet()) {
            incidentMatrix.get(vertex).remove(edgeIndex);
        }

        edges.remove(edgeIndex);
    }

    @Override
    public Set<Edge<VertexNameType, EdgeWeightType>> getEdges() {
        return new HashSet<>(edges);
    }

    @Override
    public void clear() {
        incidentMatrix.clear();
        edges.clear();
    }

    @Override
    public Set<Vertex<VertexNameType>> getVertices() {
        return new HashSet<>(incidentMatrix.keySet());
    }

    @Override
    public Set<Vertex<VertexNameType>> getNeighbors(Vertex<VertexNameType> vertex)
            throws VertexNotFoundException {
        if (!incidentMatrix.containsKey(vertex)) {
            throw new VertexNotFoundException();
        }

        HashSet<Vertex<VertexNameType>> result = new HashSet<>();

        for (Edge<VertexNameType, EdgeWeightType> edge : edges) {
            if (edge.getFrom().equals(vertex)) {
                result.add(edge.getTo());
            }
        }

        return result;
    }

    @Override
    public String toString() {
        String result = "";
        Set<Vertex<VertexNameType>> vertices = getVertices();
        int verteciesCount = vertices.size();

        if (verteciesCount > 0) {
            result += "vertices: ";
            for (Vertex<VertexNameType> vertex : vertices) {
                result += vertex;
                if (--verteciesCount > 0) {
                    result += ", ";
                }
            }

            result += "\n";
            int edgesCount = edges.size();

            if (edgesCount > 0) {

                for (Vertex<VertexNameType> vertex : vertices) {
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
