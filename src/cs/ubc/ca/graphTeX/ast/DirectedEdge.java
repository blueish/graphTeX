package cs.ubc.ca.graphTeX.ast;

import cs.ubc.ca.graphTeX.visitor.Visitor;

public class DirectedEdge extends Relation {
    String fromNode;
    String toNode;

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }
}
