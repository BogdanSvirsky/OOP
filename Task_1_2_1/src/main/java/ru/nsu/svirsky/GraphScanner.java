package ru.nsu.svirsky;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.HashMap;

import ru.nsu.svirsky.graph.Edge;
import ru.nsu.svirsky.graph.Graph;
import ru.nsu.svirsky.graph.Vertex;
import ru.nsu.svirsky.uitls.Transformer;
import ru.nsu.svirsky.uitls.exceptions.MultipleEdgesFoundException;
import ru.nsu.svirsky.uitls.exceptions.VertexNotFoundException;

/**
 * Class for graph scanning
 * 
 * @author Bogdan Svirsky
 */
public class GraphScanner {
    /**
     * First string is a integer N - count of vertices.
     * Next N strings is a vertex's names.
     * After that string with integer M - count of edges.
     * Next M strings is a vertex's edges in format "<name> -> <name>"
     * and optional part " = <weight>" with edge's weight.
     * 
     * @param <VertexNameType>  vertex name type
     * @param <EdgeWeightType>  edge weight type
     * @param path              path to file
     * @param nameTransformer   transformer from string to VertexNameType
     * @param weightTransformer transformer from string to EdgeWeightType
     * @param graph             graph, that will be cleared and created by file
     * @throws IOException             some IO troubles
     * @throws VertexNotFoundException if you have a edge with vertex, that doesn't
     *                                 exist
     */
    public static <VertexNameType, EdgeWeightType extends Number> void scan(
            String path,
            Transformer<VertexNameType> nameTransformer,
            Transformer<EdgeWeightType> weightTransformer,
            Graph<VertexNameType, EdgeWeightType> graph)
            throws IOException, VertexNotFoundException, MultipleEdgesFoundException {
        File file = new File(path);
        HashMap<String, Vertex<VertexNameType>> stringToVertex = new HashMap<>();

        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        graph.clear();

        Scanner scanner = new Scanner(file);

        int verteciesCount = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < verteciesCount; i++) {
            String vertexName = scanner.nextLine();
            stringToVertex.put(
                    vertexName, new Vertex<VertexNameType>(nameTransformer.transform(vertexName)));
            graph.addVertex(stringToVertex.get(vertexName));
        }

        int edgesCount = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < edgesCount; i++) {
            String[] elements = scanner.nextLine().split(" = ");
            String[] tokens = elements[0].split(" ");
            
            if (tokens.length != 3) {
                throw new NoSuchElementException();
            }

            if (elements.length == 1) {
                graph.addEdge(
                        new Edge<>(
                                stringToVertex.get(tokens[0]),
                                stringToVertex.get(tokens[2])));
            } else if (elements.length == 2) {
                graph.addEdge(
                        new Edge<>(
                                stringToVertex.get(tokens[0]),
                                stringToVertex.get(tokens[2]),
                                weightTransformer.transform(elements[1])));
            } else {
                throw new NoSuchElementException();
            }
        }

        scanner.close();
    }
}