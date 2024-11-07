package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import org.junit.jupiter.api.Test;
import ru.nsu.svirsky.graph.Edge;
import ru.nsu.svirsky.graph.Graph;
import ru.nsu.svirsky.graph.Vertex;
import ru.nsu.svirsky.uitls.exceptions.EdgeNotFoundException;
import ru.nsu.svirsky.uitls.exceptions.MultipleEdgesFoundException;
import ru.nsu.svirsky.uitls.exceptions.VertexNotFoundException;

/**
 * Graph tests.
 *
 * @author Bogdan Svirsky
 */
public class GraphTest {
    @Test
    public static void checkVertexMethods(Graph<String, Integer> graph) {
        graph.clear();

        Vertex<String> vertex = new Vertex<String>("1");

        graph.addVertex(vertex);

        assertTrue(graph.getVertices().size() == 1);
        assertTrue(graph.getVertices().contains(vertex));

        assertThrows(
                VertexNotFoundException.class,
                () -> graph.deleteVertex(new Vertex<String>("2")));

        assertDoesNotThrow(() -> graph.deleteVertex(vertex));

        assertTrue(graph.getVertices().size() == 0);
        assertFalse(graph.getVertices().contains(vertex));
    }

    @Test
    public static void checkEdgeMethods(Graph<String, Integer> graph) {
        graph.clear();
        Vertex<String> vertex1 = new Vertex<String>("1");
        Vertex<String> vertex2 = new Vertex<String>("2");
        Edge<String, Integer> edge = new Edge<>(vertex1, vertex2);

        assertThrows(
                VertexNotFoundException.class, () -> graph.addEdge(edge));

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);

        assertDoesNotThrow(() -> graph.addEdge(edge));
        assertThrows(MultipleEdgesFoundException.class, () -> graph.addEdge(edge));

        assertTrue(graph.getEdges().size() == 1);
        assertTrue(graph.getEdges().contains(edge));

        assertThrows(
                VertexNotFoundException.class,
                () -> graph.deleteEdge(new Edge<>(new Vertex<String>("3"), vertex2)));
        assertThrows(
                EdgeNotFoundException.class,
                () -> graph.deleteEdge(new Edge<>(vertex1, vertex2, 1)));
        assertDoesNotThrow(() -> graph.deleteEdge(edge));

        assertTrue(graph.getEdges().size() == 0);
        assertTrue(!graph.getEdges().contains(edge));
    }

    @Test
    public static void clearTest(Graph<String, Integer> graph) {
        Vertex<String> vertex1 = new Vertex<String>("dsdsdasda");
        Vertex<String> vertex2 = new Vertex<String>("adsa");
        Vertex<String> vertex3 = new Vertex<String>("kgkgk");
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex3);

        assertDoesNotThrow(() -> {
            graph.addEdge(new Edge<>(vertex2, vertex3));
            graph.addEdge(new Edge<>(vertex3, vertex1));
            graph.addEdge(new Edge<>(vertex3, vertex2));
        });

        graph.clear();

        assertTrue(graph.getVertices().size() == 0);
        assertTrue(graph.getEdges().size() == 0);
    }

    @Test
    public static void checkNeighbors(Graph<String, Integer> graph) {
        Vertex<String> vertex1 = new Vertex<String>("dsdsdasda");
        Vertex<String> vertex2 = new Vertex<String>("adsa");
        Vertex<String> vertex3 = new Vertex<String>("kgkgk");
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex3);

        assertDoesNotThrow(() -> {
            graph.addEdge(new Edge<>(vertex1, vertex3));
            graph.addEdge(new Edge<>(vertex2, vertex3));
            graph.addEdge(new Edge<>(vertex1, vertex2));
        });

        assertThrows(
                VertexNotFoundException.class,
                () -> graph.getNeighbors(new Vertex<String>("1")));

        assertDoesNotThrow(() -> {
            assertEquals(new HashSet<>(), graph.getNeighbors(vertex3));
        });
    }
}
