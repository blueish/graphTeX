package cs.ubc.ca.graphTeX.ast;

import cs.ubc.ca.graphTeX.visitor.Visitor;

public class FSAEndModifier extends Relation {
    public String node;

    public FSAEndModifier(String node) {
        this.node = node;
    }

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }
}
