package ru.nsu.svirsky;

import java.io.IOException;
import java.text.ParseException;

import ru.nsu.svirsky.graph.AdjacencyMatrixGraph;
import ru.nsu.svirsky.graph.Edge;
import ru.nsu.svirsky.graph.Vertex;
import ru.nsu.svirsky.graph.Graph;
import ru.nsu.svirsky.graph.TopologicalSorter;
import ru.nsu.svirsky.uitls.CycleFoundException;
import ru.nsu.svirsky.uitls.Transformer;

public class Main {
    public static void main(String[] args) {
        Graph<String, Integer> graph = new AdjacencyMatrixGraph<String, Integer>();

        try {
            graph.scanFromFile("input.txt", new Transformer<String>() {
                @Override
                public String transform(String input) {
                    return input;
                }
            });
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }

        for (Edge<Integer> edge : graph.getEdges()) {
            System.out.println(edge);
        }

        TopologicalSorter sorter = new TopologicalSorter();

        try {
            for (Vertex v : sorter.sort(graph)) {
                System.out.println(v);
            }
        } catch (CycleFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
