package cs.ubc.ca.graphTeX.ast;

import cs.ubc.ca.graphTeX.visitor.Visitor;

import java.util.List;

public class TreeNode extends AST {
    public String displayValue;
    public List<TreeNode> children;

    public TreeNode(String displayValue) {
        this.displayValue = displayValue;
    }

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }

    // TODO
    @Override
    public String toString() {
        return String.format("TreeNode: { value: %s, children: %s }", displayValue, children);
    }
}
