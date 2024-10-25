package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import org.junit.jupiter.api.Test;

import ru.nsu.svirsky.graph.AdjacencyMatrixGraph;
import ru.nsu.svirsky.graph.Edge;
import ru.nsu.svirsky.graph.Graph;
import ru.nsu.svirsky.graph.Vertex;

public class GraphScannerTest {
    @Test
    void test() {
        Graph<String, Integer> graph = new AdjacencyMatrixGraph<String, Integer>();

        assertDoesNotThrow(
                () -> GraphScanner.scan(
                        "res/input.txt", input -> input, Integer::parseInt, graph));

        Graph<String, Integer> graph2 = new AdjacencyMatrixGraph<String, Integer>();
        Vertex<String> vertex1 = new Vertex<>("1");
        Vertex<String> vertex2 = new Vertex<>("2");
        Vertex<String> vertex3 = new Vertex<>("3");
        Vertex<String> vertex4 = new Vertex<>("4");
        graph2.addVertex(vertex1);
        graph2.addVertex(vertex2);
        graph2.addVertex(vertex3);
        graph2.addVertex(vertex4);
        assertDoesNotThrow(() -> {
            graph2.addEdge(new Edge<>(vertex1, vertex2));
            graph2.addEdge(new Edge<>(vertex1, vertex3));
            graph2.addEdge(new Edge<>(vertex1, vertex4, 10));
            graph2.addEdge(new Edge<>(vertex2, vertex4));
            graph2.addEdge(new Edge<>(vertex2, vertex3, 89));
        });

        assertIterableEquals(graph.getVertices(), graph2.getVertices());
    }
}
