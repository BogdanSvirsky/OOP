package ru.nsu.svirsky.graph;

/**
 * Edge class.
 *
 * @author Bogdan Svirsky
 */
public class Edge<V, E extends Number> {
    private Vertex<V> from;
    private Vertex<V> to;
    private E weight = null;

    /**
     * Constructor.
     *
     * @param from vertex from
     * @param to vertex to
     * @param weight weight
     */
    public Edge(Vertex<V> from, Vertex<V> to, E weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Edge(Vertex<V> from, Vertex<V> to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof Edge other) {
            if (this.weight == null) {
                return this.from.equals(other.from) && this.to.equals(other.to)
                        && (other.weight == null);
            } else {
                return this.from.equals(other.from) && this.to.equals(other.to)
                        && this.weight.equals(other.weight);
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return from.toString() + " -> " + to.toString()
                + (weight != null ? " (" + weight.toString() + ")" : "");
    }

    public Vertex<V> getFrom() {
        return from;
    }

    public Vertex<V> getTo() {
        return to;
    }

    public E getWeight() {
        return weight;
    }

    @Override
    protected Edge<V, E> clone() {
        return new Edge<V, E>(from.clone(), to.clone());
    }
}
