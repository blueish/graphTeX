package cs.ubc.ca.graphTeX.visitor;

import cs.ubc.ca.graphTeX.ast.*;
import cs.ubc.ca.graphTeX.tokenize.Tokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    // Static helper fields for tokenizer matching
    private final String LEFT_BRACKET = "\\{";
    private final String RIGHT_BRACKET = "\\}";
    private final String LEFT_BRACE = "\\[";
    private final String RIGHT_BRACE = "\\]";
    private final String LEFT_PAREN = "\\(";
    private final String RIGHT_PAREN = "\\)";
    private final String STAR = "\\*";
    private final String COMMA = ",";


    private Map<String, Node> nodeMap;
    private List<String> nodeVals;

    private Tokenizer tokenizer;


    public Parser() {
        this.tokenizer = Tokenizer.getTokenizer();
        this.nodeMap = new HashMap<>();
        this.nodeVals = new ArrayList<>();
    }

    public AST parse() {
        if (tokenizer.checkToken("graph")) {
            return parseGraph();
        }
        else {
            return parseTree();
        }
    }


    public AST parseTree() {
        Tree tree = new Tree();
        tokenizer.getAndCheckNext("tree");
        tokenizer.getAndCheckNext(LEFT_BRACKET);

        while (!tokenizer.checkToken((RIGHT_BRACKET))) {
            parseTreeNodes(tree);
        }

        tokenizer.getAndCheckNext(RIGHT_BRACKET);
        return tree;
    }

    public AST parseGraph() {
        Graph graph = new Graph();

        tokenizer.getAndCheckNext("graph");
        tokenizer.getAndCheckNext(LEFT_BRACKET);

        while (!tokenizer.checkToken((RIGHT_BRACKET))) {
            parseNodes(graph);
            parseRelations(graph);
            if (tokenizer.checkToken("loop")) {
                parseLoop(graph);
            }
        }

        tokenizer.getAndCheckNext(RIGHT_BRACKET);
        return graph;
    }

    private void parseRelations(Graph graph) {
        // now parse all relations
        tokenizer.getAndCheckNext("relations");
        tokenizer.getAndCheckNext(LEFT_BRACE);
        while (!tokenizer.checkToken(RIGHT_BRACE)) {
            Relation newRelation;
            String edgeValue;
            // All our relations are defined of the form (<op>, <param>*), so consume the beginning
            tokenizer.getAndCheckNext(LEFT_PAREN);
            String op = tokenizer.getNext();
            tokenizer.getAndCheckNext(",");
            switch (op) {
                case "->":
                    String fromNode = tokenizer.getNext();
                    tokenizer.getAndCheckNext(",");
                    String toNode = tokenizer.getNext();
                    newRelation = new DirectedEdge(nodeMap.get(fromNode), nodeMap.get(toNode));
                    if (tokenizer.checkToken(COMMA)){
                        tokenizer.getNext();
                        edgeValue = tokenizer.getNext();
                        newRelation.edgeLabel = edgeValue;
                    }
                    break;
                case "<-":
                    // Get the next two tokens and swap them (just desurgaring the <- to a ->
                    toNode = tokenizer.getNext();
                    tokenizer.getAndCheckNext(",");
                    fromNode = tokenizer.getNext();
                    newRelation = new DirectedEdge(nodeMap.get(fromNode), nodeMap.get(toNode));
                    if (tokenizer.checkToken(COMMA)){
                        tokenizer.getNext();
                        edgeValue = tokenizer.getNext();
                        newRelation.edgeLabel = edgeValue;
                    }
                    break;
                case "--":
                    String nodeA = tokenizer.getNext();
                    tokenizer.getAndCheckNext(",");
                    String nodeB = tokenizer.getNext();
                    newRelation = new UndirectedEdge(nodeMap.get(nodeA), nodeMap.get(nodeB));
                    if (tokenizer.checkToken(COMMA)){
                        tokenizer.getNext();
                        edgeValue = tokenizer.getNext();
                        newRelation.edgeLabel = edgeValue;
                    }
                    break;
                case "<>":
                    nodeA = tokenizer.getNext();
                    tokenizer.getAndCheckNext(",");
                    nodeB = tokenizer.getNext();
                    newRelation = new BidirectionalEdge(nodeMap.get(nodeA), nodeMap.get(nodeB));
                    if (tokenizer.checkToken(COMMA)){
                        tokenizer.getNext();
                        edgeValue = tokenizer.getNext();
                        newRelation.edgeLabel = edgeValue;
                    }
                    break;
                case "start":
                    String node = tokenizer.getNext();
                    newRelation = new FSAStartModifier(nodeMap.get(node));
                    break;
                case "end":
                    node = tokenizer.getNext();
                    newRelation = new FSAEndModifier(nodeMap.get(node));
                    break;
                default:
                    throw new RuntimeException(String.format("Invalid relation operation: %s ", op));
            }
            tokenizer.getAndCheckNext(RIGHT_PAREN);

            // pass through optional trailing comma
            if (tokenizer.checkToken(",")) {
                tokenizer.getNext();
            }

            graph.relations.add(newRelation);
        }
        tokenizer.getAndCheckNext(RIGHT_BRACE);
    }

    public TreeNode parseTreeNodes(Tree tree){
        TreeNode parentTreeNode;
        String treeNodeValue = tokenizer.getNext();
        List<TreeNode> treeNodeChildren = new ArrayList<>();

        parentTreeNode = new TreeNode(treeNodeValue);
        if (tree.rootNode == null) tree.rootNode = parentTreeNode;

        parentTreeNode.displayValue = treeNodeValue;

        tokenizer.getAndCheckNext(LEFT_PAREN);

        while(!tokenizer.checkToken(RIGHT_PAREN)) {
            // recursively add child tree nodes
            treeNodeChildren.add(parseTreeNodes(tree));
            if (tokenizer.checkToken(COMMA)) {
                tokenizer.getNext();
            }
        }

        if (!treeNodeChildren.isEmpty()){
            parentTreeNode.children = treeNodeChildren;
        }

        tokenizer.getAndCheckNext(RIGHT_PAREN);

        return parentTreeNode;
    }
    private void parseLoop(Graph graph) {
        tokenizer.getAndCheckNext("loop");
        tokenizer.getAndCheckNext(LEFT_BRACE);
        tokenizer.getAndCheckNext("makeall");
        tokenizer.getAndCheckNext("nodes");
        tokenizer.getAndCheckNext("withvalue");
        graph.valueToMark = tokenizer.getNext();
        tokenizer.getAndCheckNext("colour");
        graph.colourToMark = tokenizer.getNext();
        tokenizer.getAndCheckNext(RIGHT_BRACE);

    }


    private void parseNodes(Graph graph) {
        // assume nodes come before relations, so consume the node definitions
        tokenizer.getAndCheckNext("nodes");
        tokenizer.getAndCheckNext(LEFT_BRACE);

        while (!tokenizer.checkToken(RIGHT_BRACE)) {
            Node newNode;
            String token = tokenizer.getNext();
            if (token.matches(STAR)) {
                String nodeNameValue = tokenizer.getNext();
                newNode = new Node(nodeNameValue, nodeNameValue);
                // pass through optional trailing comma
                if (tokenizer.checkToken(",")) {
                    tokenizer.getNext();
                }
            } else {
                if (!token.matches(LEFT_BRACKET)) {
                    throw new RuntimeException(String.format("Invalid token found for node dec: %s", token));
                }
                tokenizer.getAndCheckNext("id");
                tokenizer.getAndCheckNext(":");
                String nodeId = tokenizer.getNext();
                tokenizer.getAndCheckNext(",");
                tokenizer.getAndCheckNext("value");
                tokenizer.getAndCheckNext(":");
                String nodeValue = tokenizer.getNext();

                newNode = new Node(nodeValue, nodeId);

                if (tokenizer.checkToken(",")) {
                    tokenizer.getNext();
                    tokenizer.getAndCheckNext("label");
                    tokenizer.getAndCheckNext(":");
                    newNode.optLabel = tokenizer.getNext();
                }

                // pass through optional trailing comma
                if (tokenizer.checkToken(",")) {
                    tokenizer.getNext();
                }

                tokenizer.getAndCheckNext(RIGHT_BRACKET);
            }
            // pass through optional trailing comma
            if (tokenizer.checkToken(",")) {
                tokenizer.getNext();
            }

            // pass through optional trailing comma
            if (tokenizer.checkToken(",")) {
                tokenizer.getNext();
            }
            nodeMap.put(newNode.refId, newNode);
            nodeVals.add(newNode.displayValue);
            graph.nodes.add(newNode);
        }

        tokenizer.getAndCheckNext(RIGHT_BRACE);
    }
}
