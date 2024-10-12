package ru.nsu.svirsky.graph;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import ru.nsu.svirsky.uitls.VertexNotFoundException;
import ru.nsu.svirsky.uitls.Transformer;

public interface Graph<VertexNameType, EdgeWeightType> {
    public void addVertex(Vertex<VertexNameType> vertex);

    public void deleteVertex(Vertex<VertexNameType> vertex) throws VertexNotFoundException;

    public ArrayList<Vertex<VertexNameType>> getVertices();

    public void addEdge(Edge<EdgeWeightType> edge) throws VertexNotFoundException;

    public void deleteEdge(Edge<EdgeWeightType> edge) throws VertexNotFoundException;

    public ArrayList<Edge<EdgeWeightType>> getEdges();

    public ArrayList<Vertex<VertexNameType>> getNeighbors(Vertex<VertexNameType> vertex) throws VertexNotFoundException;

    public void scanFromFile(String path, Transformer<VertexNameType> trasnformer)
            throws IOException, ParseException;
}
