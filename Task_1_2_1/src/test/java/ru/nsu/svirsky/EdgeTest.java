package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import ru.nsu.svirsky.graph.Edge;
import ru.nsu.svirsky.graph.Vertex;

/**
 * Edge tests.
 *
 * @author Bogdan Svirsky
 */
public class EdgeTest {
    @Test
    void test() {
        Vertex<String> vertex1 = new Vertex<String>("1");
        Vertex<String> vertex2 = new Vertex<String>("2");
        Edge<String, Integer> edge = new Edge<String, Integer>(vertex1, vertex2);

        assertEquals(new Edge<>(vertex1, vertex2), edge);
        assertNotEquals(new Edge<>(vertex1, vertex2, 1), edge);
        assertEquals(new Edge<>(new Vertex<String>("1"), new Vertex<String>("2")), edge);

        assertEquals(edge.toString(), "1 -> 2");
    }
}
