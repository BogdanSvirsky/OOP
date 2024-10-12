package ru.nsu.svirsky.graph;

public class Edge<T> {
    private Vertex<?> from;
    private Vertex<?> to;
    private T weight = null;

    public Edge(Vertex<?> from, Vertex<?> to, T weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Edge(Vertex<?> from, Vertex<?> to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof Edge other) {
            return this.from.equals(other.from) && this.to.equals(other.to);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return from.toString() + " -" + (weight != null ? "-(" + weight.toString() + ")-" : "")
            + "> " + to.toString(); 
    }

    public Vertex<?> getFrom() {
        return from;
    }

    public Vertex<?> getTo() {
        return to;
    }

    public T getWeight() {
        return weight;
    }

    @Override
    protected Edge<T> clone() throws CloneNotSupportedException {
        return new Edge<T>(from.clone(), to.clone());
    }
}
