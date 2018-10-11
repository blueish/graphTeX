package cs.ubc.ca.graphTeX.ast;

import cs.ubc.ca.graphTeX.visitor.Visitor;

public class BidirectionalEdge extends Relation {
    private String firstNode;
    private String secondNode;

    public BidirectionalEdge(String firstNode, String secondNode) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
    }


    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }
}
