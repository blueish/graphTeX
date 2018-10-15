package cs.ubc.ca.graphTeX.ast;

import cs.ubc.ca.graphTeX.visitor.Visitor;

public class UndirectedEdge extends Relation {
    public Node firstNode;
    public Node secondNode;

    public UndirectedEdge(Node firstNode, Node secondNode) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
    }


    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }
}
