package cs.ubc.ca.graphTeX.ast;

import cs.ubc.ca.graphTeX.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class Graph extends AST {
    public List<Node> nodes = new ArrayList<>();
    public List<Relation> relations = new ArrayList<>();
    public String colourToMark = "";
    public String valueToMark = "";

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }
}
