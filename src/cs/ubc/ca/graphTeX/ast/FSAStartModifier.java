package cs.ubc.ca.graphTeX.ast;

import cs.ubc.ca.graphTeX.visitor.Visitor;

// Represents an start state for a Finite State Automata
public class FSAStartModifier extends Relation {
    private String node;

    public FSAStartModifier(String node) {
        this.node = node;
    }

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }
}
