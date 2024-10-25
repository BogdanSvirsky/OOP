package ru.nsu.svirsky.graph;

import java.util.Set;
import ru.nsu.svirsky.uitls.exceptions.EdgeNotFoundException;
import ru.nsu.svirsky.uitls.exceptions.MultipleEdgesFoundException;
import ru.nsu.svirsky.uitls.exceptions.VertexNotFoundException;

/**
 * Graph interface.
 *
 * @author Bogdan Svirsky
 */
public interface Graph<V, E extends Number> {

    public void addVertex(Vertex<V> vertex);

    public void deleteVertex(Vertex<V> vertex) throws VertexNotFoundException;
    
    public Set<Vertex<V>> getVertices();

    public void addEdge(Edge<V, E> edge)
            throws VertexNotFoundException, MultipleEdgesFoundException;

    public void deleteEdge(Edge<V, E> edge)
            throws VertexNotFoundException, EdgeNotFoundException;

    public Set<Edge<V, E>> getEdges();

    public Set<Vertex<V>> getNeighbors(Vertex<V> vertex)
            throws VertexNotFoundException;

    public void clear();
}
