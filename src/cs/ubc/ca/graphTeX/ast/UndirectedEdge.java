package cs.ubc.ca.graphTeX.ast;

import cs.ubc.ca.graphTeX.visitor.Visitor;

public class UndirectedEdge extends Relation {
    public String firstNode;
    public String secondNode;

    public UndirectedEdge(String firstNode, String secondNode) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
    }


    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }
}
