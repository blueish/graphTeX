package cs.ubc.ca.graphTeX.ast;

import cs.ubc.ca.graphTeX.visitor.Visitor;

public class Tree extends AST {
    public TreeNode rootNode;

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }
}
