package ru.nsu.svirsky.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import ru.nsu.svirsky.uitls.VertexEnumeration;
import ru.nsu.svirsky.uitls.exceptions.EdgeNotFoundException;
import ru.nsu.svirsky.uitls.exceptions.MultipleEdgesFoundException;
import ru.nsu.svirsky.uitls.exceptions.VertexNotFoundException;

/**
 * Adjacency matrix graph.
 *
 * @author Bogdan Svirsky
 */
public class AdjacencyMatrixGraph<V, E extends Number>
        implements Graph<V, E> {
    private VertexEnumeration<V> vertexEnumeration;
    private ArrayList<ArrayList<Edge<V, E>>> adjacencyMatrix;

    public AdjacencyMatrixGraph() {
        vertexEnumeration = new VertexEnumeration<V>();
        adjacencyMatrix = new ArrayList<ArrayList<Edge<V, E>>>();
    }

    /**
     * Constructor.
     *
     * @param vertices vertices from graph construct
     */
    public AdjacencyMatrixGraph(Iterable<Vertex<V>> vertices) {
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
    public AdjacencyMatrixGraph(
            Iterable<Vertex<V>> vertices, Iterable<Edge<V, E>> edges)
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
        vertexEnumeration.add(vertex);

        for (ArrayList<Edge<V, E>> list : adjacencyMatrix) {
            list.add(null);
        }

        ArrayList<Edge<V, E>> newMatrixRow = new ArrayList<>();
        int verticesCount = vertexEnumeration.getVerticesCount();

        for (int i = 0; i < verticesCount; i++) {
            newMatrixRow.add(null);
        }

        adjacencyMatrix.add(newMatrixRow);
    }

    @Override
    public void deleteVertex(Vertex<V> deletedVertex) throws VertexNotFoundException {
        int deletedIndex = vertexEnumeration.get(deletedVertex);

        vertexEnumeration.remove(deletedVertex);

        for (ArrayList<Edge<V, E>> list : adjacencyMatrix) {
            list.remove(deletedIndex);
        }

        adjacencyMatrix.remove(deletedIndex);
    }

    @Override
    public Set<Vertex<V>> getVertices() {
        return vertexEnumeration.getVertices();
    }

    @Override
    public void addEdge(Edge<V, E> edge)
            throws VertexNotFoundException, MultipleEdgesFoundException {
        if (!vertexEnumeration.contains(edge.getFrom())
                || !vertexEnumeration.contains(edge.getTo())) {
            throw new VertexNotFoundException();
        }

        if (adjacencyMatrix
                .get(vertexEnumeration.get(edge.getFrom()))
                .get(vertexEnumeration.get(edge.getTo())) != null) {
            throw new MultipleEdgesFoundException();
        }

        adjacencyMatrix
                .get(vertexEnumeration.get(edge.getFrom()))
                .set(vertexEnumeration.get(edge.getTo()), edge);
    }

    @Override
    public void deleteEdge(Edge<V, E> edge)
            throws VertexNotFoundException, EdgeNotFoundException {
        if (!vertexEnumeration.contains(edge.getFrom())
                || !vertexEnumeration.contains(edge.getTo())) {
            throw new VertexNotFoundException();
        }

        if (!adjacencyMatrix
                .get(vertexEnumeration.get(edge.getFrom()))
                .get(vertexEnumeration.get(edge.getTo())).equals(edge)) {
            throw new EdgeNotFoundException();
        }

        adjacencyMatrix
                .get(vertexEnumeration.get(edge.getFrom()))
                .set(vertexEnumeration.get(edge.getTo()), null);
    }

    @Override
    public Set<Edge<V, E>> getEdges() {
        HashSet<Edge<V, E>> result = new HashSet<>();

        for (ArrayList<Edge<V, E>> matrixRow : adjacencyMatrix) {
            for (Edge<V, E> edge : matrixRow) {
                if (edge != null) {
                    result.add(edge);
                }
            }
        }

        return result;
    }

    @Override
    public Set<Vertex<V>> getNeighbors(Vertex<V> vertex)
            throws VertexNotFoundException {
        if (!vertexEnumeration.contains(vertex)) {
            throw new VertexNotFoundException("in AdjacencyMatrixGraph.getNeighbors()");
        }

        ArrayList<Edge<V, E>> matrixRow = adjacencyMatrix.get(vertexEnumeration.get(vertex));
        HashSet<Vertex<V>> result = new HashSet<>();

        for (Edge<V, E> edge : matrixRow) {
            if (edge != null) {
                result.add(edge.getTo());
            }
        }

        return result;
    }

    @Override
    public String toString() {
        String result = "";
        int verticesCount = vertexEnumeration.getVerticesCount();

        if (vertexEnumeration.getVerticesCount() > 0) {
            result = "vertices: ";

            for (int i = 0; i < verticesCount; i++) {
                result += vertexEnumeration.get(i);

                if (i != verticesCount - 1) {
                    result += ", ";
                }
            }

            result += "\n";

            for (int i = 0; i < verticesCount; i++) {
                for (int j = 0; j < verticesCount; j++) {
                    result += (adjacencyMatrix.get(i).get(j) == null ? "0" : "1");

                    if (j != verticesCount - 1) {
                        result += " ";
                    }
                }

                if (i != verticesCount - 1) {
                    result += "\n";
                }
            }
        } else {
            result = "no vertices, no edges";
        }
        return result;
    }

    @Override
    public void clear() {
        vertexEnumeration.clear();
        adjacencyMatrix.clear();
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
