package cs.ubc.ca.graphTeX.ast;

import cs.ubc.ca.graphTeX.visitor.Visitor;

public class BidirectionalEdge extends Relation {
    public String firstNode;
    public String secondNode;

    public BidirectionalEdge(String firstNode, String secondNode) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
    }


    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }
}
