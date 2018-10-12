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
        tokenizer.getAndCheckNext("\\{");

        while (!tokenizer.checkToken(("\\}"))) {
            // assume nodes come before relations, so consume the node definitions
            tokenizer.getAndCheckNext("nodes");
            tokenizer.getAndCheckNext("\\[");

            while (!tokenizer.checkToken("\\]")) {
                Node newNode = new Node();

                // TODO: parse nodes
                System.out.println("skipping a token while in todo state");
                System.out.println(tokenizer.getNext());

//                graph.nodes.add(newNode);
            }

            tokenizer.getAndCheckNext("\\]");

            // now parse all relations
            tokenizer.getAndCheckNext("relations");
            tokenizer.getAndCheckNext("\\[");
            while (!tokenizer.checkToken("\\]")) {
                Relation newRelation;

                // All our relations are defined of the form (<op>, <param>*), so consume the beginning
                tokenizer.getAndCheckNext("\\(");
                String op = tokenizer.getNext();
                tokenizer.getAndCheckNext(",");
                switch (op) {
                    case "->":
                        String fromNode = tokenizer.getNext();
                        tokenizer.getAndCheckNext(",");
                        String toNode = tokenizer.getNext();
                        newRelation = new DirectedEdge(fromNode, toNode);
                        break;
                    case "<-":
                        // Get the next two tokens and swap them (just desurgaring the <- to a ->
                        toNode = tokenizer.getNext();
                        tokenizer.getAndCheckNext(",");
                        fromNode = tokenizer.getNext();
                        newRelation = new DirectedEdge(fromNode, toNode);
                        break;
                    case "--":
                        String nodeA = tokenizer.getNext();
                        tokenizer.getAndCheckNext(",");
                        String nodeB = tokenizer.getNext();
                        newRelation = new BidirectionalEdge(nodeA, nodeB);
                        break;
                    // TODO: create start and end parsing
                    default:
                        throw new RuntimeException(String.format("Invalid relation operation: %s ", op));
                }
                tokenizer.getAndCheckNext("\\)");

                // pass through optional trailing comma
                if (tokenizer.checkToken(",")) {
                    tokenizer.getNext();
                }

                graph.relations.add(newRelation);
            }
            tokenizer.getAndCheckNext("\\]");
        }
        return graph;
    }
}
