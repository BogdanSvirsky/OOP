package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import ru.nsu.svirsky.graph.AdjacencyListsGraph;
import ru.nsu.svirsky.graph.Edge;
import ru.nsu.svirsky.graph.Graph;
import ru.nsu.svirsky.graph.Vertex;
import ru.nsu.svirsky.uitls.exceptions.CycleFoundException;

public class ToposortTest {
    @Test
    void test() {
        Graph<String, Integer> graph = new AdjacencyListsGraph<>();
        Vertex<String> vertex1 = new Vertex<String>("1");
        Vertex<String> vertex2 = new Vertex<String>("2");
        Vertex<String> vertex3 = new Vertex<String>("3");
        ArrayList<Vertex<String>> vertices = new ArrayList<>();
        vertices.add(vertex1);
        vertices.add(vertex2);
        vertices.add(vertex3);

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex3);

        assertDoesNotThrow(() -> {
            graph.addEdge(new Edge<>(vertex1, vertex2));
            graph.addEdge(new Edge<>(vertex2, vertex3));
            graph.addEdge(new Edge<>(vertex1, vertex3));
        });

        try {
            assertEquals((new TopologicalSorter()).sort(graph), vertices);
        } catch (CycleFoundException e) {
            assertTrue(false);
        }

        assertDoesNotThrow(() -> graph.addEdge(new Edge<>(vertex3, vertex2)));
        
        assertThrows(
                CycleFoundException.class,
                () -> (new TopologicalSorter()).sort(graph));
    }
}
