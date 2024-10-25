package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import org.junit.jupiter.api.Test;

import ru.nsu.svirsky.graph.IncidentMatrixGraph;
import ru.nsu.svirsky.graph.IncidentMatrixGraph;
import ru.nsu.svirsky.graph.Edge;
import ru.nsu.svirsky.graph.Vertex;
import ru.nsu.svirsky.uitls.exceptions.GraphException;
import ru.nsu.svirsky.uitls.exceptions.MultipleEdgesFoundException;
import ru.nsu.svirsky.uitls.exceptions.VertexNotFoundException;

public class IncidentMatrixTest {
    @Test
    void constructorsCheck() {
        Vertex<String> vertex1 = new Vertex<String>("1");
        Vertex<String> vertex2 = new Vertex<String>("2");
        Vertex<String> vertex3 = new Vertex<String>("3");
        HashSet<Vertex<String>> vertices = new HashSet<>();
        vertices.add(vertex1);
        vertices.add(new Vertex<String>("2"));
        vertices.add(new Vertex<String>("3"));

        IncidentMatrixGraph<String, Integer> graph = new IncidentMatrixGraph<>();

        assertTrue(graph.getVertices().size() == 0);
        assertTrue(graph.getEdges().size() == 0);

        graph = new IncidentMatrixGraph<>(vertices);

        assertEquals(vertices, graph.getVertices());

        HashSet<Edge<String, Integer>> edges = new HashSet<>();

        edges.add(
                new Edge<>(vertex1, vertex2));
        edges.add(
                new Edge<>(vertex2, vertex3));
        edges.add(
                new Edge<>(vertex1, vertex3));

        try {
            graph = new IncidentMatrixGraph<>(
                    vertices,
                    edges);
        } catch (GraphException e) {
            assertFalse(e instanceof VertexNotFoundException);
            assertFalse(e instanceof MultipleEdgesFoundException);
        }

        assertEquals(graph.getEdges(), edges);
    }

    @Test
    void chechVertexMethods() {
        GraphTest.checkVertexMethods(new IncidentMatrixGraph<>());
    }

    @Test
    void chechEdgeMethods() {
        GraphTest.checkEdgeMethods(new IncidentMatrixGraph<>());
    }

    @Test
    void clearTest() {
        GraphTest.clearTest(new IncidentMatrixGraph<>());
    }

    @Test
    void checkNeighbors() {
        GraphTest.checkNeighbors(new IncidentMatrixGraph<>());
    }
}
