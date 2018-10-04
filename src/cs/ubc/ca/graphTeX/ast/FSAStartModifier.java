package cs.ubc.ca.graphTeX.ast;

import cs.ubc.ca.graphTeX.visitor.Visitor;

// Represents an start state for a Finite State Automata
public class FSAStartModifier extends Relation {
    String node;

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }
}
