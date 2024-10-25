package ru.nsu.svirsky.graph;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import ru.nsu.svirsky.uitls.Transformer;
import ru.nsu.svirsky.uitls.exceptions.VertexNotFoundException;

public class AdjacencyListsGraph<VertexNameType, EdgeWeightType extends Number>
        implements Graph<VertexNameType, EdgeWeightType> {
    HashMap<Vertex<VertexNameType>, ArrayList<Edge<VertexNameType, EdgeWeightType>>> adjacencyLists;

    public AdjacencyListsGraph() {
        adjacencyLists = new HashMap<>();
    }

    public AdjacencyListsGraph(Iterable<Vertex<VertexNameType>> vertices) {
        this();

        if (vertices == null) {
            return;
        }

        for (Vertex<VertexNameType> vertex : vertices) {
            addVertex(vertex);
        }
    }

    public AdjacencyListsGraph(
            Iterable<Vertex<VertexNameType>> vertices,
            Iterable<Edge<VertexNameType, EdgeWeightType>> edges)
            throws VertexNotFoundException {
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
        adjacencyLists.put(vertex, new ArrayList<>());
    }

    @Override
    public void deleteVertex(Vertex<VertexNameType> deletedVertex) throws VertexNotFoundException {
        if (!adjacencyLists.containsKey(deletedVertex)) {
            throw new VertexNotFoundException();
        }

        adjacencyLists.remove(deletedVertex);

        ArrayList<Edge<VertexNameType, EdgeWeightType>> newList;

        for (Vertex<VertexNameType> vertex : adjacencyLists.keySet()) {
            newList = new ArrayList<>();

            for (Edge<VertexNameType, EdgeWeightType> edge : adjacencyLists.get(vertex)) {
                if (!edge.getTo().equals(deletedVertex)) {
                    newList.add(edge);
                }
            }

            adjacencyLists.put(vertex, newList);
        }
    }

    @Override
    public void addEdge(Edge<VertexNameType, EdgeWeightType> edge) throws VertexNotFoundException {
        Vertex<VertexNameType> vertexFrom = edge.getFrom();

        if (!adjacencyLists.containsKey(vertexFrom) || !adjacencyLists.containsKey(edge.getTo())) {
            throw new VertexNotFoundException();
        }

        adjacencyLists.get(vertexFrom).add(edge);
    }

    @Override
    public void deleteEdge(Edge<VertexNameType, EdgeWeightType> edge)
            throws VertexNotFoundException {
        if (!adjacencyLists.containsKey(edge.getFrom())
                || !adjacencyLists.containsKey(edge.getTo())) {
            throw new VertexNotFoundException();
        }

        adjacencyLists.get(edge.getFrom()).remove(edge);
    }

    @Override
    public ArrayList<Edge<VertexNameType, EdgeWeightType>> getEdges() {
        ArrayList<Edge<VertexNameType, EdgeWeightType>> result = new ArrayList<>();

        for (Vertex<VertexNameType> vertex : adjacencyLists.keySet()) {
            for (Edge<VertexNameType, EdgeWeightType> edge : adjacencyLists.get(vertex)) {
                result.add(edge);
            }
        }

        return result;
    }

    @Override
    public ArrayList<Vertex<VertexNameType>> getVertices() {
        return new ArrayList<>(adjacencyLists.keySet());
    }

    @Override
    public ArrayList<Vertex<VertexNameType>> getNeighbors(Vertex<VertexNameType> vertex)
            throws VertexNotFoundException {
        ArrayList<Vertex<VertexNameType>> result = new ArrayList<>();

        for (Edge<VertexNameType, EdgeWeightType> edge : adjacencyLists.get(vertex)) {
            result.add(edge.getTo());
        }

        return result;
    }

    @Override
    public void clear() {
        adjacencyLists.clear();
    }

    @Override
    public String toString() {
        String result = "";
        int remainingVerticesCount = adjacencyLists.keySet().size();

        for (Vertex<VertexNameType> vertex : adjacencyLists.keySet()) {
            result += vertex + ": ";
            
            if (adjacencyLists.get(vertex).size() > 0) {
                int edgesCount = adjacencyLists.get(vertex).size();

                for (int i = 0; i < edgesCount; i++) {
                    Edge<VertexNameType, EdgeWeightType> edge = adjacencyLists.get(vertex).get(i);
                    result += edge.getTo() + (edge.getWeight() != null ? " (" + edge.getWeight() + ")" : "");

                    if (i != edgesCount - 1) {
                        result += ", ";
                    }
                }
            } else {
                result += "no edges";
            }

            if (--remainingVerticesCount > 0) {
                result += "\n";
            }
        }

        return result;
    }
}