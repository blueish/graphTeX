package cs.ubc.ca.graphTeX.ast;

import cs.ubc.ca.graphTeX.visitor.Visitor;

public class DirectedEdge extends Relation {
    public Node fromNode;
    public Node toNode;

    public DirectedEdge(Node fromNode, Node toNode) {
        this.fromNode = fromNode;
        this.toNode = toNode;
    }

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }
}
