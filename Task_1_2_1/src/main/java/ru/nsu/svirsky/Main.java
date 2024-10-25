package ru.nsu.svirsky;

import java.io.IOException;

import ru.nsu.svirsky.graph.AdjacencyListsGraph;
import ru.nsu.svirsky.graph.AdjacencyMatrixGraph;
import ru.nsu.svirsky.graph.Vertex;
import ru.nsu.svirsky.uitls.exceptions.CycleFoundException;
import ru.nsu.svirsky.uitls.exceptions.GraphException;
import ru.nsu.svirsky.graph.Graph;
import ru.nsu.svirsky.graph.IncidentMatrixGraph;

public class Main {
    public static void main(String[] args) {
        Graph<String, Integer> graph = new AdjacencyMatrixGraph<String, Integer>();

        try {
            GraphScanner.scan("res/input.txt", input -> input, input -> 1, graph);
        } catch (GraphException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }

        System.out.println(graph);

        TopologicalSorter sorter = new TopologicalSorter();

        try {
            for (Vertex v : sorter.sort(graph)) {
                System.out.println(v);
            }
        } catch (CycleFoundException e) {
            System.err.println(e);
        }
    }
}
