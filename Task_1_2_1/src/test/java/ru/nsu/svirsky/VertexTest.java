package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import ru.nsu.svirsky.graph.Vertex;

/**
 * Vertex tests class.
 *
 * @author Bogdan Svirsky
 */
public class VertexTest {
    @Test
    void test() {
        assertEquals(new Vertex<String>("1"), new Vertex<String>("1"));
        assertNotEquals(new Vertex<String>("1"), new Vertex<String>("2"));
        assertEquals(new Vertex<>("1").toString(), "1");
    }
}
