package ru.nsu.svirsky.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import ru.nsu.svirsky.uitls.exceptions.EdgeNotFoundException;
import ru.nsu.svirsky.uitls.exceptions.MultipleEdgesFoundException;
import ru.nsu.svirsky.uitls.exceptions.VertexNotFoundException;

/**
 * Adjacency lists graph class.
 *
 * @author Bogdan Svisrky
 */
public class AdjacencyListsGraph<V, E extends Number>
        implements Graph<V, E> {
    HashMap<Vertex<V>, HashSet<Edge<V, E>>> adjacencyLists;
    HashSet<Vertex<V>> vertices;

    public AdjacencyListsGraph() {
        adjacencyLists = new HashMap<>();
        vertices = new HashSet<>();
    }

    /**
     * Constructor.
     *
     * @param vertices vertices from graph construct
     */
    public AdjacencyListsGraph(Iterable<Vertex<V>> vertices) {
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
     * @param edges    edges from graph construct
     * @throws VertexNotFoundException     if vertex not found in graph
     * @throws MultipleEdgesFoundException if edge already exists in graph
     */
    public AdjacencyListsGraph(
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
        adjacencyLists.put(vertex, new HashSet<>());
        vertices.add(vertex);
    }

    @Override
    public void deleteVertex(Vertex<V> deletedVertex) throws VertexNotFoundException {
        if (!adjacencyLists.containsKey(deletedVertex)) {
            throw new VertexNotFoundException();
        }

        adjacencyLists.remove(deletedVertex);
        vertices.remove(deletedVertex);

        HashSet<Edge<V, E>> newList;

        for (Vertex<V> vertex : vertices) {
            newList = new HashSet<>();

            for (Edge<V, E> edge : adjacencyLists.get(vertex)) {
                if (!edge.getTo().equals(deletedVertex)) {
                    newList.add(edge);
                }
            }

            adjacencyLists.put(vertex, newList);
        }
    }

    @Override
    public void addEdge(Edge<V, E> edge)
            throws VertexNotFoundException, MultipleEdgesFoundException {
        Vertex<V> vertexFrom = edge.getFrom();

        if (!adjacencyLists.containsKey(vertexFrom) || !adjacencyLists.containsKey(edge.getTo())) {
            throw new VertexNotFoundException();
        }

        if (adjacencyLists.get(vertexFrom).contains(edge)) {
            throw new MultipleEdgesFoundException();
        }

        adjacencyLists.get(vertexFrom).add(edge);
    }

    @Override
    public void deleteEdge(Edge<V, E> edge)
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
    public Set<Edge<V, E>> getEdges() {
        HashSet<Edge<V, E>> result = new HashSet<>();

        for (Vertex<V> vertex : vertices) {
            for (Edge<V, E> edge : adjacencyLists.get(vertex)) {
                result.add(edge);
            }
        }

        return result;
    }

    @Override
    public Set<Vertex<V>> getVertices() {
        return new HashSet<>(vertices);
    }

    @Override
    public Set<Vertex<V>> getNeighbors(Vertex<V> vertex)
            throws VertexNotFoundException {
        if (!vertices.contains(vertex)) {
            throw new VertexNotFoundException();
        }
        HashSet<Vertex<V>> result = new HashSet<>();

        for (Edge<V, E> edge : adjacencyLists.get(vertex)) {
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

        for (Vertex<V> vertex : vertices) {
            result += vertex + ": ";

            if (adjacencyLists.get(vertex).size() > 0) {
                int remainingEdgesNumber = adjacencyLists.get(vertex).size();

                for (Edge<V, E> edge : adjacencyLists.get(vertex)) {
                    result += edge.getTo()
                            + (edge.getWeight() != null ? " (" + edge.getWeight() + ")" : "");

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
}