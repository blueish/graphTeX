package cs.ubc.ca.graphTeX.ast;

import cs.ubc.ca.graphTeX.visitor.Visitor;

public class Node extends AST {
    public String displayValue;
    public String refId;
    public String optLabel;

    public Node(String displayValue, String refId) {
        this.displayValue = displayValue;
        this.refId = refId;
    }

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return String.format("Node: { value: %s, id: %s }", displayValue, refId);
    }
}
