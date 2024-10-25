package ru.nsu.svirsky.graph;

import java.util.ArrayList;
import java.util.HashMap;

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

        incidentMatrix.get(edge.getFrom()).add(-1);
        incidentMatrix.get(edge.getTo()).add(1);
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
    public ArrayList<Edge<VertexNameType, EdgeWeightType>> getEdges() {
        return new ArrayList<>(edges);
    }

    @Override
    public void clear() {
        incidentMatrix.clear();
        edges.clear();
    }

    @Override
    public ArrayList<Vertex<VertexNameType>> getVertices() {
        return new ArrayList<>(incidentMatrix.keySet());
    }

    @Override
    public ArrayList<Vertex<VertexNameType>> getNeighbors(Vertex<VertexNameType> vertex)
            throws VertexNotFoundException {
        if (!incidentMatrix.containsKey(vertex)) {
            throw new VertexNotFoundException();
        }

        ArrayList<Vertex<VertexNameType>> result = new ArrayList<>();

        for (Edge<VertexNameType, EdgeWeightType> edge : edges) {
            if (edge.getFrom().equals(vertex)) {
                result.add(edge.getTo());
            }
        }

        return result;
    }
}
