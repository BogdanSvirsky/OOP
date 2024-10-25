package ru.nsu.svirsky.graph;

import java.util.ArrayList;

import ru.nsu.svirsky.uitls.exceptions.EdgeNotFoundException;
import ru.nsu.svirsky.uitls.exceptions.MultipleEdgesFoundException;
import ru.nsu.svirsky.uitls.exceptions.VertexNotFoundException;

public interface Graph<VertexNameType, EdgeWeightType extends Number> {

    public void addVertex(Vertex<VertexNameType> vertex);

    public void deleteVertex(Vertex<VertexNameType> vertex) throws VertexNotFoundException;

    public ArrayList<Vertex<VertexNameType>> getVertices();

    public void addEdge(Edge<VertexNameType, EdgeWeightType> edge)
            throws VertexNotFoundException, MultipleEdgesFoundException;

    public void deleteEdge(Edge<VertexNameType, EdgeWeightType> edge)
            throws VertexNotFoundException, EdgeNotFoundException;

    public ArrayList<Edge<VertexNameType, EdgeWeightType>> getEdges();

    public ArrayList<Vertex<VertexNameType>> getNeighbors(Vertex<VertexNameType> vertex)
            throws VertexNotFoundException;

    public void clear();
}
