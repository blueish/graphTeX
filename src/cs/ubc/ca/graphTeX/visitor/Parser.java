package cs.ubc.ca.graphTeX.visitor;

import cs.ubc.ca.graphTeX.ast.*;
import cs.ubc.ca.graphTeX.tokenize.Tokenizer;

public class Parser {
    private Tokenizer tokenizer;

    public Parser() {
        this.tokenizer = Tokenizer.getTokenizer();
    }

    public AST parse() {
        Graph graph = new Graph();
        tokenizer.getAndCheckNext("graph");
        tokenizer.getAndCheckNext("{");
        while (!tokenizer.checkToken(("}"))) {
            // assume nodes come before relations, so consume the node definitions
            tokenizer.getAndCheckNext("nodes");
            tokenizer.getAndCheckNext(":");
            tokenizer.getAndCheckNext("[");
            while (!tokenizer.checkToken("]")) {
                Node newNode = new Node();
                // todo: parse nodes
                graph.nodes.add(newNode);
            }
            tokenizer.getAndCheckNext("]");

            // now parse all relations
            tokenizer.getAndCheckNext("relations");
            tokenizer.getAndCheckNext(":");
            tokenizer.getAndCheckNext("[");
            while (!tokenizer.checkToken("]")) {
                Relation newRelation;

                // All our relations are defined of the form (<op>, <param>*), so consume the beginning
                tokenizer.getAndCheckNext("(");
                String op = tokenizer.getNext();
                switch (op) {
                    case "->":
                        newRelation = new DirectedEdge(tokenizer.getNext(), tokenizer.getNext());
                        break;
                    case "<-":
                        // Get the next two tokens and swap them (just desurgaring the <- to a ->
                        String toNode = tokenizer.getNext();
                        String fromNode = tokenizer.getNext();
                        newRelation = new DirectedEdge(toNode, fromNode);
                        break;
                    case "<->":
                        newRelation = new BidirectionalEdge(tokenizer.getNext(), tokenizer.getNext());
                        break;
                    default:
                        throw new RuntimeException(String.format("Invalid relation operation: %s ", op));
                }
                tokenizer.getAndCheckNext(")");

                graph.relations.add(newRelation);
            }
            tokenizer.getAndCheckNext("]");
        }
        return graph;
    }
}
