package cs.ubc.ca.graphTeX;

import cs.ubc.ca.graphTeX.ast.AST;
import cs.ubc.ca.graphTeX.ast.Graph;
import cs.ubc.ca.graphTeX.tokenize.Tokenizer;
import cs.ubc.ca.graphTeX.visitor.Parser;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {
//        List<String> literals = Arrays.asList("graph", "nodes", "relations");
        List<String> literals = Arrays.asList("graph","nodes","[","]","->","(",")",",","{","}");
        Tokenizer.makeTokenizer("input.gtex",literals);
//        Parser parser = new Parser();
//        AST ast = parser.parse();
//        System.out.println("completed successfully");
//        System.out.println(ast);
    }
}
