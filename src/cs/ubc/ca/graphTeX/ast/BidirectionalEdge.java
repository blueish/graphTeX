package cs.ubc.ca.graphTeX.ast;

import cs.ubc.ca.graphTeX.visitor.Visitor;

public class BidirectionalEdge extends Relation {
    String firstNode;
    String secondNode;


    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }
}
