package ru.nsu.svirsky.graph;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import ru.nsu.svirsky.uitls.VertexEnumeration;
import ru.nsu.svirsky.uitls.exceptions.EdgeNotFoundException;
import ru.nsu.svirsky.uitls.exceptions.MultipleEdgesFoundException;
import ru.nsu.svirsky.uitls.exceptions.VertexNotFoundException;

public class AdjacencyMatrixGraph<VertexNameType, EdgeWeightType extends Number>
        implements Graph<VertexNameType, EdgeWeightType> {
    private VertexEnumeration<VertexNameType> vertexEnumeration;
    private ArrayList<ArrayList<Edge<VertexNameType, EdgeWeightType>>> adjacencyMatrix;

    public AdjacencyMatrixGraph() {
        vertexEnumeration = new VertexEnumeration<VertexNameType>();
        adjacencyMatrix = new ArrayList<ArrayList<Edge<VertexNameType, EdgeWeightType>>>();
    }

    public AdjacencyMatrixGraph(Iterable<Vertex<VertexNameType>> vertices) {
        this();

        if (vertices == null) {
            return;
        }

        for (Vertex<VertexNameType> vertex : vertices) {
            addVertex(vertex);
        }
    }

    public AdjacencyMatrixGraph(
            Iterable<Vertex<VertexNameType>> vertices, Iterable<Edge<VertexNameType, EdgeWeightType>> edges)
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
        vertexEnumeration.add(vertex);

        for (ArrayList<Edge<VertexNameType, EdgeWeightType>> list : adjacencyMatrix) {
            list.add(null);
        }

        ArrayList<Edge<VertexNameType, EdgeWeightType>> newMatrixRow = new ArrayList<>();
        int verticesCount = vertexEnumeration.getVerticesCount();

        for (int i = 0; i < verticesCount; i++) {
            newMatrixRow.add(null);
        }

        adjacencyMatrix.add(newMatrixRow);
    }

    @Override
    public void deleteVertex(Vertex<VertexNameType> deletedVertex) throws VertexNotFoundException {
        int deletedIndex = vertexEnumeration.get(deletedVertex);

        vertexEnumeration.remove(deletedVertex);

        for (ArrayList<Edge<VertexNameType, EdgeWeightType>> list : adjacencyMatrix) {
            list.remove(deletedIndex);
        }

        adjacencyMatrix.remove(deletedIndex);
    }

    @Override
    public Set<Vertex<VertexNameType>> getVertices() {
        return vertexEnumeration.getVertices();
    }

    @Override
    public void addEdge(Edge<VertexNameType, EdgeWeightType> edge)
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
    public void deleteEdge(Edge<VertexNameType, EdgeWeightType> edge)
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
    public Set<Edge<VertexNameType, EdgeWeightType>> getEdges() {
        HashSet<Edge<VertexNameType, EdgeWeightType>> result = new HashSet<>();

        for (ArrayList<Edge<VertexNameType, EdgeWeightType>> matrixRow : adjacencyMatrix) {
            for (Edge<VertexNameType, EdgeWeightType> edge : matrixRow) {
                if (edge != null) {
                    result.add(edge);
                }
            }
        }

        return result;
    }

    @Override
    public Set<Vertex<VertexNameType>> getNeighbors(Vertex<VertexNameType> vertex)
            throws VertexNotFoundException {
        if (!vertexEnumeration.contains(vertex)) {
            throw new VertexNotFoundException("in AdjacencyMatrixGraph.getNeighbors()");
        }

        ArrayList<Edge<VertexNameType, EdgeWeightType>> matrixRow = adjacencyMatrix.get(vertexEnumeration.get(vertex));
        HashSet<Vertex<VertexNameType>> result = new HashSet<>();

        for (Edge<VertexNameType, EdgeWeightType> edge : matrixRow) {
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
}
