package ru.nsu.svirsky.graph;

public class Vertex<T> {
    private T name;

    public Vertex(T name) {
        this.name = name;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof Vertex otherVertex) {
            return this.name.equals(otherVertex.name);
        } else {
            return false;
        }
    }

    @Override
    public final String toString() {
        return this.name.toString();
    }

    public T getName() {
        return name;
    }

    @Override
    protected Vertex<T> clone() {
        return new Vertex<T>(name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
