package cs.ubc.ca.graphTeX.ast;

import cs.ubc.ca.graphTeX.visitor.Visitor;

public class DirectedEdge extends Relation {
    private String fromNode;
    private String toNode;

    public DirectedEdge(String fromNode, String toNode) {
        this.fromNode = fromNode;
        this.toNode = toNode;
    }

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }
}
