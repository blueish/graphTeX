package cs.ubc.ca.graphTeX.visitor;

import cs.ubc.ca.graphTeX.ast.*;
import cs.ubc.ca.graphTeX.util.IndentingPrinter;

public class PrintVisitor implements Visitor<Void> {
    private IndentingPrinter printer;

    public PrintVisitor(IndentingPrinter printer) {
        this.printer = printer;
    }

    @Override
    public Void visit(Graph n) {
        printer.println("Graph {");
        printer.indent();
        printer.println("Nodes {");

        printer.indent();
        n.nodes.forEach(node ->
                node.accept(this)
        );
        printer.outdent();

        printer.println("}");

        printer.println("Relations {");
        printer.indent();
        n.relations.forEach(node ->
                node.accept(this)
        );
        printer.outdent();
        printer.println("}");

        printer.outdent();
        printer.println("}");
        return null;
    }

    @Override
    public Void visit(Node n) {
        printer.println("Node { TODO }");
        return null;
    }

    @Override
    public Void visit(FSAStartModifier n) {
        printer.println("StartMod {");
        printer.println(n);
        printer.println("}");
        return null;
    }

    @Override
    public Void visit(DirectedEdge n) {
        printer.println("DirectedEdge {");
        printer.indent();
        printer.println("from: " + n.fromNode);
        printer.println("to:   " + n.toNode);
        printer.outdent();
        printer.println("}");
        return null;
    }

    @Override
    public Void visit(FSAEndModifier n) {
        printer.println("EndMod {");
        printer.println(n);
        printer.println("}");
        return null;
    }

    @Override
    public Void visit(BidirectionalEdge n) {
        printer.println("BiDirectionalEdge {");
        printer.indent();
        printer.println("first:  " + n.firstNode);
        printer.println("second: " + n.secondNode);
        printer.outdent();
        printer.println("}");
        return null;
    }
}
