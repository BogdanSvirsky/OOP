package ru.nsu.svirsky.uitls;

import ru.nsu.svirsky.graph.Vertex;
import ru.nsu.svirsky.uitls.exceptions.VertexNotFoundException;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class VertexEnumeration<VertexNameType> {
    private int verticesCount = 0;
    private final HashMap<Vertex<VertexNameType>, Integer> vertexToIndex;
    private final ArrayList<Vertex<VertexNameType>> vertices;

    public VertexEnumeration() {
        vertexToIndex = new HashMap<Vertex<VertexNameType>, Integer>();
        vertices = new ArrayList<>();
    }

    public void add(Vertex<VertexNameType> vertex) {
        vertexToIndex.put(vertex, verticesCount);
        vertices.add(vertex);
        verticesCount++;
    }

    public void remove(Vertex<VertexNameType> deletedVertex) throws VertexNotFoundException {
        if (!contains(deletedVertex)) {
            throw new VertexNotFoundException();
        }

        int deletedIndex = vertexToIndex.get(deletedVertex).intValue();

        vertexToIndex.remove(deletedVertex);
        int vertexIndex;

        vertexToIndex.remove(deletedVertex);
        vertices.remove(deletedIndex);

        for (Vertex<VertexNameType> vertex : vertexToIndex.keySet()) {
            vertexIndex = vertexToIndex.get(vertex).intValue();

            if (vertexIndex > deletedIndex) {
                vertexToIndex.put(vertex, vertexIndex - 1);
            }
        }

        verticesCount--;
    }

    public int get(Vertex<VertexNameType> vertex) throws VertexNotFoundException {
        if (!contains(vertex)) {
            throw new VertexNotFoundException();
        }

        return vertexToIndex.get(vertex);
    }

    public Vertex<VertexNameType> get(int index) {
        return vertices.get(index);
    }

    public int getVerticesCount() {
        return verticesCount;
    }

    public Set<Vertex<VertexNameType>> getVertices() {
        return new HashSet<>(vertices);
    }

    public boolean contains(Vertex<VertexNameType> vertex) {
        return vertices.contains(vertex);
    }

    public void clear() {
        vertexToIndex.clear();
        vertices.clear();
        verticesCount = 0;
    }
}
