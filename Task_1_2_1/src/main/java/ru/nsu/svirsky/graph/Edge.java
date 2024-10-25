package ru.nsu.svirsky.graph;

public class Edge<VertexNameType, EdgeWeightType extends Number> {
    private Vertex<VertexNameType> from;
    private Vertex<VertexNameType> to;
    private EdgeWeightType weight = null;

    public Edge(Vertex<VertexNameType> from, Vertex<VertexNameType> to, EdgeWeightType weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Edge(Vertex<VertexNameType> from, Vertex<VertexNameType> to) {
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

    public Vertex<VertexNameType> getFrom() {
        return from;
    }

    public Vertex<VertexNameType> getTo() {
        return to;
    }

    public EdgeWeightType getWeight() {
        return weight;
    }

    @Override
    protected Edge<VertexNameType, EdgeWeightType> clone() {
        return new Edge<VertexNameType, EdgeWeightType>(from.clone(), to.clone());
    }
}
