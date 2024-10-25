package ru.nsu.svirsky.uitls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import ru.nsu.svirsky.graph.Vertex;
import ru.nsu.svirsky.uitls.exceptions.VertexNotFoundException;

/**
 * Vertex enumeration class.
 *
 * @author Bogdan Svirsky
 */
public class VertexEnumeration<V> {
    private int verticesCount = 0;
    private final HashMap<Vertex<V>, Integer> vertexToIndex;
    private final ArrayList<Vertex<V>> vertices;

    /**
     * Constructor.
     */
    public VertexEnumeration() {
        vertexToIndex = new HashMap<Vertex<V>, Integer>();
        vertices = new ArrayList<>();
    }

    /**
     * Add vertex to enumeration.
     *
     * @param vertex vertex to add
     */
    public void add(Vertex<V> vertex) {
        vertexToIndex.put(vertex, verticesCount);
        vertices.add(vertex);
        verticesCount++;
    }

    /**
     * Removes vertex from graph.
     *
     * @param deletedVertex vertex to remove
     * @throws VertexNotFoundException if enumeration doesn't contain vertex
     */
    public void remove(Vertex<V> deletedVertex) throws VertexNotFoundException {
        if (!contains(deletedVertex)) {
            throw new VertexNotFoundException();
        }

        int deletedIndex = vertexToIndex.get(deletedVertex).intValue();

        vertexToIndex.remove(deletedVertex);
        int vertexIndex;

        vertexToIndex.remove(deletedVertex);
        vertices.remove(deletedIndex);

        for (Vertex<V> vertex : vertexToIndex.keySet()) {
            vertexIndex = vertexToIndex.get(vertex).intValue();

            if (vertexIndex > deletedIndex) {
                vertexToIndex.put(vertex, vertexIndex - 1);
            }
        }

        verticesCount--;
    }

    /**
     * Get index of vertex.
     *
     * @param vertex vertex which index we need
     * @return index
     * @throws VertexNotFoundException if enumeration doesn't contain vertex
     */
    public int get(Vertex<V> vertex) throws VertexNotFoundException {
        if (!contains(vertex)) {
            throw new VertexNotFoundException();
        }

        return vertexToIndex.get(vertex);
    }

    public Vertex<V> get(int index) {
        return vertices.get(index);
    }

    public int getVerticesCount() {
        return verticesCount;
    }

    public Set<Vertex<V>> getVertices() {
        return new HashSet<>(vertices);
    }

    public boolean contains(Vertex<V> vertex) {
        return vertices.contains(vertex);
    }

    /**
     * Method to clear enumeration.
     */
    public void clear() {
        vertexToIndex.clear();
        vertices.clear();
        verticesCount = 0;
    }
}
