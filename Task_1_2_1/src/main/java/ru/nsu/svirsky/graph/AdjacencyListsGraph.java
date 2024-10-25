package ru.nsu.svirsky.graph;

import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

import ru.nsu.svirsky.uitls.exceptions.EdgeNotFoundException;
import ru.nsu.svirsky.uitls.exceptions.MultipleEdgesFoundException;
import ru.nsu.svirsky.uitls.exceptions.VertexNotFoundException;

public class AdjacencyListsGraph<VertexNameType, EdgeWeightType extends Number>
        implements Graph<VertexNameType, EdgeWeightType> {
    HashMap<Vertex<VertexNameType>, HashSet<Edge<VertexNameType, EdgeWeightType>>> adjacencyLists;
    HashSet<Vertex<VertexNameType>> vertices;

    public AdjacencyListsGraph() {
        adjacencyLists = new HashMap<>();
        vertices = new HashSet<>();
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
        adjacencyLists.put(vertex, new HashSet<>());
        vertices.add(vertex);
    }

    @Override
    public void deleteVertex(Vertex<VertexNameType> deletedVertex) throws VertexNotFoundException {
        if (!adjacencyLists.containsKey(deletedVertex)) {
            throw new VertexNotFoundException();
        }

        adjacencyLists.remove(deletedVertex);
        vertices.remove(deletedVertex);

        HashSet<Edge<VertexNameType, EdgeWeightType>> newList;

        for (Vertex<VertexNameType> vertex : vertices) {
            newList = new HashSet<>();

            for (Edge<VertexNameType, EdgeWeightType> edge : adjacencyLists.get(vertex)) {
                if (!edge.getTo().equals(deletedVertex)) {
                    newList.add(edge);
                }
            }

            adjacencyLists.put(vertex, newList);
        }
    }

    @Override
    public void addEdge(Edge<VertexNameType, EdgeWeightType> edge)
            throws VertexNotFoundException, MultipleEdgesFoundException {
        Vertex<VertexNameType> vertexFrom = edge.getFrom();

        if (!adjacencyLists.containsKey(vertexFrom) || !adjacencyLists.containsKey(edge.getTo())) {
            throw new VertexNotFoundException();
        }

        if (adjacencyLists.get(vertexFrom).contains(edge)) {
            throw new MultipleEdgesFoundException();
        }

        adjacencyLists.get(vertexFrom).add(edge);
    }

    @Override
    public void deleteEdge(Edge<VertexNameType, EdgeWeightType> edge)
            throws VertexNotFoundException, EdgeNotFoundException {
        if (!adjacencyLists.containsKey(edge.getFrom())
                || !adjacencyLists.containsKey(edge.getTo())) {
            throw new VertexNotFoundException();
        }

        if (!adjacencyLists.get(edge.getFrom()).contains(edge)) {
            throw new EdgeNotFoundException();
        }

        adjacencyLists.get(edge.getFrom()).remove(edge);
    }

    @Override
    public Set<Edge<VertexNameType, EdgeWeightType>> getEdges() {
        HashSet<Edge<VertexNameType, EdgeWeightType>> result = new HashSet<>();

        for (Vertex<VertexNameType> vertex : vertices) {
            for (Edge<VertexNameType, EdgeWeightType> edge : adjacencyLists.get(vertex)) {
                result.add(edge);
            }
        }

        return result;
    }

    @Override
    public Set<Vertex<VertexNameType>> getVertices() {
        return new HashSet<>(vertices);
    }

    @Override
    public Set<Vertex<VertexNameType>> getNeighbors(Vertex<VertexNameType> vertex)
            throws VertexNotFoundException {
        if (!vertices.contains(vertex)) {
            throw new VertexNotFoundException();
        }
        HashSet<Vertex<VertexNameType>> result = new HashSet<>();

        for (Edge<VertexNameType, EdgeWeightType> edge : adjacencyLists.get(vertex)) {
            result.add(edge.getTo());
        }

        return result;
    }

    @Override
    public void clear() {
        adjacencyLists.clear();
        vertices.clear();
    }

    @Override
    public String toString() {
        String result = "";
        int remainingVerticesCount = vertices.size();

        for (Vertex<VertexNameType> vertex : vertices) {
            result += vertex + ": ";

            if (adjacencyLists.get(vertex).size() > 0) {
                int remainingEdgesNumber = adjacencyLists.get(vertex).size();

                for (Edge<VertexNameType, EdgeWeightType> edge : adjacencyLists.get(vertex)) {
                    result += edge.getTo() + (edge.getWeight() != null ? " (" + edge.getWeight() + ")" : "");

                    if (--remainingEdgesNumber != 0) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof Graph other) {
            return this.vertices.containsAll(other.getVertices())
            && other.getVertices().containsAll(this.vertices)
                    && this.getEdges().containsAll(other.getEdges())
                    && other.getEdges().containsAll(this.getEdges());
        } else {
            return false;
        }
    }
}