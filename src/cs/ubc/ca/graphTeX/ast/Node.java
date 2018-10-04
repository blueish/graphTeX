package cs.ubc.ca.graphTeX.ast;

import cs.ubc.ca.graphTeX.visitor.Visitor;

public class Node extends AST {
    String displayValue;
    String refId;
    String optLabel;

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }
}
