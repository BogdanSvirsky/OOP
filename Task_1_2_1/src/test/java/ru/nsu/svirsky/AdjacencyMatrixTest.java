package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import org.junit.jupiter.api.Test;

import ru.nsu.svirsky.graph.AdjacencyMatrixGraph;
import ru.nsu.svirsky.graph.Edge;
import ru.nsu.svirsky.graph.Vertex;
import ru.nsu.svirsky.uitls.exceptions.GraphException;
import ru.nsu.svirsky.uitls.exceptions.MultipleEdgesFoundException;
import ru.nsu.svirsky.uitls.exceptions.VertexNotFoundException;

public class AdjacencyMatrixTest {
    @Test
    void constructorsCheck() {
        Vertex<String> vertex1 = new Vertex<String>("1");
        Vertex<String> vertex2 = new Vertex<String>("2");
        Vertex<String> vertex3 = new Vertex<String>("3");
        HashSet<Vertex<String>> vertices = new HashSet<>();
        vertices.add(vertex1);
        vertices.add(new Vertex<String>("2"));
        vertices.add(new Vertex<String>("3"));

        AdjacencyMatrixGraph<String, Integer> graph = new AdjacencyMatrixGraph<>();

        assertTrue(graph.getVertices().size() == 0);
        assertTrue(graph.getEdges().size() == 0);

        graph = new AdjacencyMatrixGraph<>(vertices);

        assertEquals(vertices, graph.getVertices());

        HashSet<Edge<String, Integer>> edges = new HashSet<>();

        edges.add(
                new Edge<>(vertex1, vertex2));
        edges.add(
                new Edge<>(vertex2, vertex3));
        edges.add(
                new Edge<>(vertex1, vertex3));

        try {
            graph = new AdjacencyMatrixGraph<>(
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
        GraphTest.checkVertexMethods(new AdjacencyMatrixGraph<>());
    }

    @Test
    void chechEdgeMethods() {
        GraphTest.checkEdgeMethods(new AdjacencyMatrixGraph<>());
    }

    @Test
    void clearTest() {
        GraphTest.clearTest(new AdjacencyMatrixGraph<>());
    }

    @Test
    void checkNeighbors() {
        GraphTest.checkNeighbors(new AdjacencyMatrixGraph<>());
    }
}
