package cs.ubc.ca.graphTeX.visitor;

import cs.ubc.ca.graphTeX.ast.*;

import java.util.HashSet;
import java.util.Set;

public class EvaluateVisitor implements Visitor<String> {
    private static final String NODE_FORMAT_LABEL = "\"%s\"/%s [label=%s]";
    private static final String NODE_FORMAT_NO_LABEL = "\"%s\"/%s";


    @Override
    public String visit(Graph n) {
        StringBuilder val = new StringBuilder("\\documentclass{standalone}\n" +
                "\n" +
                "\\usepackage{tikz}\n" +
                "\\usetikzlibrary{graphs, graphs.standard, quotes}% quotes library is for the [\"\"] edges\n" +
                "\n" +
                "\\begin{document} \n" +
                "\\begin{tikzpicture}[node distance = {1.0cm and 1.5cm}, v/.style = {draw, circle}]\n" +
                "  \\graph[nodes={circle, draw}, grow right=2.25cm, branch down=1.75cm]{ \n");

        //hack
        boolean hasStart = !n.relations.isEmpty() && n.relations.get(0) instanceof FSAStartModifier;

        Set<String> endRefs = new HashSet<>();
        int i = 0;
        for(Relation r : n.relations) {

            //hack
            if (r instanceof FSAEndModifier) {
                FSAEndModifier curr = (FSAEndModifier) r;
                if (curr.node != null) endRefs.add(curr.node.refId);
                continue;
            }

            val.append(r.accept(this));

            if (i < n.relations.size() - 1) {
                val.append(",\n");
            }
            i++;
        }


        val.append("}; \n");

        //hack
        if (hasStart) {
            val.append("\\draw [very thick, white] (first /) circle [radius=1.6mm]; \n");
        }

        for (String refId : endRefs) {
            val.append("\\draw [thin, black] (");
            val.append(refId);
            val.append(") circle [radius=3mm]; \n");
        }

        val.append("\\end{tikzpicture}\n" + "\\end{document}");
        return val.toString();
    }

    @Override
    public String visit(Node n) {
        if (n.optLabel == null || n.optLabel.isEmpty()) {
            return String.format(NODE_FORMAT_NO_LABEL, n.refId, n.displayValue);
        } else {
            return String.format(NODE_FORMAT_LABEL, n.refId, n.displayValue, n.optLabel);
        }
    }

    @Override
    public String visit(FSAStartModifier n) {
        return "{[name=first] /} -> [\"start\"] " + n.node.refId;
    }

    @Override
    public String visit(DirectedEdge n) {
        return n.fromNode.accept(this) + " -> " + n.toNode.accept(this);
    }

    @Override
    public String visit(FSAEndModifier n) {
        return "";
    }

    @Override
    public String visit(BidirectionalEdge n) {
        return n.firstNode.accept(this) + " <-> " + n.secondNode.accept(this);
    }

    @Override
    public String visit(UndirectedEdge n) {
        return n.firstNode.accept(this) + " -- " + n.secondNode.accept(this);
    }

    @Override
    public String visit(Tree n){
        return visit(n.rootNode);
    }

    @Override
    public String visit(TreeNode n){
        StringBuilder val = new StringBuilder("\\documentclass{article}\n" +
                "\\usepackage[margin=1in]{geometry}\n" +
                "\\usepackage[linguistics]{forest}\n" +
                "\n" +
                "\\begin{document} \n" +
                "\\begin{forest}for tree={inner sep=0pt,outer sep=0pt}\n");

        val.append("[");
        visitTreeNodeChildren(n, val);
        val.append("]");

        val.append("\\end{forest}\n");
        val.append("\\end{document}\n");

        return val.toString();
    }

    public void visitTreeNodeChildren(TreeNode n, StringBuilder val){
        val.append(n.displayValue);

        for (TreeNode child : n.children){
            val.append("\n");
            val.append("    [");
            visitTreeNodeChildren(child, val);
        }
        val.append("]");
    }
}
