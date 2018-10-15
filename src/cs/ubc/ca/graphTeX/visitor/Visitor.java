package cs.ubc.ca.graphTeX.visitor;

import cs.ubc.ca.graphTeX.ast.*;

public interface Visitor<R> {
    public R visit(Graph n);
    public R visit(Node n);
    public R visit(FSAStartModifier n);
    public R visit(DirectedEdge n);
    public R visit(FSAEndModifier n);
    public R visit(BidirectionalEdge n);
    public R visit(UndirectedEdge n);
    public R visit(Tree n);
    public R visit(TreeNode n);
}
