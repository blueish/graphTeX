package cs.ubc.ca.graphTeX.ast;

import cs.ubc.ca.graphTeX.visitor.Visitor;

public abstract class AST {
    public abstract <R> R accept(Visitor<R> v);
}
