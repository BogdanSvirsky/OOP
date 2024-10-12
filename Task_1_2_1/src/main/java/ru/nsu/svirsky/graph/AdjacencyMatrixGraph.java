package ru.nsu.svirsky.graph;

import java.nio.file.Path;
import java.text.ParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

import ru.nsu.svirsky.uitls.Transformer;
import ru.nsu.svirsky.uitls.VertexNotFoundException;

public class AdjacencyMatrixGraph<VertexNameType, EdgeWeightType>
        implements Graph<VertexNameType, EdgeWeightType> {
    private int verticesCount = 0;
    private final HashMap<Vertex<VertexNameType>, Integer> vertexToIndex = new HashMap<Vertex<VertexNameType>, Integer>();
    private final ArrayList<Vertex<VertexNameType>> vertices = new ArrayList<>();

    private ArrayList<ArrayList<Integer>> adjacencyMatrix = new ArrayList<ArrayList<Integer>>();

    public AdjacencyMatrixGraph() {
    }

    public AdjacencyMatrixGraph(Iterable<Vertex<VertexNameType>> vertices) {
        if (vertices == null) {
            return;
        }

        for (Vertex<VertexNameType> vertex : vertices) {
            addVertex(vertex);
        }
    }

    public AdjacencyMatrixGraph(
            Iterable<Vertex<VertexNameType>> vertices, Iterable<Edge<EdgeWeightType>> edges)
            throws VertexNotFoundException {
        this(vertices);

        if (edges == null) {
            return;
        }

        for (Edge<EdgeWeightType> edge : edges) {
            addEdge(edge);
        }
    }

    @Override
    public void addVertex(Vertex<VertexNameType> vertex) {
        vertexToIndex.put(vertex, verticesCount);
        vertices.add(vertex);

        for (ArrayList<Integer> list : adjacencyMatrix) {
            list.add(0);
        }

        ArrayList<Integer> newMatrixRow = new ArrayList<Integer>();
        verticesCount++;

        for (int i = 0; i < verticesCount; i++) {
            newMatrixRow.add(0);
        }

        adjacencyMatrix.add(newMatrixRow);
    }

    @Override
    public void deleteVertex(Vertex<VertexNameType> deletedVertex) throws VertexNotFoundException {
        if (!vertexToIndex.keySet().contains(deletedVertex)) {
            throw new VertexNotFoundException("in AdjacencyMatrixGraph.deleteVertex()");
        }

        int deletedIndex = vertexToIndex.get(deletedVertex).intValue();
        int vertexIndex;

        vertexToIndex.remove(deletedVertex);
        vertices.remove(deletedIndex);

        for (Vertex<VertexNameType> vertex : vertexToIndex.keySet()) {
            vertexIndex = vertexToIndex.get(vertex).intValue();

            if (vertexIndex > deletedIndex) {
                vertexToIndex.put(vertex, vertexIndex - 1);
            }
        }

        for (ArrayList<Integer> list : adjacencyMatrix) {
            list.remove(deletedIndex);
        }

        adjacencyMatrix.remove(deletedIndex);

        verticesCount--;
    }

    @Override
    public ArrayList<Vertex<VertexNameType>> getVertices() {
        return new ArrayList<Vertex<VertexNameType>>(vertices);
    }

    @Override
    public void addEdge(Edge<EdgeWeightType> edge) throws VertexNotFoundException {
        if (!vertexToIndex.keySet().contains(edge.getFrom())
                || !vertexToIndex.keySet().contains(edge.getTo())) {
            throw new VertexNotFoundException("in AdjacencyMatrixGraph.addEdge()");
        }

        adjacencyMatrix.get(vertexToIndex.get(edge.getFrom())).set(vertexToIndex.get(edge.getTo()), 1);
    }

    @Override
    public void deleteEdge(Edge<EdgeWeightType> edge) throws VertexNotFoundException {
        if (!vertexToIndex.keySet().contains(edge.getFrom())
                || !vertexToIndex.keySet().contains(edge.getTo())) {
            throw new VertexNotFoundException("in AdjacencyMatrixGraph.deleteEdge()");
        }

        adjacencyMatrix.get(vertexToIndex.get(edge.getFrom())).set(vertexToIndex.get(edge.getTo()), 0);
    }

    @Override
    public ArrayList<Edge<EdgeWeightType>> getEdges() {
        ArrayList<Edge<EdgeWeightType>> result = new ArrayList<Edge<EdgeWeightType>>();

        for (int i = 0; i < verticesCount; i++) {
            for (int j = 0; j < verticesCount; j++) {
                if (adjacencyMatrix.get(i).get(j) == 1) {
                    result.add(new Edge<>(vertices.get(i).clone(), vertices.get(j).clone()));
                }
            }
        }

        return result;
    }

    @Override
    public ArrayList<Vertex<VertexNameType>> getNeighbors(Vertex<VertexNameType> vertex)
            throws VertexNotFoundException {
        if (!vertexToIndex.keySet().contains(vertex)) {
            throw new VertexNotFoundException("in AdjacencyMatrixGraph.getNeighbors()");
        }

        ArrayList<Integer> matrixRow = adjacencyMatrix.get(vertexToIndex.get(vertex));
        ArrayList<Vertex<VertexNameType>> result = new ArrayList<Vertex<VertexNameType>>();

        for (int i = 0; i < matrixRow.size(); i++) {
            if (matrixRow.get(i) == 1) {
                result.add(vertices.get(i));
            }
        }

        return result;
    }

    /**
     * Input .txt file:
     * First string - all vertex's names, separated by ", ".
     * Next n strings - adjacency matrix (n - count of vertices)
     * for all n vertices in format "1 0 0 1".
     * 
     * @param path        path to the txt file
     * @param transformer vertex name transformer from string to T
     */
    @Override
    public void scanFromFile(String path, Transformer<VertexNameType> trasnformer)
            throws IOException, ParseException {
        verticesCount = 0;
        vertexToIndex.clear();
        vertices.clear();
        adjacencyMatrix.clear();

        Scanner scanner = new Scanner(Path.of(path));

        if (scanner.hasNextLine()) {
            for (String vertexName : scanner.nextLine().split(", ")) {
                addVertex(new Vertex<>(trasnformer.transform(vertexName)));
            }

            for (int i = 0; i < verticesCount; i++) {
                if (!scanner.hasNextLine()) {
                    scanner.close();
                    throw new ParseException(
                            "Can't find matrix row line, AdjacencyMatrixGraph.scanFromFile()",
                            adjacencyMatrix.size());
                }

                for (int j = 0; j < verticesCount; j++) {
                    if (!scanner.hasNextInt()) {
                        scanner.close();
                        throw new ParseException(
                                "Invalid count of elements in matrix row in file",
                                adjacencyMatrix.size());
                    }

                    adjacencyMatrix.get(i).set(j, scanner.nextInt());
                }
            }
        } else {
            scanner.close();
            throw new ParseException(
                    "Can't find first line, AdjacencyMatrixGraph.scanFromFile()", 0);
        }

        scanner.close();
    }
}
